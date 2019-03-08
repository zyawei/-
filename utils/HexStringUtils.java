package com.miaxis.mxhidfingerdriverdome;

/**
 * @date: 2018/12/28 8:34
 * @author: zhang.yw
 * @project: BTFingerPrinterLibDemo
 */
public class HexStringUtils {

    /**
     * 在数组中截取有效字符（遇到终止符 0 即停止）并转换为String
     */
    public static String toStringUntilZero(byte[] bytes) {
        int index = -1;
        for (int i = 0; i < bytes.length; i++) {
            if (0 == bytes[i]) {
                index = i;
                break;
            }
        }
        return new String(bytes, 0, index == -1 ? bytes.length : index);
    }

    /**
     * 将byte[] 数组转换为十六进制字符串，每个byte以空格分割
     * 使用{@link #hexStringToBytes(String)}可还原此byte[]
     *
     * @param bytes byte array
     * @return HexString
     * @see #hexStringToBytes(String)
     */
    public static String bytesToHexString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            sb.append(Integer.toHexString(aByte & 0xff)).append(' ');
        }
        return sb.toString();
    }

    /**
     * 将{@link #bytesToHexString(byte[])}转换的String 还原
     *
     * @param hexString HexString
     * @return byte array
     * @see #bytesToHexString(byte[])
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.length() == 0) {
            return new byte[0];
        }
        String[] split = hexString.split(" ");
        byte[] result = new byte[split.length];
        try {
            for (int i = 0; i < split.length; i++) {
                int i1 = Integer.parseInt(split[i], 16);
                result[i] = (byte) i1;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

}
