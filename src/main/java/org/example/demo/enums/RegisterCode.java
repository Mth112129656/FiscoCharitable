package org.example.demo.enums;

/**
 * 注册过程中提示信息
 *
 */
public enum RegisterCode {
    EMAIL_FORMAT_ERROR(-1, "邮箱格式错误"),
    EMAIL_ALREADY_REGISTERED(-1, "邮箱已注册"),
    CAPTCHA_ERROR(-1, "验证码错误!请重新获取"),
    CAPTCHA_EXPIRED(-1, "验证码已过期!请重新获取"),
    SYSTEM_ERROR(-1, "系统繁忙，请稍后重试"),
    SUCCESS(200, "注册成功!");

    private final int code;
    private final String message;

    RegisterCode(int code, String message) {
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
