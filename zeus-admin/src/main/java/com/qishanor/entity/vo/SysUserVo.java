package com.qishanor.entity.vo;

import lombok.Data;

@Data
public class SysUserVo {

    private Long userId;
    private String username;
    private String tel;
    private String avatar;
    private String email;
    private String token;
    private Long tenantId;
}
