package com.qishanor.common.core.exception;

import cn.dev33.satoken.exception.*;
import com.qishanor.common.core.util.R;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @description: 全局异常配置
 * @author: 周振林
 * @date: 2022-04-10
 * @Copyright: 博客：http://www.zhouzhenlin.com - 沉淀、分享、成长、让自己和他人都有所收获
 **/

@Slf4j
@ControllerAdvice
public class GlobalException {

    // 全局异常拦截（拦截项目中的所有异常）
    @ResponseBody
    @ExceptionHandler(SaTokenException.class)
    public Object handlerException(Exception e, HttpServletRequest request, HttpServletResponse response){

        // 打印堆栈，以供调试
        log.error("权限异常信息：",e);

        // 不同异常返回不同状态码
        if (e instanceof NotLoginException) {
            // 如果是未登录异常
            NotLoginException ee = (NotLoginException) e;
            return ResponseEntity.status(424).body(ee.getMessage());
        }
        else if(e instanceof NotRoleException) {
            // 如果是角色异常
            NotRoleException ee = (NotRoleException) e;
            return R.failed("无此角色：" + ee.getRole());
        }
        else if(e instanceof NotPermissionException) {
            // 如果是权限异常
            NotPermissionException ee = (NotPermissionException) e;
            return R.failed("无此权限：" + ee.getCode());
        }
//        else if(e instanceof DisableLoginException) {
//            // 如果是被封禁异常
//            DisableLoginException ee = (DisableLoginException) e;
//            return R.failed("账号被封禁：" + ee.getDisableTime() + "秒后解封");
//        }

        // 当做普通异常, 输出：500 + 异常信息，返回给前端
        return R.failed(e.getMessage());
    }

    // 全局异常拦截（拦截项目中的所有异常）
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Object globalException(Exception e, HttpServletRequest request, HttpServletResponse response){

        // 打印堆栈，以供调试
        log.error("异常信息：",e);

        // 当做普通异常, 输出：500 + 异常信息
        // 返回给前端
        return R.failed(e.getMessage());
    }



}
