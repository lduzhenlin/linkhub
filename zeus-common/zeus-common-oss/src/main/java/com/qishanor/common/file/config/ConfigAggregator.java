package com.qishanor.common.file.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

public class ConfigAggregator {


    private Environment env;



    private final Map<String, Object> aggregatedConfig = new HashMap<>();



    public void loadConfig(Environment env){
        this.env=env;
        loadFromYaml();
        loadFromDatabase(env);
        convertBooleanValues();
    }

    private void loadFromYaml() {
        if (env.getProperty("storageType") != null) {
            aggregatedConfig.put("storageType", env.getProperty("storageType"));
        }
        if (env.getProperty("enable") != null) {
            aggregatedConfig.put("enable", env.getProperty("enable"));
        }
        if (env.getProperty("bucketName") != null) {
            aggregatedConfig.put("bucketName", env.getProperty("bucketName"));
        }
        if (env.getProperty("basePath") != null) {
            aggregatedConfig.put("basePath", env.getProperty("basePath"));
        }
        if (env.getProperty("endpoint") != null) {
            aggregatedConfig.put("endpoint", env.getProperty("endpoint"));
        }
        if (env.getProperty("accessKey") != null) {
            aggregatedConfig.put("accessKey", env.getProperty("accessKey"));
        }
        if (env.getProperty("secretKey") != null) {
            aggregatedConfig.put("secretKey", env.getProperty("secretKey"));
        }
        if (env.getProperty("pathStyleAccess") != null) {
            aggregatedConfig.put("pathStyleAccess", env.getProperty("pathStyleAccess"));
        }
        if (env.getProperty("region") != null) {
            aggregatedConfig.put("region", env.getProperty("region"));
        }
    }

    private void loadFromDatabase(Environment env) {
        DbConfigReader dbConfigReader=new DbConfigReader();
        Map<String, String> dbConfig = dbConfigReader.loadConfigFromDb(env);
        dbConfig.forEach((key, value) -> {
            if (value != null && !aggregatedConfig.containsKey(key)) {
                aggregatedConfig.put(key, value);
            }
        });
    }

    private void convertBooleanValues() {
        // 转换布尔值（从 "1/0" 或 "true/false" 字符串）
        convertToBoolean("enable");
        convertToBoolean("pathStyleAccess");
    }

    private void convertToBoolean(String key) {
        if (aggregatedConfig.containsKey(key)) {
            Object value = aggregatedConfig.get(key);
            if (value instanceof String) {
                aggregatedConfig.put(key, Boolean.parseBoolean((String) value));
            }
        }
    }

    public <T> T getConfig(String key) {
        @SuppressWarnings("unchecked")
        T value = (T) aggregatedConfig.get(key);
        return value;
    }
}