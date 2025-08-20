package com.qishanor.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.qishanor.common.data.tenant.TenantTable;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TenantTable
public class Link extends Model<Link> {

    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(type = IdType.ASSIGN_ID)
    private Long linkId;
    private String title;
    private String url;
    private String description;
    private String icon;
    private String categoryId;

    /**
     * 是否共享 1：已共享 0：未共享
     */
    private String isShared;
    
    /**
     * 共享时间
     */
    private LocalDateTime sharedTime;
    
    /**
     * 原始链接ID（用于复制模式）
     */
    private Long originalLinkId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 是否删除 1：已删除 0：正常
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String delFlag;
    private Long tenantId;

    @TableField(exist = false)
    private String iconUrl;

}
