package com.qishanor.common.data.tenant;

import java.lang.annotation.*;

/**
 * table 实体增加此注解自动声明为租户表
 *
 * 注解方式 配置表需要进行租户过滤
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface TenantTable {
}