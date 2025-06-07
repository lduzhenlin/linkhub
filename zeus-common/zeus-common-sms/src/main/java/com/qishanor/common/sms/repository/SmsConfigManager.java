package com.qishanor.common.sms.repository;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.core.env.Environment;

public class SmsConfigManager {

    private static SmsConfig smsConfig=new SmsConfig();

    public static SmsConfig loadConfig(Environment env){
        smsConfig=SmsYamlConfigRepository.loadConfigFromYaml(env);
        loadFromDatabase(env);
        return smsConfig;
    }

    private static void loadFromDatabase(Environment env) {
        SmsConfig dbConfig =SmsDbConfigRepository.loadConfigFromDb(env);
        if(ObjectUtil.isNotEmpty(dbConfig)){
            BeanUtil.copyProperties(dbConfig,smsConfig);
        }
    }
}
