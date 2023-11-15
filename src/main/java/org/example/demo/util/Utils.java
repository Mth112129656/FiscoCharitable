package org.example.demo.util;

import org.example.demo.entity.ResResult;
import org.example.demo.enums.EnumResponse;
import org.example.demo.enums.RegisterEnum;

import java.util.Random;
import java.util.regex.Pattern;

public class Utils {

    /**
     * 验证邮箱,邮箱正则表达
     *
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email) {
        if ((email != null) && (!email.isEmpty())) {
            return Pattern.matches("^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$", email);
        }
        return false;
    }

    /**
     * 生成六位数验证码
     *
     * @return
     */
    public static String generateCheckCode() {
        int code = new Random().nextInt(899999) + 100000;
        return String.valueOf(code);
    }

    /**
     * 检查拦截器url白名单
     *
     * @param url
     * @return
     */
    public static boolean urlCheck(String url) {
        return url.matches("(?i).*login.*")
                || url.matches("(?i).*register.*")
                || url.matches("(?i).*favicon*")
                || url.contains("doc")
                || url.contains("/webjars/");
    }

    /**
     * 通用设置接口回答
     *
     * @param res          接口返回
     * @param responseEnum 返回的枚举信息
     * @param <T>          各种枚举类
     */
    public static <T extends EnumResponse> void setResponseEnum(ResResult<String> res, T responseEnum) {
        res.setCode(responseEnum.getCode());
        res.setMsg(responseEnum.getMessage());
    }
}
