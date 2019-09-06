package com.shimh.aurora.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author: shimh
 * @create: 2019年09月
 **/
public class PasswordUtils {

    /**
     * 加密2次 salt + password
     * @param password
     * @param salt
     * @return
     * @throws Exception
     */
    public static String encode(String password, String salt) throws Exception{
        return DigestUtils.md5Hex( DigestUtils.md5(salt + password));
    }
}
