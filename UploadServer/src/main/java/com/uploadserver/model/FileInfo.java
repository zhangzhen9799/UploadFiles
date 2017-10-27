package com.uploadserver.model;

import lombok.Data;

@Data
public class FileInfo {

    /**
     * 文件名
     */
    private String fileName;
    /**
     * 原文件名
     */
    private String originalFileName;
    /**
     * 文件扩展名
     */
    private String fileExtensions;
    /**
     * 类型
     */
    private String contentType;
    /**
     * 文件大小 单位:字节（B）
     */
    private Long size;
    /**
     * 服务端绝对路径
     */
    private String absolutePath;
    /**
     * 相对路径
     */
    private String relativePath;
    /**
     * 时间
     */
    private String dateTime;

}