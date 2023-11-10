package org.example.demo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ResResult<T> {
    @Schema(description = "状态码")
    private Integer code;

    @Schema(description = "提示信息")
    private String msg;

    @Schema(description = "查询结果数据")
    private T data;

    public ResResult() {
    }

    public ResResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public ResResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }
}
