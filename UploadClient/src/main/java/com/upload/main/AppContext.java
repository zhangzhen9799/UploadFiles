package com.upload.main;

import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 线程上下文
 */
@Log4j2
public class AppContext {

    /**
     * 任务队列
     */
    public static BlockingQueue<File> blockingQueueFile = new LinkedBlockingQueue<File>(100);

    /**
     * 去重
     */
    public static Set<String> FILTER_SET = new HashSet<>(10000);

    public static boolean add(String s) {

        try {

            if (FILTER_SET.size() > 9999) {

                FILTER_SET.clear();

            }

            if (FILTER_SET.contains(s)) {

                return false;

            }

            if (FILTER_SET.add(s)) {

                return true;

            }

        } catch (Exception e) {

            log.error("{}", e);

        }

        return false;

    }

}
