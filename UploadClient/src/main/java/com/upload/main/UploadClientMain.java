package com.upload.main;

import com.upload.config.SystemConfiguration;
import com.upload.threads.ScanThread;
import com.upload.threads.UploadThread;

import java.io.File;

public class UploadClientMain {

    public static void main(String[] args) {

        // 加载配置
        init();

        // 文件扫描
        scan();

        // 文件上传
        upload();

    }

    /**
     * 加载配置
     */
    private static void init() {

        SystemConfiguration.getInstance();

    }

    /**
     * 文件扫描
     */
    private static void scan() {

        String filePath = SystemConfiguration.getInstance().getProperty("Path");
        File file = new File(filePath);
        ScanThread scanThread = new ScanThread(file);
        Thread thread = new Thread(scanThread);
        thread.start();

    }

    /**
     * 文件上传
     */
    private static void upload() {

        int number = Integer.parseInt(SystemConfiguration.getInstance().getProperty("ThreadNumber"));
        UploadThread uploadThread = new UploadThread(number);
        Thread thread = new Thread(uploadThread);
        thread.start();

    }

}
