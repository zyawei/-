package com.miaxis.livedetect.utils.queue;

/**
 * @date: 2018/11/15 11:54
 * @author: zhang.yw
 * @project: FaceRecognition2
 */
public class CoverQueue<T> {

    private final int MAX_COUNT;
    private int point;
    private int count;
    private Object[] lineQueue;

    public CoverQueue(int count) {
        MAX_COUNT = count;
        lineQueue = new Object[MAX_COUNT];
    }

    /**
     * 追加msg到队列尾部
     * 新的数据会把老的数据覆盖
     *
     * @param pointer 最新数据
     */
    public void push(T pointer) {
        point %= MAX_COUNT;
        lineQueue[point] = pointer;
        point++;
        if (count < MAX_COUNT) {
            count++;
        }
    }

    /**
     * @return 最老的数据 也就是MAX_COUNT条之前的数据
     * @see #MAX_COUNT MAX_COUNT > 队列的长度
     */
    @SuppressWarnings("unchecked")
    public T pull() {
        if (count == 0) {
            return null;
        } else if (count == MAX_COUNT) {
            count--;
            return (T) lineQueue[point % MAX_COUNT];
        } else {
            T t = (T) lineQueue[(MAX_COUNT + point - count) % MAX_COUNT];
            count--;
            return t;
        }
    }
    public boolean isEmpty() {
        return count == 0;
    }
}