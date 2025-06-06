package com.qishanor.common.file;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.qishanor.common.file.repository.FileConfig;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 文件操作接口
 *
 */
public interface FileTemplate  {



	FileConfig getFileConfig();

	/**
	 * 存储桶bucket 操作
	 */
	List<Bucket> getAllBuckets();

	void createBucket(String bucketName);

	void removeBucket(String bucketName);


	/**
	 * 上传文件
	 */
	String putObject(MultipartFile file) throws IOException;

	String putObject(String dir, MultipartFile file) throws IOException;

	String putObject(MultipartFile file,String objectName) throws IOException;

	String putObject(String dir, String objectName, MultipartFile file) throws IOException;




	S3Object getObject( String objectName);
	/**
	 * 获取文件
	 * @param dir 文件夹名称
	 * @param objectName 文件名称
	 * @return 二进制流 s3Object.getObjectContent()文件输入流内容
	 */
	S3Object getObject(String dir, String objectName);




	void removeObject(String objectName) ;

	void removeObject(String dir, String objectName) ;




	List<S3ObjectSummary> getObjectsByPrefix(String prefix, boolean recursive);

	List<S3ObjectSummary> getObjectsByPrefix(String dir, String prefix, boolean recursive);





}
