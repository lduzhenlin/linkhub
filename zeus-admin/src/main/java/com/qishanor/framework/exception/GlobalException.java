package com.qishanor.framework.exception;

import com.qishanor.common.core.util.R;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        //1、result 中封装了所有错误信息
        BindException ex;
        BindingResult result = e.getBindingResult();

        List<FieldError> errors = result.getFieldErrors();
        Map<String, String> map = new HashMap<>();
        for (FieldError error : errors) {
            String field = error.getField();
            String message = error.getDefaultMessage();
            map.put(field, message);
        }

        return R.failed(map, "参数错误");
    }

    // 最终的兜底
    @ExceptionHandler(Throwable.class)
    public R error(Throwable e) {
        System.out.println("【全局】 - Exception处理" + e.getClass());
        return R.failed(e.getMessage());
    }
}
