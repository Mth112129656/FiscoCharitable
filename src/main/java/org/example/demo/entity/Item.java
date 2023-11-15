package org.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Data
@Component
@TableName(value = "item")
public class Item {
    @TableId(type = IdType.AUTO)
    private Integer itemId;

    private String itemName;

    private String itemDes;

    private String fileUrl;

    @TableField(exist = false)
    private MultipartFile blFile;

    private Integer userId;

    private Integer postId;

    @Schema(description = "一对一关系,该资助项目属于哪个发布者用户:查询需要")
    @TableField(exist = false)
    private User user;

    @Schema(description = "一对一关系,该资助项目的发布状态:查询需要")
    @TableField(exist = false)
    private Post post;
}
