package com.upload.threads;

import com.upload.utils.HttpUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.File;

/**
 * 任务执行
 */
@Log4j2
@AllArgsConstructor
public class UploadTask implements Runnable {

    private File file;

    @Override
    public void run() {

        HttpUtils httpUtils = new HttpUtils();
        String result = httpUtils.uploadSingle(file);
        log.info(result);
        if ("".equals(result)) {
            result = httpUtils.uploadSingle(file);
            log.info(result);
            if ("".equals(result)) {
                // 上传失败处理有待待改进！！！
                // 删除文件！！！
                file.delete();
                log.info("上传失败！！已删除:{}", file.getName());
            } else {
                // 删除文件！！！
                file.delete();
                log.info("已删除:{}", file.getName());
            }
        } else {
            // 删除文件！！！
            file.delete();
            log.info("已删除:{}", file.getName());
        }

    }

}
