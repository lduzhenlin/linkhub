package com.qishanor.common.data.tenant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;

/**

 * 租户信息拦截
 */
@Configuration
public class ZeusTenantConfiguration {

    @Bean
    public ClientHttpRequestInterceptor zeusTenantRequestInterceptor() {
        return new TenantRequestInterceptor();
    }

}