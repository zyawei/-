package com.miaxis.btfingerprinterlibdemo;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @date: 2019/1/17 13:26
 * @author: zhang.yw
 * @project: BTFingerPrinterLibDemo
 */
public class GrayBitmap {

    public static Bitmap toGray(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int[] inPixels = new int[width * height];
        int[] outPixels = new int[width * height];
        bitmap.getPixels(inPixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        int index = 0;
        int means = getThreshold(inPixels, height, width);
        for (int row = 0; row < height; row++) {
            int ta = 0, tr = 0, tg = 0, tb = 0;
            for (int col = 0; col < width; col++) {
                index = row * width + col;
                ta = (inPixels[index] >> 24) & 0xff;
                tr = (inPixels[index] >> 16) & 0xff;
                tg = (inPixels[index] >> 8) & 0xff;
                tb = inPixels[index] & 0xff;
                if (tr > means) {
                    tr = tg = tb = 255; //white
                } else {
                    tr = tg = tb = 0; // black
                }
                outPixels[index] = (ta << 24) | (tr << 16) | (tg << 8) | tb;
            }
        }
        return Bitmap.createBitmap(outPixels, width, height, Bitmap.Config.ARGB_8888);
    }

    private static int getThreshold(int[] inPixels, int height, int width) {
        // maybe this value can reduce the calculation consume;
        long start = System.currentTimeMillis();

        int iniThreshold = 127;
        int finalThreshold = 0;
        int temp[] = new int[inPixels.length];
        for (int index = 0; index < inPixels.length; index++) {
            temp[index] = (inPixels[index] >> 16) & 0xff;
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
        Log.i("MX-Gray", "Final threshold  = " + finalThreshold);
        long endTime = System.currentTimeMillis() - start;
        Log.i("MX-Gray", "Time consumes : " + endTime);
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
}
