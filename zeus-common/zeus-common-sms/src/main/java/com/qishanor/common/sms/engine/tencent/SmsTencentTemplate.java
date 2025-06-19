package com.qishanor.common.sms.engine.tencent;

import com.qishanor.common.sms.engine.SmsResult;
import com.qishanor.common.sms.engine.SmsTemplate;

import java.util.Map;

public class SmsTencentTemplate implements SmsTemplate {


    @Override
    public SmsResult send(String phoneNumber, String templateCode, Map<String, Object> templateParam) {
        return send(null,phoneNumber,templateCode,templateParam);
    }

    @Override
    public SmsResult send(String signName, String phoneNumber, String templateCode, Map<String, Object> templateParam) {
        return null;
    }
}
