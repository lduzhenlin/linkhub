package com.qishanor.common.file.engine;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.qishanor.common.file.FileTemplate;
import com.qishanor.common.file.repository.FileConfig;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 本地文件读取模式
 *
 * @author zeus
 * @date 2022/4/19
 */
@Data
@RequiredArgsConstructor
public class LocalFileTemplate implements FileTemplate {

	private final FileConfig fileConfig;


	public FileConfig getFileConfig(){return fileConfig;}

	/**
	 * 创建bucket
	 * @param bucketName bucket名称
	 */
	@Override
	public void createBucket(String bucketName) {
		FileUtil.mkdir(fileConfig.getBasePath() + FileUtil.FILE_SEPARATOR + bucketName);
	}

	/**
	 * 获取全部bucket
	 */
	@Override
	public List<Bucket> getAllBuckets() {
		return Arrays.stream(FileUtil.ls(fileConfig.getBasePath()))
			.filter(FileUtil::isDirectory)
			.map(dir -> new Bucket(dir.getName()))
			.collect(Collectors.toList());
	}

	/**
	 * 删除bucket
	 */
	@Override
	public void removeBucket(String bucketName) {
		FileUtil.del(fileConfig.getBasePath() + FileUtil.FILE_SEPARATOR + bucketName);
	}


	/**
	 * 上传文件 自动生成文件名称
	 * @param file
	 */
	@Override
	public String putObject(MultipartFile file) throws IOException {
		return putObject(null,null,file);
	}
	@Override
	public String putObject(String dir, MultipartFile file) throws IOException {
		return putObject(dir,null,file);
	}

	@Override
	public String putObject(MultipartFile file, String objectName) throws IOException {
		return putObject(null,objectName,file);
	}

	/**
	 * 上传文件
	 * @param dir 目录名
	 * @param objectName 文件名称
	 * @param file
	 */
	@Override
	public String putObject(String dir, String objectName, MultipartFile file) throws IOException {

		String bucketName=fileConfig.getBucketName();
		if (StrUtil.isNotBlank(dir)) {
			bucketName = bucketName + FileUtil.FILE_SEPARATOR + dir;
		}
		String path = fileConfig.getBasePath() + FileUtil.FILE_SEPARATOR + bucketName;

		// 当 Bucket 不存在时创建
		if (!FileUtil.isDirectory(path)){
			createBucket(bucketName);
		}

		if(StrUtil.isBlank(objectName)){
			objectName= fileConfig.generateFileName(file);
		}

		//写入文件
		File destFile = FileUtil.file(path + FileUtil.FILE_SEPARATOR + objectName);
		FileUtil.writeFromStream(file.getInputStream(), destFile);
		return objectName;
	}


	@Override
	@SneakyThrows
	public S3Object getObject(String objectName) {
		return getObject(null,objectName);
	}

	/**
	 * 获取文件
	 * @param dir 文件夹名称
	 * @param objectName 文件名称
	 * @return 二进制流
	 */
	@Override
	public S3Object getObject(String dir, String objectName) {
		String bucketName= fileConfig.getBucketName();
		if (StrUtil.isNotBlank(dir)) {
			bucketName = bucketName + FileUtil.FILE_SEPARATOR + dir;
		}

		String path = fileConfig.getBasePath() + FileUtil.FILE_SEPARATOR + bucketName;
		S3Object s3Object = new S3Object();
		s3Object.setObjectContent(FileUtil.getInputStream(path + FileUtil.FILE_SEPARATOR + objectName));
		return s3Object;
	}



	@Override
	public void removeObject(String objectName){
		removeObject(null,objectName);
	}

	/**
	 * 删除文件
	 * @param dir 目录名
	 * @param objectName 文件名
	 */
	@Override
	public void removeObject(String dir, String objectName){
		String bucketName= fileConfig.getBucketName();
		if (StrUtil.isNotBlank(dir)) {
			bucketName = bucketName + FileUtil.FILE_SEPARATOR + dir;
		}

		String path = fileConfig.getBasePath() + FileUtil.FILE_SEPARATOR + bucketName;
		FileUtil.del(path + FileUtil.FILE_SEPARATOR + objectName);
	}


	@Override
	public List<S3ObjectSummary> getObjectsByPrefix(String prefix, boolean recursive) {
		return getObjectsByPrefix(null,prefix,recursive);
	}
	/**
	 * 根据文件前置查询文件
	 * @param dir 目录
	 * @param prefix 前缀
	 * @param recursive 是否递归查询
	 * @return S3ObjectSummary 列表 只包含文件名称
	 *
	 * S3ObjectSummary包含元数据摘要信息，包含对象的基本信息（如键、大小、修改时间等），但不包含对象的实际内容。
	 */
	@Override
	public List<S3ObjectSummary> getObjectsByPrefix(String dir, String prefix, boolean recursive) {
		String path = fileConfig.getBasePath() + FileUtil.FILE_SEPARATOR + fileConfig.getBucketName();
		if (StrUtil.isNotBlank(dir)) {
			path = path + FileUtil.FILE_SEPARATOR + dir;
		}

		return Arrays.stream(FileUtil.ls(path)).filter(file -> file.getName().startsWith(prefix)).map(file -> {
			S3ObjectSummary summary = new S3ObjectSummary();
			summary.setKey(file.getName());
			return summary;
		}).collect(Collectors.toList());
	}





}
