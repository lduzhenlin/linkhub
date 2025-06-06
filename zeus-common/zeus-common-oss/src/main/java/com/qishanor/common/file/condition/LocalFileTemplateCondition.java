package com.qishanor.common.file.condition;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.SqlConnRunner;
import cn.hutool.db.ds.DSFactory;
import cn.hutool.setting.Setting;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.qishanor.common.file.config.ConfigAggregator;
import com.qishanor.common.file.service.SysFileConfig;
import com.qishanor.common.file.service.SysFileConfigMapper;
import com.qishanor.common.file.service.SysFileConfigService;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.core.Ordered;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Slf4j
public class LocalFileTemplateCondition implements Condition {


    @SneakyThrows
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        /**
         * 返回 true,Bean会被创建;返回 false，Bean不会被创建
         *
         * 会优先查询yml配置文件中的配置信息，yml中没有配置在查询数据库中配置，数据库中也没有配置则返回false
         */

        ConfigAggregator configAggregator=new ConfigAggregator();
        configAggregator.loadConfig(context.getEnvironment());

        Boolean enable = configAggregator.getConfig("enable");
        String storageType = configAggregator.getConfig("storageType");

        return enable != null && enable &&
               storageType != null&&"local".equals(storageType);
    }



//        String ymlStorageEnableConfig=context.getEnvironment().getProperty("file.storage.type");
//
//        // 创建 HikariCP 数据源（推荐）
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setJdbcUrl(context.getEnvironment().getProperty("spring.datasource.url"));
//        dataSource.setUsername(context.getEnvironment().getProperty("spring.datasource.username"));
//        dataSource.setPassword(context.getEnvironment().getProperty("spring.datasource.password"));
//        dataSource.setDriverClassName(context.getEnvironment().getProperty("spring.datasource.driver-class-name"));
//        Db db = DbUtil.use(dataSource);
//
//        String storageType="";
//        List<Entity> result = db.query("select storage_type from sys_file_config where enable=1");
//        if(StrUtil.isBlank(ymlStorageEnableConfig) ||result.size()==0){
//            log.warn("文件存储配置：在数据库表sys_file_config中至少需要一个启用配置enable=1");
//            return false;
//        }
//        if(StrUtil.isBlank(ymlStorageEnableConfig) ||result.size()>1){
//            log.warn("文件存储配置：在数据库表sys_file_config中只能一个启用配置enable=1");
//        }
//        if(result.size()==1){
//            storageType= (String) result.get(0).get("storage_type");
//        }
//
//
//        boolean isLocal = "local".equals(ymlStorageEnableConfig)||"local".equalsIgnoreCase(storageType);
//        log.info("文件存储配置：是否启用本地文件存储: {}", isLocal);
//        return isLocal;
//    }



}
