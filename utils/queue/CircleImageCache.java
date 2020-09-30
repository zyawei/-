package com.miaxis.livedetect.utils.queue;


import com.miaxis.image.MXImage;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CircleImageCache implements ImageCache {

    private CoverQueue<MXImage> imageCoverQueue = new CoverQueue<>(2);
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public MXImage take() throws InterruptedException {
        lock.lock();
        try {
            while (isEmpty()) {
                condition.await();
            }
            return imageCoverQueue.pull();
        } finally {
            lock.unlock();
        }
    }

    public void put(MXImage image) {
        lock.lock();
        try {
            imageCoverQueue.push(image);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        return imageCoverQueue.isEmpty();
    }
}
