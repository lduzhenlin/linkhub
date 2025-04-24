package com.qishanor.admin.Service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qishanor.admin.Service.SysUserService;
import com.qishanor.admin.entity.SysUser;
import com.qishanor.admin.mapper.SysUserMapper;
import com.qishanor.common.core.constant.CommonConstant;
import com.qishanor.common.core.util.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserMapper sysUserMapper;


    @Override
    public R saveSysUser(SysUser sysUser) {
        if(StrUtil.isBlank(sysUser.getTel())||StrUtil.isBlank(sysUser.getPassword())){
            return R.failed("手机号或密码不能为空");
        }
        List<SysUser> dbUsers=sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getTel,sysUser.getTel()));
        if(CollUtil.size(dbUsers)>0){
            return R.failed("手机号已经注册,请登录");
        }
        //密码加密存储
        sysUser.setUsername(sysUser.getTel());
        //连续两次md5加密
        sysUser.setPassword(SaSecureUtil.md5(SaSecureUtil.md5(sysUser.getPassword())));

        sysUserMapper.insert(sysUser);


        return R.ok();
    }
}
