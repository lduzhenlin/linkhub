package com.qishanor.common.sms.log;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.qishanor.common.data.tenant.TenantContextHolder;
import com.qishanor.common.sms.engine.SmsResult;
import com.qishanor.common.sms.repository.SmsDbConfigRepository;
import jakarta.websocket.SendResult;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class SmsSendPostProcessorImpl implements SmsSendPostProcessor {

    private final Environment env;

    @Override
    public void afterSend( SmsResult smsResult) {

        SmsSendLog smsSendLog=new SmsSendLog();
        BeanUtil.copyProperties(smsResult,smsSendLog);
        smsSendLog.setLogId(IdUtil.getSnowflakeNextId());
        smsSendLog.setCreateTime(LocalDateTime.now());
        smsSendLog.setUpdateTime(LocalDateTime.now());
        smsSendLog.setTenantId(TenantContextHolder.getTenantId());

        SmsDbConfigRepository.saveSmsSendLog(env,smsSendLog);
    }


}
