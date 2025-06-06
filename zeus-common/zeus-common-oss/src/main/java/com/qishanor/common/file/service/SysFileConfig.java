package com.qishanor.common.file.service;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.qishanor.common.data.tenant.TenantTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * 文件配置
 *
 * @author Joe
 * @date 2024-07-03 20:50:46
 */
@Data
@TenantTable
@TableName("sys_file_config")
@EqualsAndHashCode(callSuper = true)
public class SysFileConfig extends Model<SysFileConfig> {

	/**
	* 主键ID
	*/
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

	private String storageType;
	/**
	* 是否开启
	*/
    private Boolean enable;

	/**
	 * 默认的存储桶名称
	 */
	private String bucketName;

	/**
	 * 本地存储默认路径
	 */
	private String basePath;

	/**
	 * 对象存储服务的URL
	 */
	private String endpoint;
	/**
	 * 访问key
	 */
	private String accessKey;

	/**
	 * 密钥key
	 */
	private String secretKey;
	/**
	 * 路径风格
	 * true path-style nginx 反向代理和S3默认支持 pathStyle {http://endpoint/bucketname} false
	 supports virtual-hosted-style 阿里云等需要配置为 virtual-hosted-style
	 */
//    @Schema(description="路径风格")
	private Boolean pathStyleAccess;




	/**
	* 自定义域名
	*/
    private String customDomain;

	/**
	* 应用ID
	*/
    private String appId;

	/**
	* 区域
	*/
    private String region;

	/**
	* 最大线程数，默认： 100
	*/
    private Integer maxConnections;

	/**
	* 创建人
	*/
	@TableField(fill = FieldFill.INSERT)
    private String createBy;

	/**
	* 创建时间
	*/
	@TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

	/**
	* 修改人
	*/
	@TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

	/**
	* 更新时间
	*/
	@TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

	/**
	* 删除标记
	*/
    @TableLogic
	@TableField(fill = FieldFill.INSERT)
    private String delFlag;

	/**
	* 租户
	*/
    private Long tenantId;


	public String generateFileName(MultipartFile multipartFile) {
		String name = multipartFile.getOriginalFilename();
		String ext  = Objects.requireNonNull(name).substring(name.lastIndexOf("."));
//		return  UUID.randomUUID() + ext.toLowerCase();
		return IdUtil.getSnowflakeNextIdStr()+ext.toLowerCase();
	}
}