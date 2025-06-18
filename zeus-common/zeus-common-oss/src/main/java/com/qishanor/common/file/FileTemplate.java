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
	 * 返回文件名
	 */
	String uploadFile(MultipartFile file) throws IOException;

	String uploadFile(String dir, MultipartFile file) throws IOException;

	String uploadFile(MultipartFile file, String objectName) throws IOException;

	String uploadFile(String dir, String objectName, MultipartFile file) throws IOException;



	/**
	 * 获取文件
	 * @param objectName 文件名称
	 * @return 二进制流
	 */
	S3Object getFile(String objectName);
	/**
	 * 获取文件
	 * @param dir 文件夹名称
	 * @param objectName 文件名称
	 * @return 二进制流 s3Object.getObjectContent()文件输入流内容
	 */
	S3Object getFile(String dir, String objectName);



	void removeFile(String objectName) ;

	void removeFile(String dir, String objectName) ;


	List<S3ObjectSummary> getFileByPrefix(String prefix, boolean recursive);

	List<S3ObjectSummary> getFileByPrefix(String dir, String prefix, boolean recursive);





}
