package com.qishanor.Service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.qishanor.Service.SysUserService;
import com.qishanor.entity.SysUser;
import com.qishanor.mapper.SysUserMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qishanor.common.core.util.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {


    @Override
    public R saveSysUser(SysUser sysUser) {
        if(StrUtil.isBlank(sysUser.getTel())||StrUtil.isBlank(sysUser.getPassword())){
            return R.failed("手机号或密码不能为空");
        }
        List<SysUser> dbUsers=baseMapper.selectList(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getTel,sysUser.getTel()));
        if(CollUtil.size(dbUsers)>0){
            return R.failed("手机号已经注册,请登录");
        }
        //密码加密存储
        sysUser.setUsername(sysUser.getTel());
        //使用 Spring Security 的 BCryptPasswordEncoder
        sysUser.setPassword(BCrypt.hashpw(sysUser.getPassword()));


        sysUser.setTenantId(getNextTenantId());

        baseMapper.insert(sysUser);


        return R.ok();
    }

    /**
     * 获取下一个租户Id
     */
    public Long getNextTenantId(){
        // Step 1:查询出最大编号
        SysUser sysUser =this.baseMapper.selectOne(Wrappers.<SysUser>lambdaQuery().select(SysUser::getTenantId).orderByDesc(SysUser::getTenantId).last("LIMIT 1"));
        Long nextTenantId=1L;
        if(sysUser !=null){
            // Step 2: 获取最大分类编号，并进行加 1
            nextTenantId = sysUser.getTenantId() + 1;
        }
        return nextTenantId;
    }
}
