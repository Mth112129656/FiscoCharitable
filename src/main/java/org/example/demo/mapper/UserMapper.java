package org.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.demo.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from tb_user where id = #{id}")
    User queryUserId(Integer id);

    @Select("select * from tb_user where username = #{username}")
    User queryUsername(String username);

}
