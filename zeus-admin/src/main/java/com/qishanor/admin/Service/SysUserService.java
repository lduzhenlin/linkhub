package com.qishanor.admin.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qishanor.admin.entity.SysUser;
import com.qishanor.common.core.util.R;


public interface SysUserService extends IService<SysUser> {

    R saveSysUser(SysUser sysUser);
}
