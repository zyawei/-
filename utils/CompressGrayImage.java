package com.miaxis.btfingerprinterlibdemo;

import android.util.Log;


/**
 * @date: 2019/1/17 13:51
 * @author: zhang.yw
 * @project: BTFingerPrinterLibDemo
 */
public class CompressGrayImage {

    private static final String TAG = "MX-CompressGrayImage";
    private static byte[] BINARY = new byte[]{(byte) 0x80, 0x40, 0x20, 0x10, 0x8, 0x4, 0x2, 0x1};

    public static byte[] compressGrayPix(byte[] src, int width, int height) {
        //Log.i(TAG, "size=" + width + "/" + height + "*******************************************************************************************");
        for (int i = 0; i < height; i++) {
            char[] chars = new char[width];
            for (int j = 0; j < chars.length; j++) {
                chars[j] = (src[i * width + j] == 0 ? ' ' : '0');
            }
          //  Log.v(TAG, "compressGrayPix: " + new String(chars));
        }
        int appendLength = 8 - width % 8;
        int widthBytes = appendLength != 8 ? width / 8 + 1 : width / 8;
        //Log.i(TAG, "compressGrayPix: width bytes = " + widthBytes + "width = " + width + " ################################################################################");
        byte[] result = new byte[widthBytes * height];
        int p = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < widthBytes; j++) {
                int location = i * widthBytes + j;
                byte[] bytes = new byte[8];
                if (j == widthBytes - 1 && appendLength != 8) {
                    System.arraycopy(src, location * 8 - p, bytes, 0, 8 - appendLength);
                    for (int k = 8 - appendLength; k < 8; k++) {
                        bytes[k] = (byte) 0xff;
                    }
                    p += appendLength;
                } else {
                    System.arraycopy(src, location * 8 - p, bytes, 0, 8);
                }
                byte b8 = Byte2byte(bytes);
                result[location] = b8;
            }
            /*StringBuilder line = new StringBuilder();
            for (int j = i * widthBytes; j < i * widthBytes + widthBytes; j++) {
                line.append(toBinaryString(result[j]))*//*.append(' ')*//*;
            }
            Log.v(TAG, "compressGrayPix: " + line.toString());*/
        }
        //Log.i(TAG, "################################################################################################################################");
        return result;
    }

    public static char[] toBinaryString(byte value) {
        char[] chars = new char[8];
        for (int i = 0; i < 8; i++) {
            chars[i] = (BINARY[i] == (value & BINARY[i])) ? ' ' : '0';
        }
        return chars;
    }


    private static byte Byte2byte(byte[] bytes) {
        byte result = 0;
        for (int k = 0; k < bytes.length; k++) {
            if (bytes[k] == 0) {
                result |= BINARY[k];
            }
        }
        return result;
    }
}
