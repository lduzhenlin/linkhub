package com.qishanor.common.data.tenant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 多租户配置
 *
 * 配置文件方式 需要进行租户过滤的表集合
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "zeus.tenant")
public class ZeusTenantConfigProperties {

    /**
     * 维护租户列名称
     */
    private String column = "tenant_id";

    /**
     * 多租户需要过滤的数据表集合
     */
    private List<String> tables = new ArrayList<>();

}

