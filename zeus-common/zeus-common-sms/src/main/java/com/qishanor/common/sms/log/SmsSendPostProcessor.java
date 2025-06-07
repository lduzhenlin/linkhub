package com.qishanor.common.sms.log;

import com.qishanor.common.sms.engine.SmsResult;
import jakarta.websocket.SendResult;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信发送后置处理器，例如用于记录发送记录等
 *
 * @author JQ棣
 *
 */
public interface SmsSendPostProcessor {

    /**
     * 短信发送之后执行
     *
     */
    void afterSend(SmsResult smsResult);
}
