package com.uploadserver.web;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class AppController {

    /**
     * Hello World ！
     * @return
     */
    @GetMapping("")
    public ModelMap app() {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("Hello World ！");
        return modelMap;
    }
}
