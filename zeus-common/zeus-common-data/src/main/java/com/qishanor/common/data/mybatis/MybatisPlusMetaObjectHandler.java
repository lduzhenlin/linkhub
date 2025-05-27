package com.qishanor.common.data.mybatis;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.qishanor.common.data.constant.CacheConstant;
import com.qishanor.common.data.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

/**
 * @description: MybatisPlus 自动填充配置
 * @author: 周振林
 * @date: 2022-04-16
 * @Copyright: 博客：http://www.zhouzhenlin.com - 沉淀、分享、成长、让自己和他人都有所收获
 **/
@Slf4j
@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("mybatis plus start insert fill ....");
        LocalDateTime now = LocalDateTime.now();
        boolean isLogin= StpUtil.isLogin();
        String username=null;
        String deptId=null;
        if(isLogin){
            username=(String)StpUtil.getSession().get(CacheConstant.USER_NAME);
            deptId=(String)StpUtil.getSession().get(CacheConstant.USER_DEPT);
        }
        // 审计字段自动填充
        fillValIfNullByName("createTime", now, metaObject, false);
        fillValIfNullByName("updateTime", now, metaObject, false);
        if(username!=null){
            fillValIfNullByName("createBy", username, metaObject, false);
            fillValIfNullByName("updateBy", username, metaObject, false);
            Object deptIdDefault = getFieldValByName("deptId", metaObject);
            if(deptIdDefault==null){
                fillValIfNullByName("deptId",deptId,metaObject,false);
            }
        }

        // 删除标记自动填充
        fillValIfNullByName("delFlag", CommonConstant.STATUS_NORMAL, metaObject, false);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("mybatis plus start update fill ....");
        boolean isLogin= StpUtil.isLogin();
        String username=null;
        if(isLogin){
            username=(String)StpUtil.getSession().get(CacheConstant.USER_NAME);
        }
        fillValIfNullByName("updateTime", LocalDateTime.now(), metaObject, true);
        fillValIfNullByName("updateBy", username, metaObject, true);
    }

    /**
     * 填充值，先判断是否有手动设置，优先手动设置的值，例如：job必须手动设置
     * @param fieldName 属性名
     * @param fieldVal 属性值
     * @param metaObject MetaObject
     * @param isCover 是否覆盖原有值,避免更新操作手动入参
     */
    private  void fillValIfNullByName(String fieldName, Object fieldVal, MetaObject metaObject, boolean isCover) {

        // 0. 如果填充值为空
        if (fieldVal == null) {
            return;
        }
        // 1. 没有 get 方法
        if (!metaObject.hasSetter(fieldName)) {
            return;
        }
        // 2. 如果用户有手动设置的值
        Object userSetValue = metaObject.getValue(fieldName);
        String setValueStr = StrUtil.str(userSetValue, Charset.defaultCharset());
        if (StrUtil.isNotBlank(setValueStr) && !isCover) {
            return;
        }
        // 3. field 类型相同时设置
        Class<?> getterType = metaObject.getGetterType(fieldName);
        if (ClassUtils.isAssignableValue(getterType, fieldVal)) {
            metaObject.setValue(fieldName, fieldVal);
        }
    }



}