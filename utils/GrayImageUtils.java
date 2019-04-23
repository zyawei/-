package com.miaxis.btfingerprinterlibdemo;

/**
 * @date: 2019/1/16 15:07
 * @author: zhang.yw
 * @project: BTFingerPrinterLibDemo
 */

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yawei
 * @data on 18-6-7  下午2:44
 * @project panowin
 * @org www.komlin.com
 * @email zyawei@live.com
 */
public class GrayImageUtils {
    private static final String TAG = "GrayImageUtils";

    public static Bitmap getGrayBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        byte[] grayData = getGrayData(bitmap);
        int[] argb = new int[width * height];
        for (int i = 0; i < argb.length; i++) {
            argb[i] = argb2Pix(new byte[]{(byte) 0xff, grayData[i], grayData[i], grayData[i]});
        }
        return Bitmap.createBitmap(argb, width, height, Bitmap.Config.ARGB_8888);
    }

    public static Bitmap getBinarizationBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        byte[] grayData = getBinarizationData(bitmap);
        int[] argb = new int[width * height];
        for (int i = 0; i < argb.length; i++) {
            argb[i] = argb2Pix(new byte[]{(byte) 0xff, grayData[i], grayData[i], grayData[i]});
        }
        return Bitmap.createBitmap(argb, width, height, Bitmap.Config.ARGB_8888);
    }

    public static byte[] getGrayData(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] argb = new int[width * height];
        bitmap.getPixels(argb, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        return getGrayData(argb, width, height);
    }

    public static byte[] getBinarizationData(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] argb = new int[width * height];
        bitmap.getPixels(argb, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        return getBinarizationData(argb, width, height);
    }

    public static byte[] getGrayData(int[] argb, int width, int height) {
        byte[] brightness = new byte[width * height];
        for (int i = 0; i < argb.length; i++) {
            brightness[i] = getPixGray(pix2Argb(argb[i]));
        }
        return brightness;
    }

    public static byte[] getBinarizationData(int[] argb, int width, int height) {
        byte[] gray = new byte[width * height];
        for (int i = 0; i < argb.length; i++) {
            gray[i] = getPixGray(pix2Argb(argb[i]));
        }
        int threshold = getThreshold(gray);
        for (int i = 0; i < argb.length; i++) {
            if ((gray[i] & 0xff) > threshold) {
                gray[i] = (byte) 0xff;
            } else {
                gray[i] = 0;
            }
        }
        return gray;
    }

    private static int getThreshold(byte[] inPixels) {
        // maybe this value can reduce the calculation consume;
        long start = System.currentTimeMillis();

        int iniThreshold = 127;
        int finalThreshold = 0;
        int temp[] = new int[inPixels.length];
        for (int index = 0; index < inPixels.length; index++) {
            temp[index] = (inPixels[index] /*>> 16*/) & 0xff;
        }
        List<Integer> sub1 = new ArrayList<>();
        List<Integer> sub2 = new ArrayList<>();
        int means1, means2;
        while (finalThreshold != iniThreshold) {
            finalThreshold = iniThreshold;
            for (int i = 0; i < temp.length; i++) {
                if (temp[i] <= iniThreshold) {
                    sub1.add(temp[i]);
                } else {
                    sub2.add(temp[i]);
                }
            }
            means1 = getMeans(sub1);
            means2 = getMeans(sub2);
            sub1.clear();
            sub2.clear();
            iniThreshold = (means1 + means2) / 2;
        }
        Log.i(TAG, "Final threshold  = " + finalThreshold);
        long endTime = System.currentTimeMillis() - start;
        Log.i(TAG, "Time consumes : " + endTime);
        return finalThreshold;
    }

    private static int getMeans(List<Integer> data) {
        int result = 0;
        int size = data.size();
        for (Integer i : data) {
            result += i;
        }
        return (result / size);
    }

    private static byte getPixGray(byte[] argb) {
        return (byte) ((argb[1] & 0xff) * 0.299 + (argb[2] & 0xff) * 0.578 + (argb[3] & 0xff) * 0.114);
    }

    private static int argb2Pix(byte[] bytes) {
        return (bytes[0] << 24 & 0xff000000) | (bytes[1] << 16 & 0x00ff0000) | (bytes[2] << 8 & 0xff00) | (bytes[3] & 0xff);
    }

    private static byte[] pix2Argb(int color) {
        byte[] argb = new byte[4];
        argb[0] = (byte) (color >> 24);
        argb[1] = (byte) (color >> 16);
        argb[2] = (byte) (color >> 8);
        argb[3] = (byte) (color);
        return argb;
    }
}
