package com.qishanor.common.security;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
public class SaTokenConfigure implements WebMvcConfigurer {

    public SaTokenConfigure() {
        log.info("SaTokenConfigure 已初始化"); // 添加日志
    }

    // 注册拦截器 拦截所有请求，如果是api接口可以在方法上增加@SaIgnore 已经排除静态资源
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/js/**",
                        "/css/**",
                        "/image/**",
                        "/fonts/**",
                        "/favicon.ico",
                        "/",                    // 排除首页
                        "/api/user/login",     // 排除用户登录接口
                        "/api/user/register",  // 排除用户注册接口
                        "/api/user/isLogin",   // 排除检查登录状态接口
                        "/api/sms/register",   // 排除发送注册短信接口
                        "/api/sms/forgotPassword", // 排除发送找回密码短信接口
                        "/api/category/list",  // 排除分类列表接口，允许未登录用户访问
                        "/api/link/list",      // 排除链接列表接口，允许未登录用户访问
                        "/api/link/getById",   // 排除根据ID获取链接接口，允许未登录用户访问
                        "/api/link/shared"     // 排除共享链接接口，允许未登录用户访问
//                        "/**/*.js",
//                        "/**/*.css",
//                        "/**/*.png",
//                        "/**/*.jpg",
//                        "/**/*.jpeg",

                );
    }
}
