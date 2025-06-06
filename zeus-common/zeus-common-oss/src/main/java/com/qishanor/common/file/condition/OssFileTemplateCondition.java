package com.qishanor.common.file.condition;

import cn.hutool.db.Db;
import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class OssFileTemplateCondition implements Condition {


    @SneakyThrows
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 返回 true 表示条件满足，Bean 会被创建
        // 返回 false 表示条件不满足，Bean 不会被创建

        // 创建 HikariCP 数据源（推荐）
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setJdbcUrl(context.getEnvironment().getProperty("spring.datasource.url"));
//        dataSource.setUsername(context.getEnvironment().getProperty("spring.datasource.username"));
//        dataSource.setPassword(context.getEnvironment().getProperty("spring.datasource.password"));
//        dataSource.setDriverClassName(context.getEnvironment().getProperty("spring.datasource.driver-class-name"));
//        Db db = DbUtil.use(dataSource);
//
//        String storageType="";
//        List<Entity> result = db.query("select storage_type from sys_file_config where enable=1");
//        if(result.size()==0){
//            log.warn("文件存储配置：在数据库表sys_file_config中至少需要一个启用配置enable=1");
//        }else if(result.size()>1){
//            log.warn("文件存储配置：在数据库表sys_file_config中只能一个启用配置enable=1,使用第一个");
//            storageType= (String) result.get(0).get("storage_type");
//        }
//
//
//
//        boolean isLocal = "oss".equalsIgnoreCase(storageType);
//        log.info("文件存储基于数据库配置，是否启用OSS文件存储: {}", isLocal);
//        return isLocal;
        return false;
    }



}
