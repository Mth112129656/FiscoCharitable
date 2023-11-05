package org.example.demo.controller;

import org.example.demo.entity.User;
import org.example.demo.mapper.UserMapper;
import org.example.demo.service.UserService;
import org.example.demo.util.FiscoBcos;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private FiscoBcos fiscoBcos;

    @RequestMapping("/testgetcontract")
    String testGetContract() throws Exception {
        // 测试合约hello
//    transaction hash: 0xf00909b9410b5241beaf0749a0cfaea0f299ee5a47b309929ced09843f02c436
//    contract address: 0xff71babd817e225d1decf55312e033625cb06aef
//    currentAccount: 0x3d3ee495c68e6c563170107dd075d5b352eafce4

        String helloWorldAddrss="0xff71babd817e225d1decf55312e033625cb06aef";
        List<Object> params = new ArrayList<>();
//        params.add("chenbiao");
        TransactionResponse transactionResponse = fiscoBcos.getTransactionProcessor().sendTransactionAndGetResponseByContractLoader("HelloWorld", helloWorldAddrss, "get", params);

        return transactionResponse.getValues();
    }

    @RequestMapping("/testsetcontract")
    String testSetContract() throws Exception {
        // 测试合约hello
//    transaction hash: 0xf00909b9410b5241beaf0749a0cfaea0f299ee5a47b309929ced09843f02c436
//    contract address: 0xff71babd817e225d1decf55312e033625cb06aef
//    currentAccount: 0x3d3ee495c68e6c563170107dd075d5b352eafce4

        String helloWorldAddrss="0xff71babd817e225d1decf55312e033625cb06aef";
        List<Object> params = new ArrayList<>();
        params.add("比赛加油!!!");
        TransactionResponse transactionResponse = fiscoBcos.getTransactionProcessor().sendTransactionAndGetResponseByContractLoader("HelloWorld", helloWorldAddrss, "set", params);

        return "ok";
    }

    @RequestMapping("/userall")
    List<User> userAll() {
        List<User> user = userService.userAll();
        return user;
    }

    @RequestMapping("/userone")
    User userOne() {
        User user = userService.userOne(1);
        return user;
    }

    @RequestMapping("/username")
    User userName() {
        User user = userService.userName("张三");
        return user;
    }
}
