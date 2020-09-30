package com.miaxis.livedetect.utils;

import java.util.Random;

/**
 * @date: 2018/11/27 13:54
 * @author: zhang.yw
 * @project: LiveDetection
 */
public class UnqueIntRandom {

    private Random random;
    private int[] cache;
    private int length;
    private int range;

    public UnqueIntRandom(int range) {
        random = new Random();
        cache = new int[range];
        this.range = range;
    }

    public int random() {
        if (cache.length == length) {
            throw new IllegalStateException("all cached !");
        }
        int i = random.nextInt(range);
        while (cached(i)) {
            i = random.nextInt(range);
        }
        cache[length] = i;
        length++;
        return i;
    }

    public void clear() {
        length = 0;
    }


    private boolean cached(int k) {
        for (int i = 0; i < length; i++) {
            if (cache[i] == k) {
                return true;
            }
        }
        return false;
    }


}
