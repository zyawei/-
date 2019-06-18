import org.jetbrains.annotations.NotNull;

/**
 * @date: 2018/12/28 8:34
 * @author: zhang.yw
 */
public class HexStringUtils {
    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
    private static final char[] CHAR_ARRAY = new char[128];

    static {
        for (int i = 0; i < HEX_ARRAY.length; i++) {
            char c = HEX_ARRAY[i];
            CHAR_ARRAY[c] = (char) i;
        }
    }

    /**
     * 将byte[] 数组转换为十六进制字符串，每个byte以空格分割
     * 使用{@link #hexStringToBytes(String)}可还原此byte[]
     *
     * @param bytes byte array
     * @return HexString
     * @see #hexStringToBytes(String)
     */

    public static String bytesToHexString(@NotNull byte[] bytes) {
        char[] hexChars = new char[bytes.length * 3];
        for (int j = 0, len = bytes.length; j < len; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 3] = HEX_ARRAY[v >>> 4];
            hexChars[j * 3 + 1] = HEX_ARRAY[v & 0xF];
            hexChars[j * 3 + 2] = ' ';
        }
        return new String(hexChars);
    }


    /**
     * 将{@link #bytesToHexString(byte[])}转换的String 还原
     *
     * @param hexString HexString
     * @return byte array
     * @see #bytesToHexString(byte[])
     */
    public static byte[] hexStringToBytes(@NotNull String hexString) {
        byte[] bytes = new byte[hexString.length() / 3];
        for (int i = 0, len = bytes.length; i < len; i++) {
            int i0 = CHAR_ARRAY[hexString.charAt(i * 3)];
            int i1 = CHAR_ARRAY[hexString.charAt(i * 3 + 1)];
            bytes[i] = (byte) ((i0 << 4) | (i1 & 0xf));
        }
        return bytes;
    }
}
