package org.example.demo.controller;


import org.example.demo.entity.User;
import org.example.demo.service.UserService;
import org.example.demo.util.FiscoBcos;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private FiscoBcos fiscoBcos;

    @RequestMapping("/testgetcontract")
    String testGetContract() throws Exception {

        return userService.GetContract();
    }

    @RequestMapping("/testsetcontract/{value}")
    String testSetContract(String value) throws Exception {
        return userService.SetContract(value);
    }

    @RequestMapping("/userall")
    List<User> userAll() {
        List<User> user = userService.userAll();
        return user;
    }

    @RequestMapping("/userone/{id}")
    User userOne(Integer id) {
        User user = userService.userOne(id);
        return user;
    }


    @RequestMapping("/username/{name}")
    User userName(String name) {
        User user = userService.userName(name);
        return user;
    }
}
