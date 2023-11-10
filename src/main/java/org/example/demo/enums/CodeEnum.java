package org.example.demo.enums;

public enum CodeEnum implements EnumResponse {

    SUCCESS(200, "获取验证码成功!"),
    EMAIL_EMPTY(-1, "邮箱不能为空"),
    SEND_MAIL_ERROR(-1, "获取验证码失败!");

    private final int code;
    private final String message;

    CodeEnum(int code, String message) {
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
