package com.qishanor.common.file;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.qishanor.common.file.condition.LocalFileTemplateCondition;
import com.qishanor.common.file.condition.OssFileTemplateCondition;
import com.qishanor.common.file.config.FileStorageProperties;
import com.qishanor.common.file.local.LocalFileTemplate;
import com.qishanor.common.file.oss.OssFileTemplate;
import com.qishanor.common.file.service.SysFileConfig;
import com.qishanor.common.file.service.SysFileConfigMapper;
import com.qishanor.common.file.service.SysFileConfigService;
import com.qishanor.common.file.service.SysFileConfigServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置类
 *
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class FileAutoConfiguration {

	private final SqlSessionFactory sqlSessionFactory;


	@Bean
	public SysFileConfig getFileConfig(){
		SysFileConfigService fileConfigService=fileConfigService();
		SysFileConfig fileConfig=fileConfigService.getOne(Wrappers.<SysFileConfig>lambdaQuery().eq(SysFileConfig::getEnable,1));

		return fileConfig;
	}

	@Bean
	public MapperFactoryBean<SysFileConfigMapper> sysFileConfigMapper() {
		MapperFactoryBean<SysFileConfigMapper> factory = new MapperFactoryBean<>(SysFileConfigMapper.class);
		factory.setSqlSessionFactory(sqlSessionFactory);
		return factory;
	}
	@Bean
	public SysFileConfigService fileConfigService(){
		return new SysFileConfigServiceImpl();
	}

	@Bean
	@Conditional(LocalFileTemplateCondition.class)
	public FileTemplate localFileTemplate() {
		return new LocalFileTemplate(getFileConfig());
	}

	@Bean
	@Conditional(OssFileTemplateCondition.class)
	public FileTemplate ossFileTemplate() {
		return new OssFileTemplate(getFileConfig());
	}


//	@Bean
//	@ConditionalOnProperty(name = "file.storage.type", havingValue = "local")
//	public FileTemplate localFileTemplate() {
//		return new LocalFileTemplate(getFileConfig());
//	}


}
