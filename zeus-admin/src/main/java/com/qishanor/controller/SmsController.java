package com.qishanor.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.qishanor.common.core.util.R;
import com.qishanor.common.sms.Constant;
import com.qishanor.common.sms.engine.SmsResult;
import com.qishanor.common.sms.engine.SmsTemplate;
import com.qishanor.common.sms.repository.SmsConfig;
import com.qishanor.framework.util.VerifyCodeCacheUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sms")
public class SmsController {

    private final SmsTemplate smsTemplate;

    interface SmsConstant{
        String template_ForgotPassword = "SMS_489465138";
    }

    @SaIgnore
    @RequestMapping("/forgotPassword")
    public Object sendForgotPasswordSms(String phone){

        // 生成安全的随机4位数字
        int randomNum = RandomUtil.randomInt(1000,9999);
        Map<String, Object> paramMap = MapUtil.<String,Object>builder().put("code",randomNum).build();

        SmsResult smsResult=smsTemplate.send(phone, SmsConstant.template_ForgotPassword, paramMap);


        if(smsResult.isSuccess()){
            // 存储到缓存中，键为手机号，值为验证码
            VerifyCodeCacheUtil.put(phone, String.valueOf(randomNum));
            return R.ok();
        }else{
            return R.failed(smsResult.getReponseMessage());
        }
    }
}
