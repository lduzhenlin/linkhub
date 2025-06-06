package com.qishanor.Service;

import com.qishanor.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qishanor.common.core.util.R;


public interface SysUserService extends IService<SysUser> {

    R saveSysUser(SysUser sysUser);
}
