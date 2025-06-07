package com.qishanor.common.sms.engine;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public interface SmsTemplate {


    SmsResult send(String signName, String phoneNumber,String templateCode, Map<String, String> templateParam);

//    CompletableFuture<SmsResult> sendAsync(String phoneNumbers, String templateId, Map<String, String> templateParams);

}
