package com.uploadserver.configuration;

import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ErrorPageRegistrar;
import org.springframework.boot.web.servlet.ErrorPageRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * Http 错误配置
 */
@Configuration
public class ErrorConfiguration {

    @Bean
    public ErrorPageRegistrar errorPageRegistrar() {
        return new MyErrorPageRegistrar();
    }

    private static class MyErrorPageRegistrar implements ErrorPageRegistrar {

        @Override
        public void registerErrorPages(ErrorPageRegistry registry) {

            registry.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/error/400"));
            registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"));
            registry.addErrorPages(new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/error/405"));
            registry.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500"));
            registry.addErrorPages(new ErrorPage(HttpStatus.BAD_GATEWAY, "/error/502"));
            registry.addErrorPages(new ErrorPage(HttpStatus.SERVICE_UNAVAILABLE, "/error/503"));

        }

    }

}
