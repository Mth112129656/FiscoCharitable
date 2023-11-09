package org.example.demo.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.demo.entity.User;
import org.example.demo.service.RedisDataService;
import org.example.demo.service.UserService;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "用户信息查询（测试）")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisDataService redisDataService;


    @Operation(summary = "区块链数据查询")
    @GetMapping("/testgetcontract")
    String testGetContract() throws Exception {

        return userService.GetContract();
    }

    @Operation(summary = "区块链修改数据")
    @Parameters({
            @Parameter(name = "value",description = "修改名称",in = ParameterIn.PATH),
    })
    @PostMapping("/testsetcontract/{value}")
    String testSetContract(@PathVariable("value") String value) throws Exception {
        return userService.SetContract(value);
    }

    @Operation(summary = "查询全部数据（数据库）")
    @GetMapping("/userall")
    List<User> userAll() {
        List<User> user = userService.userAll();
        return user;
    }

    @Operation(summary = "根据id查询用户信息")
    @Parameters({
            @Parameter(name = "id",description = "查询id",in = ParameterIn.PATH),
    })
    @GetMapping("/userone/{id}")
    User userOne(@PathVariable("id") Integer id) {
        User user = userService.userOne(id);
        return user;
    }


    @Operation(summary = "根据姓名查询用户信息")
    @Parameters({
            @Parameter(name = "name",description = "name",in = ParameterIn.PATH),
    })
    @GetMapping("/username/{name}")
    User userName(@PathVariable("name") String name) {
        User user = userService.userName(name);
        return user;
    }

    @Operation(summary = "添加信息(redis)")
    @PostMapping("/userOneRedisPost/")
    String userOneRedisPost() throws Exception {
        redisDataService.postdataString();

        return "ok";
    }

    @Operation(summary = "查询信息(redis)")
    @Parameters({
            @Parameter(name = "value",description = "value",in = ParameterIn.PATH),
    })
    @GetMapping("/userOneRedisGet/{value}")
    Object userOneRedisGet(String value) throws Exception {
        Object valuereids = redisDataService.getdataString(value);

        return valuereids;
    }
}
