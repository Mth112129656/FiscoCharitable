package org.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.demo.entity.Post;
import org.example.demo.entity.User;
import org.example.demo.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PostService {

    @Autowired
    private PostMapper postMapper;

    /**
     * 插入post,并且返回该记录主键postId
     *
     * @return
     */
    public Integer insertPost() throws Exception {
        Post post = new Post();
        //创建postId:末尾id+1
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("post_id");
        queryWrapper.last("limit 1");
        Post new_post = postMapper.selectOne(queryWrapper);
        post.setPostId(new_post.getPostId() + 1);
        post.setPostTime(new Date());
        post.setPostStatus(1);
        Integer insertRes = postMapper.insert(post);
        if (insertRes == 1) return post.getPostId();
        else
            throw new Exception("插入post失败,开启回滚");
    }
}
