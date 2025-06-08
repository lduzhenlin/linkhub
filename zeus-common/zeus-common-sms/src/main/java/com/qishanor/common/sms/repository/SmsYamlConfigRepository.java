package com.qishanor.common.sms.repository;

import cn.hutool.core.convert.Convert;
import org.springframework.core.env.Environment;

public class SmsYamlConfigRepository {

    public static SmsConfig loadConfigFromYaml(Environment env) {
        SmsConfig smsConfig=new SmsConfig();
        if (env.getProperty("sms.supplier.enable") != null) {
            smsConfig.setEnable(Convert.toBool(env.getProperty("file.storage.enable")));
        }
        if(smsConfig.getEnable()==null||!smsConfig.getEnable())return smsConfig;

        if (env.getProperty("sms.supplier.type") != null) {
            smsConfig.setSupplier(env.getProperty("sms.supplier.type"));
        }
        if (env.getProperty("sms.supplier.signName") != null) {
            smsConfig.setSignName(env.getProperty("sms.supplier.signName"));
        }
        if (env.getProperty("sms.supplier.regionId") != null) {
            smsConfig.setRegionId(env.getProperty("sms.supplier.regionId"));
        }
        if (env.getProperty("sms.supplier.accessKey") != null) {
            smsConfig.setAccessKey(env.getProperty("sms.supplier.accessKey"));
        }
        if (env.getProperty("sms.supplier.secretKey") != null) {
            smsConfig.setSecretKey(env.getProperty("sms.supplier.secretKey"));
        }


        return smsConfig;
    }
}
