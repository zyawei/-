package com.miaxis.livedetect.utils.debug;

/**
 * @date: 2018/11/13 16:24
 * @author: zhang.yw
 * @project: FaceRecognition2
 */
public class CpuMonitor extends Thread {

    @Override
    public void run() {
        super.run();
        while (!isInterrupted()) {
            String cpuRateDesc = CpuInfoUtils.getCPURateDesc();
            callBack.onChange(cpuRateDesc);
        }
    }

    private final CallBack callBack;

    public CpuMonitor(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack {
        void onChange(String info);
    }

}
