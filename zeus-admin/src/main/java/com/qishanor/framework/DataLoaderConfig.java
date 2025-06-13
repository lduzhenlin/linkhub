package com.qishanor.framework;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.qishanor.Service.SysUserService;
import com.qishanor.common.core.constant.CacheConstant;
import com.qishanor.entity.SysUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.qishanor.common.core.constant.CacheConstant.TENANT_DETAIL;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoaderConfig {

    private final SysUserService userService;

    private final CacheManager cacheManager;

    @EventListener(ApplicationReadyEvent.class)
    public void loadDataAfterStartup() {
        // 从数据库加载数据
        List<Map<String,Object>> dataList = userService.listMaps(Wrappers.<SysUser>lambdaQuery().select(SysUser::getTel,SysUser::getTenantId));

        // 从缓存获取
        Cache cache = cacheManager.getCache(CacheConstant.TENANT_DETAIL);
        if (cache != null) {
            cache.put(CacheConstant.TENANT_DETAIL, dataList);
        }
        System.out.println("已加载 " + dataList.size() + " 条数据到缓存");

    }
}
