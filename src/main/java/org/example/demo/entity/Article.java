package org.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_article")
public class Article {

    private static final long serialVersionUID=1L;

    public Article(String title, String detail, Date createTime) {
        this.title = title;
        this.detail = detail;
        this.createTime = createTime;
    }

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 主题
     */
    private String title;

    /**
     * 详细信息
     */
    private String detail;

    /**
     * 创建时间
     */
    private Date createTime;
}
