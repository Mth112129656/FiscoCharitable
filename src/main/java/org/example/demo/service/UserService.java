package org.example.demo.service;

import org.example.demo.entity.User;
import org.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

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
}
