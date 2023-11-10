package org.example.demo.util;

import org.example.demo.entity.ResResult;
import org.example.demo.enums.RegisterCode;

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
                || url.contains("doc")
                || url.contains("/webjars/");
    }

    public static void setRegResponse(ResResult<String> res, RegisterCode registerCode) {
        res.setCode(registerCode.getCode());
        res.setMsg(registerCode.getMessage());
    }
}
