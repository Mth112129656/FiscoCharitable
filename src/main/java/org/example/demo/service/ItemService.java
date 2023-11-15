package org.example.demo.service;

import org.example.demo.entity.Item;
import org.example.demo.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ItemService {

    @Autowired
    private ItemMapper itemMapper;

    public void insertItem(Item item) throws Exception {
        item.setItemId(null);
        Integer itemRes = itemMapper.insert(item);

        if (itemRes != 1)
            throw new Exception("插入item失败,开启回滚");
    }
}
