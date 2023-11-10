package org.example.demo.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.entity.ResResult;
import org.example.demo.entity.User;
import org.example.demo.enums.RegisterCode;
import org.example.demo.service.RedisService;
import org.example.demo.service.UserService;


import org.example.demo.util.JwtUtil;
import org.example.demo.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户请求")
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;
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
        String email = user.getEmail();
        int verifyResult = userService.verifyUser(email);
        if (user == null) {
            Utils.setRegResponse(res, RegisterCode.SYSTEM_ERROR);
            return res;
        }

        switch (verifyResult) {
            case -1:
                res.setCode(RegisterCode.EMAIL_FORMAT_ERROR.getCode());
                res.setMsg(RegisterCode.EMAIL_FORMAT_ERROR.getMessage());
                break;
            case -2:
                res.setCode(RegisterCode.EMAIL_ALREADY_REGISTERED.getCode());
                res.setMsg(RegisterCode.EMAIL_ALREADY_REGISTERED.getMessage());
                break;
            default:
                try {
                    boolean ttl = redisService.tokenTTL(email);//获取key的过期时间
                    if (ttl) {//未过期
                        String cachedCode = redisService.getRedis(email); // 从Redis中获取验证码
                        if (cachedCode.equals(user.getCaptcha())) {//验证码正确
                            int regResult = userService.regUser(user);
                            if (regResult == 1) {
                                res.setCode(RegisterCode.SUCCESS.getCode());
                                res.setData(RegisterCode.SUCCESS.getMessage());
                                //清除redis,保证一个验证码注册一次
                                redisService.deleteRedis(email);
                            }
                        } else {//验证码错误
                            res.setCode(RegisterCode.CAPTCHA_ERROR.getCode());
                            res.setMsg(RegisterCode.CAPTCHA_ERROR.getMessage());
                        }
                    } else {//验证码过期
                        res.setCode(RegisterCode.CAPTCHA_EXPIRED.getCode());
                        res.setMsg(RegisterCode.CAPTCHA_EXPIRED.getMessage());
                    }

                } catch (Exception e) {
                    log.error("注册用户时发生异常:{}", e.getMessage());
                    res.setCode(RegisterCode.SYSTEM_ERROR.getCode());
                    res.setMsg(RegisterCode.SYSTEM_ERROR.getMessage());
                }
                break;
        }
        return res;
    }


    @Operation(summary = "获取验证码")
    @GetMapping("/getLoginCheckCode")
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
            userService.sendMail(email, "注册验证码", message);
            redisService.setRedis(email, checkCode, 10);//10分钟过期
            res.setCode(200);
            res.setData("获取验证码成功!");
        } catch (Exception e) {
            res.setCode(-1);
            res.setMsg("获取验证码失败!");

        }
        return res;
    }


}

