package com.xiao.oasystem.utils;

import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;

public class EncryptUtil {

    //加盐并生成密码
    public static String encrypt(String password) {
        // 产生盐值(32位)  这里要注意去掉 UUID 生成 -
        String salt = UUID.randomUUID().toString().replaceAll("-", "");
        // 生成加盐之后的密码((盐值 + 密码)整体 md5 加密)
        String saltPassword = DigestUtils.md5DigestAsHex((salt + password).getBytes());
        // 生成最终密码(保存到数据库中的密码)[自己约定的格式：32位盐值 +&+ 32位加盐后的密码]
        // 这样约定格式是为了解密的时候方便得到盐值
        return salt + "$" + saltPassword;
    }

    public static String encrypt(String password, String salt) {
        // 生成加盐后的密码
        String saltPassword = DigestUtils.md5DigestAsHex((salt + password).getBytes());
        // 生成最终密码(约定格式: 32位 盐值 +&+ 32位加盐后的密码)
        String finalPassword = salt + "$" + saltPassword;
        return finalPassword;
    }

    //验证密码
    public static boolean isOk(String inputPassword, String dbPassword) {
        // 判空处理
        if(StringUtils.hasLength(inputPassword) && StringUtils.hasLength(dbPassword)) {
            // 得到盐值(之前约定: $前面的就是盐值)
            String salt = dbPassword.split("\\$")[0];// 由于 $ 在这里也可以表示通配符，所以需要使用 \\ 进行转义
            // 使用之前的加密步骤将明文进行加密，生成最终密码
            String confirmPassword = EncryptUtil.encrypt(inputPassword, salt);
            // 对比两个最终密码是否相同
            return confirmPassword.equals(dbPassword);
        }
        return false;
    }
}
