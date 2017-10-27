package com.uploadserver.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * yml配置文件中的全局常量
 */
@Data
@Component
@ConfigurationProperties(value = "app")
public class AppYml {

    private String fileDirectory;
    private String filePathFormat;
    private String fileNameFormat;

}
