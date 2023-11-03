package org.example.demo.controller;

import org.example.demo.entity.User;
import org.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/home")
    String home() {
        return "Hello World!";
    }

    @RequestMapping("/userall")
    List<User> userAll() {
        List<User> user = userMapper.selectList(null);
        return user;
    }

    @RequestMapping("/userone")
    User userOne() {
        User user = userMapper.queryUserId(1);
        return user;
    }

    @RequestMapping("/username")
    User userName() {
        User user = userMapper.queryUsername("张三");
        return user;
    }
}
