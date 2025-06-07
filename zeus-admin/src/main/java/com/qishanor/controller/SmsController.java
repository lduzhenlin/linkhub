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

        SmsConfig smsConfig=new SmsConfig();
        smsConfig.setSupplier("ali");
        smsConfig.setRegionId("cn-qingdao");
        smsConfig.setAccessKey("LTAI5tGdfdQNma1oYRVPzmv7");
        smsConfig.setSecretKey("UBSjsDeVd9JsJkrJGFw0j7UGUs7Myz");
        smsConfig.setSignName("启善智能");

//        SmsTemplate smsTemplate = new SmsAliTemplate(smsConfig);

        String phone = "13345092258";
        String templateCode = "SMS_278275295";
        HashMap<String,String> map=new HashMap<>();
        map.put("code","1234");
        String templateParam = JSONUtil.toJsonStr(map);

        SmsResult smsResult = smsTemplate.send(null,phone,templateCode,map);
        Console.log("sendResponse:" + smsResult);
        return R.ok();
    }
}
