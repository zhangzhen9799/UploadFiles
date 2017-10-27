package com.upload.threads;

import com.upload.main.AppContext;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.File;

/**
 * 实时文件扫描
 */
@Log4j2
@AllArgsConstructor
public class ScanThread implements Runnable {

    private File file;

    @Override
    public void run() {
        while (true) {
            showDirectory(file);
            try {
                Thread.sleep(1000 * 10);
            } catch (InterruptedException e) {
                log.error("{}", e);
            }
        }

    }

    /**
     * 递归遍历文件
     * @param file
     */
    public static void showDirectory(File file) {
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                showDirectory(f);
            } else {
                log.info("FilePath:{}", f.getAbsolutePath());
                try {
                    // 扫描到文件添加到任务队列
                    if (!AppContext.blockingQueueFile.contains(f)) {
                        if (AppContext.add(f.getName())) {
                            AppContext.blockingQueueFile.put(f);
                        }
                    }
                } catch (InterruptedException e) {
                    log.error("{}", e);
                }
            }
        }
    }

}
