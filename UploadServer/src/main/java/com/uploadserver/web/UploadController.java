package com.uploadserver.web;

import com.uploadserver.model.FileInfo;
import com.uploadserver.service.UploadService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 上传文件控制器
 */
@Log4j2
@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * 单文件上传
     * @param file
     * @return
     */
    @RequestMapping(value = "/single-file", method = RequestMethod.POST)
    public ModelMap uploadFile(@RequestParam("file") MultipartFile file) {
        ModelMap modelMap = new ModelMap();
        FileInfo fileInfo = null;
        try {
            fileInfo = uploadService.uploadFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("{}", e.getMessage());
            modelMap.addAttribute("error", "Uploading Failed");
            return modelMap;
        }
        modelMap.addAttribute("file", fileInfo);
        return modelMap;
    }

    /**
     * 多文件上传
     * @param files
     * @return
     */
    @PostMapping("/multi-file")
    public ModelMap uploadMultiFile(@RequestParam("file") MultipartFile[] files) {
        ModelMap modelMap = new ModelMap();
        List<FileInfo> fileInfoList = new ArrayList<>();
        for (MultipartFile file : files) {
            FileInfo fileInfo = null;
            try {
                fileInfo = uploadService.uploadFile(file);
            } catch (IOException e) {
                e.printStackTrace();
                log.error("{}", e.getMessage());
                modelMap.addAttribute("error", "Uploading Failed");
                return modelMap;
            }
            fileInfoList.add(fileInfo);
        }
        modelMap.addAttribute("fileList", fileInfoList);
        return modelMap;
    }
}
