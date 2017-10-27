package com.upload.threads;

import com.upload.main.AppContext;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 任务分发
 */
@Log4j2
@AllArgsConstructor
public class UploadThread implements Runnable {

    private int number;

    @Override
    public void run() {

        // 根据配置文件创建线程池
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(number);

        // 从队列中取任务执行
        while (true) {
            if (AppContext.blockingQueueFile.size() != 0) {
                try {
                    fixedThreadPool.execute(new UploadTask(AppContext.blockingQueueFile.take()));
                } catch (InterruptedException e) {
                    log.error("{}", e);
                }
            }
        }

    }


}
