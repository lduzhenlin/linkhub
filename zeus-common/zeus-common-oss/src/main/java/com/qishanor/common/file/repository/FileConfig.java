package com.qishanor.common.file.repository;

import cn.hutool.core.util.IdUtil;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Data
public class FileConfig {

    private Long configId;

    private String name;

    private String storageType="local";
    /**
     * 是否开启
     */
    private Boolean enable;

    /**
     * 默认的存储桶名称
     */
    private String bucketName="images";

    /**
     * 存储目录
     */
    private String dir;

    /**
     * 本地存储默认路径
     */
    private String basePath="/upload";

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
     * 自定义域名
     */
    private String customDomain;


    public String generateFileName(MultipartFile multipartFile) {
        String name = multipartFile.getOriginalFilename();
        String ext  = Objects.requireNonNull(name).substring(name.lastIndexOf("."));
//		return  UUID.randomUUID() + ext.toLowerCase();
        return IdUtil.getSnowflakeNextIdStr()+ext.toLowerCase();
    }

}
