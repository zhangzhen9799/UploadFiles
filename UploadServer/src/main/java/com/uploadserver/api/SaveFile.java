package com.uploadserver.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uploadserver.config.AppYml;
import com.uploadserver.model.FileInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
@Component
public class SaveFile {

    @Autowired
    private AppYml appYml;

    @Autowired
    private ObjectMapper objectMapper;

    public FileInfo saveFile(MultipartFile multipartFile) throws IOException {

        // 获取配置文件中的全局常量
        String fileDirectory = appYml.getFileDirectory();
        String filePathFormat = appYml.getFilePathFormat();
        String fileNameFormat = appYml.getFileNameFormat();

        // 根据日期时间生成文件路径和文件名称
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        DateTimeFormatter filePathDateTimeFormatter = DateTimeFormatter.ofPattern(filePathFormat);
        // DateTimeFormatter fileNameDateTimeFormatter = DateTimeFormatter.ofPattern(fileNameFormat);
        String dateTime = dateTimeFormatter.format(localDateTime);
        String filePath = localDateTime.format(filePathDateTimeFormatter);
        String fileName = multipartFile.getOriginalFilename();

        // 获取文件后缀名，此处直接使用源文件后缀名，而没有根据mimeType判断
        String fileExtensions = getFileExtension(multipartFile.getOriginalFilename());

        // 绝对路径
        String absolutePath = fileDirectory + filePath + fileName;
        // 相对路径
        String relativePath = filePath + fileName;
        // 存放位置
        String directory = fileDirectory + filePath;

        // 如果不存在目录，创建
        File file = new File(directory);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }

        file = new File(absolutePath);

        // 写文件
        multipartFile.transferTo(file);

        // 文件信息
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName(fileName);
        fileInfo.setOriginalFileName(multipartFile.getOriginalFilename());
        fileInfo.setFileExtensions(fileExtensions);
        fileInfo.setContentType(new MimetypesFileTypeMap().getContentType(file));
        fileInfo.setSize(file.length());
        fileInfo.setAbsolutePath(absolutePath);
        fileInfo.setRelativePath(relativePath);
        fileInfo.setDateTime(dateTime);

        // 记录日志
        logFileInfo(fileInfo);

        return fileInfo;
    }

    /**
     * 获取文件扩展名
     * @param fileName
     * @return
     */
    private String getFileExtension(String fileName) {
        int beginIndex = fileName.lastIndexOf(".");
        int endIndex = fileName.length();
        String extension = fileName.substring(beginIndex, endIndex);
        return extension;
    }

    private void logFileInfo(FileInfo fileInfo) throws JsonProcessingException {
        String fileInfoJson = objectMapper.writeValueAsString(fileInfo);
        log.info(fileInfoJson);
    }


}
