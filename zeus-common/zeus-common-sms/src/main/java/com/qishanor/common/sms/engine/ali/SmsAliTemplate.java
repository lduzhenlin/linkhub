package com.qishanor.common.sms.engine.ali;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.qishanor.common.sms.engine.SmsResult;
import com.qishanor.common.sms.engine.SmsTemplate;
import com.qishanor.common.sms.log.SmsSendPostProcessor;
import com.qishanor.common.sms.repository.SmsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Map;

@Slf4j
public class SmsAliTemplate implements SmsTemplate {

    private ApplicationContext applicationContext;

    private IAcsClient client;

    private SmsConfig smsConfig;


    public SmsAliTemplate(SmsConfig config, ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.smsConfig=config;

        // 初始化SDK客户端
        DefaultProfile profile = DefaultProfile.getProfile(
                config.getRegionId(),
                config.getAccessKey(),
                config.getSecretKey()
        );
        this.client = new DefaultAcsClient(profile);

    }

    @Override
    public SmsResult send(String signName, String phoneNumber,String templateCode, Map<String, String> templateParam) {

        if(StrUtil.isBlank(signName)){
           signName= smsConfig.getSignName();
        }

        SmsResult smsResult=SmsResult.builder()
                .supplier(smsConfig.getSupplier())
                .regionId(smsConfig.getRegionId())
                .signName(signName)
                .phone(phoneNumber)
                .templateCode(templateCode)
                .templateParam(JSONUtil.toJsonStr(templateParam))
                .build();
        try {
            // 构建发送请求
            SendSmsRequest request = new SendSmsRequest();
            request.setSignName(signName);
            request.setPhoneNumbers(phoneNumber);
            request.setTemplateCode(templateCode);
            request.setTemplateParam(JSONUtil.toJsonStr(templateParam));

            // 发送短信
            SendSmsResponse response = client.getAcsResponse(request);

            // 处理响应
            if ("OK".equals(response.getCode())) {
                smsResult.setSuccess(response.getBizId(),response.getRequestId());
            } else {
                log.warn("短信发送失败:phone={},templateCode={}, code={}, message={}", phoneNumber,templateCode,response.getCode(), response.getMessage());
                smsResult.setFailed(response.getCode(),response.getMessage(),response.getRequestId());
            }
            return smsResult;
        } catch (ClientException e) {
            log.error("短信发送失败,调用阿里云SMS API异常:phone={},templateCode={}, code={}, message={}", phoneNumber,templateCode,e.getErrCode(), e.getErrMsg());
            log.error("调用阿里云SMS API异常", e);
            smsResult.setFailed(e.getErrCode(),e.getErrMsg(),e.getRequestId());
            return smsResult;
        }finally {
            //短信发送后，处理操作
            afterSend(smsResult);
        }
    }

    public void afterSend(SmsResult smsResult){
        // 获取所有SmsService类型的Bean
        Map<String, SmsSendPostProcessor> beans = applicationContext.getBeansOfType(SmsSendPostProcessor.class);

        beans.forEach((name, service) -> {
//            System.out.println("服务名称: " + name);
//            System.out.println("实现类: " + service.getClass().getName());
            service.afterSend(smsResult);
        });
    }
}
