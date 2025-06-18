
package com.qishanor.common.data.tenant;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.qishanor.common.core.constant.CacheConstant;
import com.qishanor.common.core.constant.CommonConstant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 租户过滤器
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantContextHolderFilter extends GenericFilterBean  {

    private final static String UNDEFINED_STR = "undefined";

    private final CacheManager cacheManager;

    @Override
    @SneakyThrows
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String headerTenantId = request.getHeader(CommonConstant.TENANT_ID);
        String paramTenantId = request.getParameter(CommonConstant.TENANT_ID);

        log.debug("获取header中的租户ID为:{}", headerTenantId);

        if (StrUtil.isNotBlank(headerTenantId) && !StrUtil.equals(UNDEFINED_STR, headerTenantId)&& !StrUtil.equals("null", headerTenantId)) {
            TenantContextHolder.setTenantId(Long.parseLong(headerTenantId));
        } else if (StrUtil.isNotBlank(paramTenantId) && !StrUtil.equals(UNDEFINED_STR, paramTenantId)&& !StrUtil.equals("null", headerTenantId)) {
            TenantContextHolder.setTenantId(Long.parseLong(paramTenantId));
        }
        else {
            TenantContextHolder.setTenantId(null);
        }

        if (!isValidTenantStatus(request, response)) {
            TenantContextHolder.clear();
            return;
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContextHolder.clear();
        }
    }


    /**
     * 检查租户状态是否有效
     *
     * @param request  请求
     * @param response 响应
     * @return boolean
     */
    private boolean isValidTenantStatus(HttpServletRequest request, HttpServletResponse response) {
//        Console.log(request.getRequestURI());
//        if("/".equals(request.getRequestURI())){return true;}
        // 如果是获取租户列表请求跳过检查
//        if (StrUtil.containsAnyIgnoreCase(request.getRequestURI(), "/user/register","/user/login","/user/isLogin","/file/*")) {
//            return true;
//        }

        //如果是租户ID不为空，则进行下面操作租户ID的验证
        if(ObjUtil.isEmpty(TenantContextHolder.getTenantId())){
            return true;
        }

        // 从缓存管理器中获取租户详情缓存，如果缓存不存在也放行（可能是缓存未初始化）
        Cache cache = cacheManager.getCache(CacheConstant.TENANT_DETAIL);
        if (cache == null) {
            return true;
        }

        //获取所有租户列表，如果列表为空则放行
        List<Map<String,Object>> tenantList = cache.get(CacheConstant.TENANT_DETAIL, List.class);
        if (CollUtil.isEmpty(tenantList)) {
            return true;
        }

//        检查当前租户ID（从TenantContextHolder获取）是否存在于有效租户列表中
        boolean exist = tenantList.stream().anyMatch(tenant -> NumberUtil.equals((Long) tenant.get("tenant_id"), TenantContextHolder.getTenantId()));
        if (exist) {
            return true;
        }

        response.setStatus(HttpStatus.UPGRADE_REQUIRED.value());
        return false;
    }

}
