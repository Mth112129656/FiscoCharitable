package org.example.demo.mapper;

import org.example.demo.common.MybatisCache;
import org.example.demo.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;

@CacheNamespace(implementation = MybatisCache.class)
public interface ArticleMapper extends BaseMapper<Article>{
}
