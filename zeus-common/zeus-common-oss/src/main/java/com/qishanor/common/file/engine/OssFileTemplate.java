
package com.qishanor.common.file.engine;

import cn.hutool.core.text.CharPool;
import cn.hutool.core.util.StrUtil;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.qishanor.common.file.FileTemplate;
import com.qishanor.common.file.repository.FileConfig;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

/**
 * aws-s3 通用存储操作
 * 支持所有兼容s3协议的云存储: {阿里云OSS，腾讯云COS，七牛云，京东云，minio 等}
 *
 */
@Data
@RequiredArgsConstructor
public class OssFileTemplate implements InitializingBean, FileTemplate {

	private AmazonS3 amazonS3;

	private final FileConfig fileConfig;

	public FileConfig getFileConfig(){return fileConfig;}

	/**
	 * 查询全部bucket
	 */
	@SneakyThrows
	public List<Bucket> getAllBuckets() {
		return amazonS3.listBuckets();
	}

	/**
	 * 根据名称查询bucket
	 */
	@SneakyThrows
	public Optional<Bucket> getBucket(String bucketName) {
		return amazonS3.listBuckets().stream().filter(b -> b.getName().equals(bucketName)).findFirst();
	}

	@SneakyThrows
	public void createBucket(String bucketName) {
		if (!amazonS3.doesBucketExistV2(bucketName)) {
			amazonS3.createBucket((bucketName));
		}
	}

	@SneakyThrows
	public void removeBucket(String bucketName) {
		amazonS3.deleteBucket(bucketName);
	}


	@Override
	public String uploadFile(MultipartFile file) throws IOException{
		return uploadFile(null,null,file);
	}
	@Override
	public String uploadFile(String dir, MultipartFile file) throws IOException{
		return uploadFile(dir,null,file);
	}
	@Override
	public String uploadFile(MultipartFile file, String objectName) throws IOException {
		return uploadFile(null,objectName,file);
	}
	@Override
	public String uploadFile(String dir, String objectName, MultipartFile file) throws IOException {

		String resultFileName="";
		if(StrUtil.isBlank(objectName)){
			resultFileName=fileConfig.generateFileName(file);
			objectName= resultFileName;
		}

		if (StrUtil.isNotBlank(dir)) {
			objectName = dir + CharPool.SLASH + objectName;
		}

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(file.getSize());
		objectMetadata.setContentType(file.getContentType());
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(file.getBytes());
		// 上传
		 amazonS3.putObject(fileConfig.getBucketName(), objectName, byteArrayInputStream, objectMetadata);
		return resultFileName;
	}



	@Override
	public S3Object getFile(String objectName){
		return getFile(null,objectName);
	}
	/**
	 * 获取文件
	 * @param dir 目录
	 * @param objectName 文件名称
	 * @return 二进制流
	 */
	@Override
	public S3Object getFile(String dir, String objectName){
		if (StrUtil.isNotBlank(dir)) {
			objectName = dir + CharPool.SLASH + objectName;
		}
		return amazonS3.getObject(fileConfig.getBucketName(), objectName);
	}


	@Override
	public void removeFile(String objectName) {
		removeFile(null,objectName);
	}
	/**
	 * 删除文件
	 * @param dir 目录
	 * @param objectName 文件名称
	 */
	@Override
	public void removeFile(String dir, String objectName){
		if (StrUtil.isNotBlank(dir)) {
			// dir 路径为 a/b
			objectName = dir + CharPool.SLASH  + objectName;
		}
		amazonS3.deleteObject(fileConfig.getBucketName(), objectName);
	}







	@SneakyThrows
	public List<S3ObjectSummary> getFileByPrefix(String prefix, boolean recursive) {

		return getFileByPrefix(null,prefix,recursive);
	}

	/**
	 * 根据文件前置查询文件
	 * @param dir 目录
	 * @param prefix 前缀
	 * @param recursive 是否递归查询
	 * @return S3ObjectSummary 列表
	 */
	@SneakyThrows
	public List<S3ObjectSummary> getFileByPrefix(String dir, String prefix, boolean recursive) {
		if (StrUtil.isNotBlank(dir)) {
			// dir 路径为 a/b
			prefix = dir + CharPool.SLASH  + prefix;
		}

		ListObjectsV2Result objectListing = amazonS3.listObjectsV2(fileConfig.getBucketName(), prefix);
		return new ArrayList<>(objectListing.getObjectSummaries());
	}


	@Override
	public void afterPropertiesSet() {
		Integer maxConnections=fileConfig.getMaxConnections();
		String region= fileConfig.getRegion();
		String endpoint= fileConfig.getEndpoint();
		String accessKey= fileConfig.getAccessKey();
		String secretKey= fileConfig.getSecretKey();
		Boolean pathStyleAccess=fileConfig.getPathStyleAccess();


		ClientConfiguration clientConfiguration = new ClientConfiguration();
		clientConfiguration.setMaxConnections(maxConnections);

		AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(endpoint, region);
		AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
		AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
		this.amazonS3 = AmazonS3Client.builder()
			.withEndpointConfiguration(endpointConfiguration)
			.withClientConfiguration(clientConfiguration)
			.withCredentials(awsCredentialsProvider)
			.disableChunkedEncoding()
			.withPathStyleAccessEnabled(pathStyleAccess)
			.build();
	}

//	public S3Object getObjectInfo(String dir, String objectName) throws Exception {
//		if (StrUtil.isNotBlank(dir)) {
//			// dir 路径为 a/b
//			objectName = dir + CharPool.SLASH  + objectName;
//		}
//		@Cleanup
//		S3Object object = amazonS3.getObject(fileConfig.getBucketName(), objectName);
//		return object;
//	}


}
