package com.qishanor.common.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
//@Component
//@ConfigurationProperties(prefix = "file.storage")
public class FileStorageProperties {
    private String storageType;
    private Boolean enable;
    private String bucketName;
    private String basePath;
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private Boolean pathStyleAccess;
}