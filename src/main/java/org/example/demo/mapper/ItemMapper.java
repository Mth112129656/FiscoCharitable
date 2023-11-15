package org.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.demo.entity.Item;

@Mapper
public interface ItemMapper extends BaseMapper<Item> {
}
