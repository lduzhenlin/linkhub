package com.qishanor.common.sms.repository;

import cn.hutool.db.Db;
import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import com.qishanor.common.sms.Constant;
import com.qishanor.common.sms.log.SmsSendLog;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class SmsDbConfigRepository {

    public static SmsConfig loadConfigFromDb(Environment env) {
        try {
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl(env.getProperty("spring.datasource.url"));
            dataSource.setUsername(env.getProperty("spring.datasource.username"));
            dataSource.setPassword(env.getProperty("spring.datasource.password"));
            dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
            Db db = DbUtil.use(dataSource);

            // 查询启用的配置
            List<SmsConfig> entities = db.query("SELECT * FROM sys_sms_config WHERE enable = 1 LIMIT 1",SmsConfig.class);

            if (entities.isEmpty()) {
                return null;
            }

            return entities.get(0);
        } catch (SQLException e) {
            log.error("读取数据库配置失败", e);
            return null;
        }
    }

    public static void saveSmsSendLog(Environment env, SmsSendLog smsSendLog) {
        try {
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl(env.getProperty("spring.datasource.url"));
            dataSource.setUsername(env.getProperty("spring.datasource.username"));
            dataSource.setPassword(env.getProperty("spring.datasource.password"));
            dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
            Db db = DbUtil.use(dataSource);

            // 查询启用的配置
            Entity entity =Entity.create(Constant.SMS_LOG_TABLE_NAME).parseBean(smsSendLog,true,true);;

            db.insert(entity);


        } catch (SQLException e) {
            log.error("读取数据库配置失败", e);
        }
    }
}
