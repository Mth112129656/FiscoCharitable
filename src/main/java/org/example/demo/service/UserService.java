package org.example.demo.service;

import org.example.demo.entity.User;
import org.example.demo.mapper.UserMapper;
import org.example.demo.util.FiscoBcos;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FiscoBcos fiscoBcos;


    public User userOne(Integer id) {
        User user = userMapper.queryUserId(id);
        return user;
    }

    public List<User> userAll() {
        List<User> user = userMapper.selectList(null);
        return user;
    }

    public User userName(String name) {
        User user = userMapper.queryUsername(name);
        return user;
    }

    public String GetContract() throws Exception {
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

    public String SetContract(String value) throws Exception {
        // 测试合约hello
//    transaction hash: 0xf00909b9410b5241beaf0749a0cfaea0f299ee5a47b309929ced09843f02c436
//    contract address: 0xff71babd817e225d1decf55312e033625cb06aef
//    currentAccount: 0x3d3ee495c68e6c563170107dd075d5b352eafce4

        String helloWorldAddrss="0xff71babd817e225d1decf55312e033625cb06aef";
        List<Object> params = new ArrayList<>();
        params.add(value);
        TransactionResponse transactionResponse = fiscoBcos.getTransactionProcessor().sendTransactionAndGetResponseByContractLoader("HelloWorld", helloWorldAddrss, "set", params);

        return "success";
    }

//    public User userOneredis(String id) {
//        User user = new User();
//        redisCache.setCacheObject(id,user);
//        return user;
//    }
}
