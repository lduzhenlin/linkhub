package com.qishanor.controller;

import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONUtil;
import com.qishanor.common.core.util.R;
import com.qishanor.common.sms.engine.SmsResult;
import com.qishanor.common.sms.engine.SmsTemplate;
import com.qishanor.common.sms.repository.SmsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class SmsController {

    private final SmsTemplate smsTemplate;

    @RequestMapping("/sms")
    public Object sendSms(){


        return R.ok();
    }
}
