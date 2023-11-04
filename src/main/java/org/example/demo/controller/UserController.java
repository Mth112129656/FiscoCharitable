package org.example.demo.controller;

import com.webank.wedpr.Main;
import org.example.demo.entity.User;
import org.example.demo.mapper.UserMapper;
import org.example.demo.util.FiscoBcos;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.client.protocol.response.BlockNumber;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.transaction.model.dto.CallResponse;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FiscoBcos fiscoBcos;

    @RequestMapping("/testgetcontract")
    TransactionResponse testGetContract() throws Exception {

        //    测试合约hello
//    transaction hash: 0xf00909b9410b5241beaf0749a0cfaea0f299ee5a47b309929ced09843f02c436
//    contract address: 0xff71babd817e225d1decf55312e033625cb06aef
//    currentAccount: 0x3d3ee495c68e6c563170107dd075d5b352eafce4

        fiscoBcos.init();
        Client client = fiscoBcos.getBcosSDK().getClient(Integer.valueOf(1));
        CryptoKeyPair keyPair = client.getCryptoSuite().createKeyPair();
        String helloWorldAddrss="0xff71babd817e225d1decf55312e033625cb06aef";
        AssembleTransactionProcessor transactionProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(client, keyPair, "src/main/resources/abi/", "src/main/resources/bin/");
        List<Object> params = new ArrayList<>();
//        params.add("chenbiao");
        TransactionResponse transactionResponse = transactionProcessor.sendTransactionAndGetResponseByContractLoader("HelloWorld", helloWorldAddrss, "get", params);

        return transactionResponse;
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
