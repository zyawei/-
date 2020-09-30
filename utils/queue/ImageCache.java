package com.miaxis.livedetect.utils.queue;


import com.miaxis.image.MXImage;

/**
 * @date: 2018/11/13 13:30
 * @author: zhang.yw
 * @project: FaceRecognition2
 */
public interface ImageCache {

    MXImage take() throws InterruptedException;

    void put(MXImage faceInfo);

    boolean isEmpty();
}
