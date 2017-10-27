package com.uploadserver.api;

import com.uploadserver.model.FileInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Log4j2
@RestController
public class UploadAPI {

    @Autowired
    SaveFile saveFile;

    @PostMapping("/api/upload")
    public ModelMap uploadFile(@RequestParam("file") MultipartFile file) {
        ModelMap modelMap = new ModelMap();
        FileInfo fileInfo = null;
        try {
            fileInfo = saveFile.saveFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("{}", e.getMessage());
            modelMap.addAttribute("error", "Uploading Failed");
            return modelMap;
        }
        modelMap.addAttribute("file", fileInfo);
        return modelMap;
    }

}
