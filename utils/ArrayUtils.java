package com.mx.finger.utils;

/**
 * @author zhangyw
 * @date 2019-06-27 09:53
 * @email zyawei@live.com
 */
public class ArrayUtils {
    public static byte[] merge(byte[]... bytes) {
        int length = 0;
        for (byte[] aByte : bytes) {
            length += aByte.length;
        }
        byte[] result = new byte[length];
        int position = 0;
        for (byte[] aByte : bytes) {
            System.arraycopy(aByte, 0, result, position, aByte.length);
            position += aByte.length;
        }
        return result;
    }



}
