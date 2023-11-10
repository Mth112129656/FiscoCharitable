package org.example.demo.enums;

public enum CommonEnum implements EnumResponse{
    LOGIN_TOKEN_ERROR(-1, "登录状态无效!请重新登录");
    private final int code;
    private final String message;

    CommonEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
