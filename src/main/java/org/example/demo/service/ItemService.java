package org.example.demo.service;

import org.example.demo.entity.Item;
import org.example.demo.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.UUID;

@Service
public class ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Value("${file.upload.url}")
    private String uploadFilePath;

    /**
     * 申请资助项目
     *
     * @param item
     * @throws Exception
     */
    public void insertItem(Item item) throws Exception {
        item.setItemId(null);
        Integer itemRes = itemMapper.insert(item);

        if (itemRes != 1)
            throw new Exception("插入item失败,开启回滚");
    }

    /**
     * 上传文件
     *
     * @param file
     * @return 若上传成功则返回路径
     * @throws Exception
     */
    public String uploadFile(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();  // 文件名
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String destUrl = uuid + fileName;
        File dest = new File(uploadFilePath + '/' + destUrl);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        file.transferTo(dest);
        return destUrl;
    }
}
