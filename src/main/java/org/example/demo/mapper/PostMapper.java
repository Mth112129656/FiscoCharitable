package org.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.demo.entity.Post;

@Mapper
public interface PostMapper extends BaseMapper<Post> {
}
