package com.uploadserver.service;

import com.uploadserver.model.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {

    /**
     * 文件上传接口
     * @param multipartFile
     * @return
     * @throws IOException
     */
    FileInfo uploadFile(MultipartFile multipartFile) throws IOException;

}
