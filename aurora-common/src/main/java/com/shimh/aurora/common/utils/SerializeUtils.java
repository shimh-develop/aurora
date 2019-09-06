package com.shimh.aurora.common.utils;

import com.shimh.aurora.common.exception.AuroraException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import java.io.*;

/**
 * @author: shimh
 * @create: 2019年09月
 **/
public class SerializeUtils {

    public static String serialize(Object object) {
        if (null == object) {
            return null;
        }
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            return Base64.encodeBase64String(bos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("serialize session error", e);
        }
    }

    public static Object deserialize(String str) {
        if (null == str) {
            return null;
        }
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(Base64.decodeBase64(str));
            ObjectInputStream ois = new ObjectInputStream(bis);
            return ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("deserialize session error", e);
        }
    }

}
