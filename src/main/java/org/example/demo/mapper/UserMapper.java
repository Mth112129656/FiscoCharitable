package org.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.example.demo.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}

