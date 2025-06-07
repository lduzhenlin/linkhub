package com.qishanor.common.sms;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import com.qishanor.common.sms.engine.SmsTemplate;
import com.qishanor.common.sms.engine.ali.SmsAliTemplate;
import com.qishanor.common.sms.engine.tencent.SmsTencentTemplate;
import com.qishanor.common.sms.log.SmsSendPostProcessor;
import com.qishanor.common.sms.log.SmsSendPostProcessorImpl;
import com.qishanor.common.sms.repository.SmsConfig;
import com.qishanor.common.sms.repository.SmsConfigManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;

@Configuration
public class SmsAutoConfiguration {



    @Bean
    public SmsSendPostProcessor smsSendPostProcessor(Environment env) {
        return new SmsSendPostProcessorImpl(env);
    }

    @Bean
    public SmsTemplate smsTemplate(Environment env, ApplicationContext context){
        SmsConfig smsConfig=SmsConfigManager.loadConfig(env);
        switch (smsConfig.getSupplier()) {
            case Constant.Supplier.ALI -> {
                return new SmsAliTemplate(smsConfig, context);
            }
            case Constant.Supplier.TENCENT -> {
                return new SmsTencentTemplate();
            }
        }

        //默认使用阿里云短信
        return new SmsAliTemplate(smsConfig,context);
    }
}
