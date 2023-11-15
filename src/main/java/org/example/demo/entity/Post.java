package org.example.demo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
@TableName(value = "post")
public class Post {

    @TableId(type = IdType.AUTO)
    private Integer postId;

    private Date postTime;

    @Schema(description = "发布者状态：0代表撤销发布，1代表发布")
    private Integer postStatus;
}
