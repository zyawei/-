package com.integratedbiometrics.HerofunFpSample;


import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 * @author yawei
 */
public class CustomCrashHandler implements UncaughtExceptionHandler {
    private final Thread.UncaughtExceptionHandler mDefaultHandler;
    private Application application;


    CustomCrashHandler(Application application) {
        this.application = application;
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }



    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        }
    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        collectCrashLog(ex);
        exit();
        return true;
    }


    private void exit() {
        System.exit(0);
    }


    private void collectCrashLog( Throwable ex) {
        String versionInfo = collectDeviceInfo();
        String exMessage = tryCollectMessageByWrite(ex);
        if (exMessage == null) {
            exMessage = tryCollectMessageByEx(ex);
        }
        if (exMessage == null) {
            return;
        }
        String msgUpload = versionInfo + "Crash Info : \n" + exMessage;
        writeExMessageToFile(msgUpload);
    }

    private void writeExMessageToFile(String msg) {
        try {
            File file = new File("/sdcard/crash.log");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(msg.getBytes());
            fos.flush();
            fos.close();
        } catch (Exception ignored) {

        }
    }

    private String collectDeviceInfo() {
        try {
            PackageManager pm = application.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(application.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                return "Version Name : [" + pi.versionName + "]\nVersion Code : [" + pi.versionCode + "]\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String tryCollectMessageByWrite(Throwable ex) {
        String result = null;
        try {
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ex.printStackTrace(printWriter);
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.close();
            result = writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String tryCollectMessageByEx(Throwable ex) {
        String result = null;
        try {
            StringBuilder msgBuild = new StringBuilder();
            Throwable cause = ex;
            while (cause != null) {
                msgBuild.append(cause.toString()).append('\n');
                StackTraceElement[] stackTrace = cause.getStackTrace();
                for (StackTraceElement e : stackTrace) {
                    msgBuild.append(e).append('\n');
                }
                cause = cause.getCause();
            }
            result = msgBuild.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}