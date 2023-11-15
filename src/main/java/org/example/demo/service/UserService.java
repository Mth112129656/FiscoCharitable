package org.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.demo.entity.User;
import org.example.demo.mapper.UserMapper;
import org.example.demo.util.FiscoBcos;
import org.example.demo.util.Utils;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    // from:邮件发送方
    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private FiscoBcos fiscoBcos;

    public List<User> userAll() {
        List<User> user = userMapper.selectList(null);
        return user;
    }


    public String GetContract() throws Exception {
        // 测试合约hello
//    transaction hash: 0xf00909b9410b5241beaf0749a0cfaea0f299ee5a47b309929ced09843f02c436
//    contract address: 0xff71babd817e225d1decf55312e033625cb06aef
//    currentAccount: 0x3d3ee495c68e6c563170107dd075d5b352eafce4

        String helloWorldAddrss = "0xff71babd817e225d1decf55312e033625cb06aef";
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

        String helloWorldAddrss = "0xff71babd817e225d1decf55312e033625cb06aef";
        List<Object> params = new ArrayList<>();
        params.add(value);
        TransactionResponse transactionResponse = fiscoBcos.getTransactionProcessor().sendTransactionAndGetResponseByContractLoader("HelloWorld", helloWorldAddrss, "set", params);

        return "success";
    }


    /**
     * 查询用户登录信息是否正确
     *
     * @param email
     * @param password
     * @return
     */
    public long verifyLogin(String email, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        queryWrapper.eq("password", password);
        long count = userMapper.selectCount(queryWrapper);
        return count;
    }

    /**
     *根据唯一的email查询用户id
     *
     * @param email
     * @return
     */
    public int getUserId(String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        List<User> users = userMapper.selectList(queryWrapper);
        return users.get(0).getUserId();
    }

    /**
     * 验证邮箱格式以及重复性
     *
     * @param email 邮箱
     * @return -1: 格式错误, -2: 重复, 1: 正确
     */
    public int verifyUser(String email) {
        boolean validEmail = Utils.isValidEmail(email); // 邮箱验证
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        long count = userMapper.selectCount(queryWrapper);

        //todo 枚举类
        if (!validEmail) {
            return -1; // 格式错误
        } else if (count == 1) {
            return -2; // 重复
        } else {
            return 1; // 正确
        }
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    public int regUser(User user) {
        user.setUserId(null);
        user.setAdmin(0);
        return userMapper.insert(user);
    }

    /**
     * 发送邮件
     *
     * @param to      目的地邮件
     * @param title   邮件标题
     * @param content 邮件内容
     */
    public void sendMail(String to, String title, String content) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(title);
        message.setText(content);
        mailSender.send(message);
    }

}
