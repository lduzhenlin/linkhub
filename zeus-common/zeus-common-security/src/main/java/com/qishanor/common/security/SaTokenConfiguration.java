package com.qishanor.common.security;


import cn.dev33.satoken.interceptor.SaInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description: SaToken 注解式鉴权
 **/

@Slf4j
@Configuration
public class SaTokenConfiguration {


    @Bean
    public SaTokenConfigure saTokenConfigure() {
        return new SaTokenConfigure();
    }
}
