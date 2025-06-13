package com.qishanor.common.file.repository;

import cn.hutool.db.Db;
import cn.hutool.db.DbUtil;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.core.env.Environment;

import java.sql.SQLException;
import java.util.List;

public class FileDbConfigRepository {


    public static FileConfig loadConfigFromDb(Environment env) {
        try {
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl(env.getProperty("spring.datasource.url"));
            dataSource.setUsername(env.getProperty("spring.datasource.username"));
            dataSource.setPassword(env.getProperty("spring.datasource.password"));
            dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
            Db db = DbUtil.use(dataSource);


            // 查询启用的配置
            List<FileConfig> entities = db.query("SELECT * FROM sys_file_config WHERE enable = 1 LIMIT 1",FileConfig.class);

            if (entities.isEmpty()) {
                return null;
            }

            return entities.get(0);
        } catch (SQLException e) {
            throw new RuntimeException("读取数据库配置失败", e);
        }
    }
}