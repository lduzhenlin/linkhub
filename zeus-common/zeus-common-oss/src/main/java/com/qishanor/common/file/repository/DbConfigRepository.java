package com.qishanor.common.file.repository;

import cn.hutool.db.Db;
import cn.hutool.db.DbUtil;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.core.env.Environment;

import java.sql.SQLException;
import java.util.List;

public class DbConfigRepository {


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

//            Map<String, String> configMap = new HashMap<>();
//
//            // 映射字段
//            configMap.put("storageType", configEntity.getStr("storage_type"));
//            configMap.put("enable", configEntity.getStr("enable"));
//            configMap.put("bucketName", configEntity.getStr("bucket_name"));
//            configMap.put("basePath", configEntity.getStr("base_path"));
//            configMap.put("endpoint", configEntity.getStr("endpoint"));
//            configMap.put("accessKey", configEntity.getStr("access_key"));
//            configMap.put("secretKey", configEntity.getStr("secret_key"));
//            configMap.put("pathStyleAccess", configEntity.getStr("path_style_access"));
//            configMap.put("region", configEntity.getStr("region"));
//            return configMap;
        } catch (SQLException e) {
            throw new RuntimeException("读取数据库配置失败", e);
        }
    }
}