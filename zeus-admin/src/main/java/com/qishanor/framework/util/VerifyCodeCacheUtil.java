package com.qishanor.framework.util;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.util.StrUtil;
import com.qishanor.common.core.util.R;

public class VerifyCodeCacheUtil {

    // 创建定时缓存（5分钟过期）
    private static final Cache<String, String> VERIFY_CODE_CACHE =
            CacheUtil.newTimedCache(5 * 60 * 1000);

    // 获取缓存实例
    public static Cache<String, String> getCache() {
        return VERIFY_CODE_CACHE;
    }

    // 简化常用操作
    public static void put(String key, String code) {
        VERIFY_CODE_CACHE.put(key, code);
    }

    public static String get(String key) {
        return VERIFY_CODE_CACHE.get(key);
    }

    public static void remove(String key) {
        VERIFY_CODE_CACHE.remove(key);
    }

    /**
     * 检查验证码是否有效
     * @param phone
     * @param inputCode
     */
    public static Boolean isValidCode(String phone, String inputCode){
        if(StrUtil.isBlank(phone)||StrUtil.isBlank(inputCode)){
            return Boolean.FALSE;
        }

        // 从缓存中获取验证码
        String savedCode = VerifyCodeCacheUtil.get(phone);
        // 验证码是否相等
        if (!inputCode.equals(savedCode)) {
            return Boolean.FALSE;
        }

        // 如果验证成功，移除验证码（防止重复使用）
        VerifyCodeCacheUtil.remove(phone);
        return Boolean.TRUE;


    }
}