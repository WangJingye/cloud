package com.delcache.website.common.helper;

import org.apache.commons.codec.digest.DigestUtils;

public class Encrypt {

    public static String encryptPassword(String password, String salt) {
        return DigestUtils.md5Hex(salt + DigestUtils.md5Hex(password + salt));
    }
}
