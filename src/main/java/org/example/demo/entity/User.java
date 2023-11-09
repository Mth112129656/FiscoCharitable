package org.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
@TableName(value = "user")
public class User {

    @TableId(type = IdType.AUTO)
    private Integer userId;

    private String email;

    private String password;

    private String username;

    @Schema(description = "1代表男性,0代表女性")
    private int gender;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "邮箱验证码")
    @TableField(exist = false)
    private String captcha;


}
