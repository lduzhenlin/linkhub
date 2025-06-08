package com.qishanor.controller;

import com.qishanor.common.core.util.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler
    public Object handlerException(Exception e) {
        e.printStackTrace();
        return R.failed(e.getMessage());
    }
}
