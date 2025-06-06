package com.qishanor.common.file.repository;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.core.env.Environment;


public class FileConfigManager {


    private static FileConfig fileConfig=new FileConfig();

    public static FileConfig loadConfig(Environment env){
        YamlConfigRepository.loadConfigFromYaml(env);
        loadFromDatabase(env);
        return fileConfig;
    }

    private static void loadFromDatabase(Environment env) {
        FileConfig dbConfig = DbConfigRepository.loadConfigFromDb(env);
        if(ObjectUtil.isNotEmpty(dbConfig)){
            BeanUtil.copyProperties(dbConfig,fileConfig);
        }
    }


}