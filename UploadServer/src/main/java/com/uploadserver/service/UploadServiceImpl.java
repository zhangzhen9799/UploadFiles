package com.uploadserver.service;

import com.uploadserver.component.FileUpload;
import com.uploadserver.model.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private FileUpload fileUpload;

    /**
     * 文件上传接口实现
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @Override
    public FileInfo uploadFile(MultipartFile multipartFile) throws IOException {

        FileInfo fileInfo = fileUpload.saveFile(multipartFile);

        return fileInfo;

    }

}
