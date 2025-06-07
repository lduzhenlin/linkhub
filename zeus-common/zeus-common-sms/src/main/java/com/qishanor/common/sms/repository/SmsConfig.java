package com.qishanor.common.sms.repository;

import lombok.Data;

@Data
public class SmsConfig {

    private Boolean enable=Boolean.FALSE;

    private String supplier="ali";

    private String regionId;

    private String accessKey;

    private String secretKey;

    private String signName;

}
