package org.example.demo.enums;

public enum ItemEnum implements EnumResponse {
    APPLY_ITEM_ERROR(-1, "申请资助项目失败!请检查信息!"),
    APPLY_ITEM_SUCCESS(200, "申请资助项目成功!");
    private final int code;
    private final String message;

    ItemEnum(int code, String message) {
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
