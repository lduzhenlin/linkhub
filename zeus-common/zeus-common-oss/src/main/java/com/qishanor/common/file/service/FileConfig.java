package com.qishanor.common.file.service;

import lombok.Data;

@Data
public class FileConfig {

    private Long id;

    private String storageType;
    /**
     * 是否开启
     */
    private Boolean enable;

    /**
     * 默认的存储桶名称
     */
    private String bucketName;

    /**
     * 本地存储默认路径
     */
    private String basePath;

    /**
     * 对象存储服务的URL
     */
    private String endpoint;
    /**
     * 访问key
     */
    private String accessKey;

    /**
     * 密钥key
     */
    private String secretKey;
    /**
     * 路径风格
     * true path-style nginx 反向代理和S3默认支持 pathStyle {http://endpoint/bucketname} false
     supports virtual-hosted-style 阿里云等需要配置为 virtual-hosted-style
     */
//    @Schema(description="路径风格")
    private Boolean pathStyleAccess;

    /**
     * 区域
     */
    private String region;

    /**
     * 自定义域名
     */
    private String customDomain;

    /**
     * 应用ID
     */
    private String appId;

}
