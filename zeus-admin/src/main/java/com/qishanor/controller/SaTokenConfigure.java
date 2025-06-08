package com.qishanor.controller;


import cn.dev33.satoken.interceptor.SaInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description: SaToken 注解式鉴权
 **/

@Slf4j
//@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {

    public SaTokenConfigure() {
        log.info("SaTokenConfigure 已初始化"); // 添加日志
    }

    // 注册Sa-Token的注解拦截器，打开注解式鉴权功能
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册注解拦截器
        registry.addInterceptor(new SaInterceptor())
                .addPathPatterns("/**");// 拦截所有请求

        log.info("Sa-Token 拦截器已注册"); // 添加日志确认
    }
}
