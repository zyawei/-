package com.mx.finger.utils;

/**
 * @author zhangyw
 * @date 2019-06-27 09:46
 * @email zyawei@live.com
 */
public class BytesUtils {

    /**
     * 将byte[](低字节在前) 转换为short
     */
    public static short bytes2Short(byte[] bytes) {
        return bytes2Short(bytes, 0);
    }

    /**
     * 将byte[](低字节在前) 转换为short
     *
     * @param position 从这个下标开始取两个字节
     */
    public static short bytes2Short(byte[] bytes, int position) {
        if (position + 2 > bytes.length) {
            throw new IllegalArgumentException(String.format("position[%d] + 2 > bytes.length[%d]", position, bytes.length));
        }
        return (short) (((bytes[position + 1] & 0xff) << 8) | (bytes[position] & 0xff));
    }

    /**
     * 将short转换为byte[] , 低字节在前
     */
    public static byte[] short2Bytes(short s) {
        byte[] result = new byte[2];
        result[0] = (byte) (s & 0xff);
        result[1] = (byte) ((s >> 8) & 0xff);
        return result;
    }
}
