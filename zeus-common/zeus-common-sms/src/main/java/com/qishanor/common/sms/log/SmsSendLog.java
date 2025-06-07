package com.qishanor.common.sms.log;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsSendLog {

    private Long configId;

    private String supplier;

    private String regionId;

    private String signName;

    private String phone;

    private String templateCode;

    private String templateParam;


    private Boolean success;
    private String reponseCode;
    private String reponseMessage;
    private String bizId;
    private String requestId;


    private LocalDateTime createTime;
    private String createBy;
    private LocalDateTime updateTime;
    private String updateBy;
    private String delFlag;
    private Long tenantId;
}
