package org.example.demo.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.demo.entity.ResResult;
import org.example.demo.entity.User;
import org.example.demo.service.UserService;


import org.example.demo.util.JwtUtil;
import org.example.demo.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户请求")
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    private String code;


    @Operation(summary = "区块链数据查询")
    @GetMapping("/testgetcontract")
    String testGetContract() throws Exception {

        return userService.GetContract();
    }

    @Operation(summary = "区块链修改数据")
    @Parameters({@Parameter(name = "value", description = "修改名称", in = ParameterIn.PATH),})
    @PostMapping("/testsetcontract/{value}")
    String testSetContract(@PathVariable("value") String value) throws Exception {
        return userService.SetContract(value);
    }


    @Operation(summary = "邮箱账号密码登录")
    @Parameters({@Parameter(name = "email", description = "登录邮箱"), @Parameter(name = "password", description = "登录密码")})
    @GetMapping("/loginUser")
    ResResult loginUser(String email, String password) {
        ResResult res = new ResResult<String>();
        if (!"".equals(email) && !"".equals(password)) {//输入不为空
            long verify_res = userService.verifyLogin(email, password);
            if (verify_res == 1) {//登录成功
                res.setCode(200);
                res.setData(JwtUtil.createToken(email));
                //返回token
                return res;
            }
        }
        res.setCode(-1);
        res.setMsg("账号密码输入错误!");
        return res;
    }

    @Operation(summary = "注册用户")
    @Parameters({@Parameter(name = "user", description = "将表单属性整合成user(json字符串)传入接口"),})
    @PostMapping("/registerUser")
    ResResult registerUser(@RequestBody User user) {
        ResResult res = new ResResult<String>();

        int verifyResult = userService.verifyUser(user.getEmail());
        if (user != null) switch (verifyResult) {
            case -1:
                res.setCode(-1);
                res.setMsg("邮箱格式错误");
                break;
            case -2:
                res.setCode(-1);
                res.setMsg("邮箱已注册");
                break;
            default:
                try {
                    // todo 从Redis中获取验证码并验证
//                    String cachedCode = redisService.getCode(user.getEmail()); // 从Redis中获取验证码
                    String cachedCode = code;
                    if (cachedCode.equals(user.getCaptcha())) {
                        int regResult = userService.regUser(user);
                        if (regResult == 1) {
                            res.setCode(200);
                            res.setData("注册成功!");
                        }
                    } else {
                        res.setCode(-1);
                        res.setMsg("验证码错误");
                    }
                } catch (Exception e) {
//                    log.error("注册用户时发生异常", e);
                    res.setCode(-1);
                    res.setMsg("系统繁忙，请稍后重试");
                }
                break;
        }
        return res;
    }


    @Operation(summary = "获取验证码")
    @GetMapping("/getCheckCode")
    ResResult getLoginCheckCode(String email) {
        ResResult res = new ResResult<String>();
        if (email == null) {
            res.setCode(-1);
            res.setMsg("邮箱不能为空");
            return res;
        }
        String checkCode = Utils.generateCheckCode();
        String message = "您的注册验证码为：" + checkCode;
        try {
            //todo 判断是否超过10分钟: 超过则重新发,反之不发
            userService.sendMail(email, "注册验证码", message);
            //todo redis 以验证码存活10分钟
            code = checkCode;
            res.setCode(200);
            res.setData("获取验证码成功!");
        } catch (Exception e) {
            res.setCode(-1);
            res.setMsg("获取验证码失败!");
        }
        return res;
    }



}

