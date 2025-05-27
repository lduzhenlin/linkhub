package com.qishanor.common.data.satoken;

import com.qishanor.common.data.util.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 全局异常拦截
    @ExceptionHandler
    public Object handlerException(Exception e) {
        e.printStackTrace();
        return R.failed(e.getMessage());
    }
}
