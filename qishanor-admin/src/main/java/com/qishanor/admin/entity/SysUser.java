package com.qishanor.admin.entity;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysUser extends Model<SysUser> {

    @TableId(type= IdType.ASSIGN_ID)
    private Long userId;
    private String username;
    private String password;
    private String phone;
    private String avatar;
    private String nickname;
    private String email;

    @TableField(fill = FieldFill.INSERT)
    private String create_time;
    @TableField(fill = FieldFill.INSERT)
    private String create_by;
    @TableField(fill = FieldFill.UPDATE)
    private String update_time;
    @TableField(fill = FieldFill.UPDATE)
    private String update_by;

    /**
     * 是否删除 1：已删除 0：正常
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String del_flag;
    private Long tenant_id;


}
