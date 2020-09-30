package com.miaxis.livedetect.utils.debug.fps;

/**
 * @date: 2018/11/13 10:23
 * @author: zhang.yw
 * @project: FaceRecognition2
 */
public class FpsCounter {

    private long startTime;
    private int count;
    private int record;

    /**
     * 每秒调用次数
     *
     * @return f/s
     */
    public int signal() {
        count++;
        long now = System.currentTimeMillis();
        long diff = now - startTime;
        if (diff > 1000) {
            startTime = now;
            record = (int) (1000 * count / diff);
            count = 0;
        }
        return record;
    }
}
