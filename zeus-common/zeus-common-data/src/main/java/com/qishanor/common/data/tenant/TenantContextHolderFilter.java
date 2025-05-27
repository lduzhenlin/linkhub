/*
 *    Copyright (c) 2018-2025, zeus All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: zeus
 */

package com.qishanor.common.data.tenant;

import cn.hutool.core.util.StrUtil;
import com.qishanor.common.data.constant.CacheConstant;
import com.qishanor.common.data.constant.CommonConstant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

/**
 * 租户过滤器
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantContextHolderFilter extends GenericFilterBean {

    private final static String UNDEFINED_STR = "undefined";

//    private final CacheManager cacheManager;

    @Override
    @SneakyThrows
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String headerTenantId = request.getHeader(CommonConstant.TENANT_ID);
        String paramTenantId = request.getParameter(CommonConstant.TENANT_ID);

        log.debug("获取header中的租户ID为:{}", headerTenantId);

        if (StrUtil.isNotBlank(headerTenantId) && !StrUtil.equals(UNDEFINED_STR, headerTenantId)) {
            TenantContextHolder.setTenantId(Long.parseLong(headerTenantId));
        } else if (StrUtil.isNotBlank(paramTenantId) && !StrUtil.equals(UNDEFINED_STR, paramTenantId)) {
            TenantContextHolder.setTenantId(Long.parseLong(paramTenantId));
        } else {
            TenantContextHolder.setTenantId(CommonConstant.TENANT_ID_1);
        }

//        if (!checkTenantStatus(request, response)) {
//            TenantContextHolder.clear();
//            return;
//        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContextHolder.clear();
        }
    }

    /**
     * 检查租户状态
     *
     * @param request  请求
     * @param response 响应
     * @return boolean
     */
    private boolean checkTenantStatus(HttpServletRequest request, HttpServletResponse response) {
        // 如果是获取租户列表请求跳过检查
        if (StrUtil.containsAnyIgnoreCase(request.getRequestURI(), "/tenant/list")) {
            return true;
        }

        // 从缓存管理器中获取租户详情缓存，如果缓存不存在也放行（可能是缓存未初始化）
//        Cache cache = cacheManager.getCache(CacheConstant.TENANT_DETAIL);
//        if (cache == null) {
//            return true;
//        }

        //获取所有租户列表，如果列表为空则放行
//        List<SysTenant> tenantList = cache.get(SimpleKey.EMPTY, List.class);
//        if (CollUtil.isEmpty(tenantList)) {
//            return true;
//        }

        //检查当前租户ID（从TenantContextHolder获取）是否存在于有效租户列表中
//        boolean exist = tenantList.stream().anyMatch(tenant -> NumberUtil.equals(tenant.getId(), TenantContextHolder.getTenantId()));
//        if (exist) {
//            return true;
//        }

        response.setStatus(HttpStatus.UPGRADE_REQUIRED.value());
        return false;
    }

}
