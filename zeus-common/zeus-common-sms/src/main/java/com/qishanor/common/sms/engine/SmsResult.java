package com.qishanor.common.sms.engine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// SmsResult.java
@Data  // 生成 getter/setter/toString 等方法
@Builder(toBuilder = true)  // 启用构建器模式并支持 toBuilder()
@AllArgsConstructor  // 全参构造函数
@NoArgsConstructor  // 无参构造函数
public class SmsResult {

    private String supplier;
    private String regionId;
    private String signName;
    private String phone;
    private String templateCode;
    private String templateParam;


    /**
     * 发送是否成功
     */
    private boolean success;

    /**
     * 状态码
     * - 阿里云格式: OK/错误码
     * - 腾讯云格式: 0/错误码
     */
    private String reponseCode;

    /**
     * 状态消息
     */
    private String reponseMessage;

    /**
     * 业务ID（阿里云返回的BizId，腾讯云返回的Extend）
     */
    private String bizId;

    /**
     * 请求ID（用于追踪问题）
     */
    private String requestId;

    // 自定义方法：快速设置成功信息
    public SmsResult setSuccess(String bizId, String requestId) {
        this.success = true;
        this.reponseCode = "OK";
        this.reponseMessage = "发送成功";
        this.bizId = bizId;
        this.requestId = requestId;
        return this;
    }

    // 自定义方法：快速设置错误信息
    public SmsResult setFailed(String errorCode, String errorMessage,String requestId) {
        this.success = false;
        this.reponseCode = errorCode;
        this.reponseMessage = errorMessage;
        this.requestId = requestId;
        return this;
    }


}