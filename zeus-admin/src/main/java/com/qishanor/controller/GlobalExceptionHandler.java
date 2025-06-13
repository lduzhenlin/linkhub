package com.qishanor.controller;

import cn.dev33.satoken.exception.*;
import cn.dev33.satoken.util.SaResult;
import com.qishanor.common.core.util.R;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 拦截：未登录异常
    @ExceptionHandler(NotLoginException.class)
    public Object handlerException(NotLoginException e, HttpServletRequest request) {

        // 打印堆栈，以供调试
//        e.printStackTrace();

        Map<String, Object> result = new HashMap<>();

        // 记录异常信息
        result.put("code", 401);
        result.put("message", "未登录或token无效");
        result.put("type", e.getType());  // token 异常类型

        // 记录请求信息
        result.put("requestUrl", request.getRequestURL().toString());
        result.put("requestMethod", request.getMethod());
        result.put("remoteAddr", request.getRemoteAddr());
//        result.put("headers", getRequestHeaders(request));  // 自定义方法获取请求头

        // 记录时间戳
        result.put("timestamp", System.currentTimeMillis());

        // 打印详细日志
        System.out.println("未登录异常: " + result);

//        return result;

        // 返回给前端
        return SaResult.error(e.getMessage());
    }

    // 拦截：缺少权限异常
    @ExceptionHandler(NotPermissionException.class)
    public SaResult handlerException(NotPermissionException e) {
        e.printStackTrace();
        return SaResult.error("缺少权限：" + e.getPermission());
    }

    // 拦截：缺少角色异常
    @ExceptionHandler(NotRoleException.class)
    public SaResult handlerException(NotRoleException e) {
        e.printStackTrace();
        return SaResult.error("缺少角色：" + e.getRole());
    }

    // 拦截：二级认证校验失败异常
    @ExceptionHandler(NotSafeException.class)
    public SaResult handlerException(NotSafeException e) {
        e.printStackTrace();
        return SaResult.error("二级认证校验失败：" + e.getService());
    }

    // 拦截：服务封禁异常
    @ExceptionHandler(DisableServiceException.class)
    public SaResult handlerException(DisableServiceException e) {
        e.printStackTrace();
        return SaResult.error("当前账号 " + e.getService() + " 服务已被封禁 (level=" + e.getLevel() + ")：" + e.getDisableTime() + "秒后解封");
    }

    // 拦截：Http Basic 校验失败异常
    @ExceptionHandler(NotHttpBasicAuthException.class)
    public SaResult handlerException(NotHttpBasicAuthException e) {
        e.printStackTrace();
        return SaResult.error(e.getMessage());
    }

    // 拦截：其它所有异常
//    @ExceptionHandler(Exception.class)
//    public SaResult handlerException(Exception e) {
//        e.printStackTrace();
//        return SaResult.error(e.getMessage());
//    }

    // 全局异常拦截
    @ExceptionHandler
    public Object handlerException(Exception e,HttpServletRequest request) {
        System.out.println("捕获到异常: " + e.getClass().getName());
        System.out.println("请求URL: " + request.getRequestURL());
        e.printStackTrace();
        return R.failed(e.getMessage());
    }
}
