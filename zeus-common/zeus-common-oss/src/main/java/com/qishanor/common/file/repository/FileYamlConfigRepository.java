package com.qishanor.common.file.repository;

import cn.hutool.core.convert.Convert;
import org.springframework.core.env.Environment;

public class FileYamlConfigRepository {

    public static FileConfig loadConfigFromYaml(Environment env) {
        FileConfig fileConfig=new FileConfig();
        if (env.getProperty("file.storage.enable") != null) {
            fileConfig.setEnable(Convert.toBool(env.getProperty("file.storage.enable")));
        }
        if(!fileConfig.getEnable())return fileConfig;

        if (env.getProperty("file.storage.type") != null) {
            fileConfig.setStorageType(env.getProperty("file.storage.type"));
        }
        if (env.getProperty("file.storage.bucketName") != null) {
            fileConfig.setBucketName(env.getProperty("file.storage.bucketName"));
        }
        if (env.getProperty("file.storage.basePath") != null) {
            fileConfig.setBasePath(env.getProperty("file.storage.basePath"));
        }
        if (env.getProperty("file.storage.endpoint") != null) {
            fileConfig.setEndpoint(env.getProperty("file.storage.endpoint"));
        }
        if (env.getProperty("file.storage.accessKey") != null) {
            fileConfig.setAccessKey(env.getProperty("file.storage.accessKey"));
        }
        if (env.getProperty("file.storage.secretKey") != null) {
            fileConfig.setSecretKey(env.getProperty("file.storage.secretKey"));
        }
        if (env.getProperty("file.storage.pathStyleAccess") != null) {
            fileConfig.setPathStyleAccess("0".equals(env.getProperty("file.storage.pathStyleAccess"))?Boolean.FALSE:Boolean.TRUE);
        }
        if (env.getProperty("file.storage.region") != null) {
            fileConfig.setRegion(env.getProperty("file.storage.region"));
        }
        return fileConfig;
    }
}
