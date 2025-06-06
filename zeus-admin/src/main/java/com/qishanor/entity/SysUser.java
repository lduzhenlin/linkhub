package com.qishanor.entity;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysUser extends Model<SysUser> {

    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(type= IdType.ASSIGN_ID)
    private Long userId;
    private String username;
    private String password;
    private String tel;
    private String avatar;
    private String email;

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



}
