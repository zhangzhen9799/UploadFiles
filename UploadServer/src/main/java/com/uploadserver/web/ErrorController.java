package com.uploadserver.web;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Http error
 */
@RestController
@RequestMapping(value = "error")
public class ErrorController {

    @RequestMapping(value = "/{httpStatusCode:\\d+}", method = RequestMethod.GET)
    public ModelMap httpStatusCode(@PathVariable(name = "httpStatusCode", required = true) Integer httpStatusCode) {

        ModelMap modelMap = new ModelMap();

        String message;
        switch (httpStatusCode) {
            case 400:
                message = "BAD_REQUEST";
                break;
            case 404:
                message = "NOT_FOUND";
                break;
            case 405:
                message = "METHOD_NOT_ALLOWED";
                break;
            case 500:
                message = "INTERNAL_SERVER_ERROR";
                break;
            case 502:
                message = "BAD_GATEWAY";
                break;
            case 503:
                message = "SERVICE_UNAVAILABLE";
                break;
            default:
                message = "OK";
                break;
        }

        Map<String, Object> error = new HashMap<>();
        error.put("code", httpStatusCode);
        error.put("message", message);
        modelMap.addAttribute("error", error);

        return modelMap;
    }
}
