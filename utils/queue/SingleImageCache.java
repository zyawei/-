package com.miaxis.livedetect.utils.queue;


import com.miaxis.image.MXImage;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SingleImageCache implements ImageCache {
    private volatile boolean empty = true;
    private MXImage image;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public SingleImageCache() {

    }

    public MXImage take() throws InterruptedException {
        lock.lock();
        try {
            while (empty) {
                condition.await();
            }
            empty = true;
            return image;
        } finally {
            lock.unlock();
        }
    }

    public void put(MXImage bytes) {
        if (empty){
            lock.lock();
            try {
                this.image = bytes;
                this.empty = false;
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

    public boolean isEmpty() {
        lock.lock();
        try {
            return empty;
        } finally {
            lock.unlock();
        }
    }
}
