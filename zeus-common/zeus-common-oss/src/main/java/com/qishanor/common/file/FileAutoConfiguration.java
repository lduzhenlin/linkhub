package com.qishanor.common.file;

import com.qishanor.common.file.repository.FileConfig;
import com.qishanor.common.file.repository.FileConfigManager;
import com.qishanor.common.file.engine.LocalFileTemplate;
import com.qishanor.common.file.engine.OssFileTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 自动配置类
 *
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class FileAutoConfiguration {


	@Bean
	public FileTemplate localFileTemplate(Environment env) {
		FileConfig fileConfig=FileConfigManager.loadConfig(env);

		if("oss".equals(fileConfig.getStorageType())){
			return new OssFileTemplate(fileConfig);
		}
		/*
		  默认是local  存储路径为/upload
		 */
		return new LocalFileTemplate(fileConfig);
	}




}
