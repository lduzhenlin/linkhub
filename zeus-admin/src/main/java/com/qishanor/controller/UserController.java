package com.qishanor.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.qishanor.common.data.tenant.TenantContextHolder;
import com.qishanor.entity.SysUser;
import com.qishanor.entity.vo.SysUserVo;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.qishanor.Service.SysUserService;
import com.qishanor.common.core.constant.CacheConstant;
import com.qishanor.common.core.util.R;
import com.qishanor.framework.util.VerifyCodeCacheUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Data
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final SysUserService sysUserService;

    @SaIgnore
    @PostMapping("/login")
    public Object login(String tel, String password) {
        //设置租户
        SysUser dbUser=sysUserService.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername,tel).or().eq(SysUser::getTel,tel));
        if(ObjUtil.isEmpty(dbUser)||!BCrypt.checkpw(password,dbUser.getPassword())){
            return R.failed("用户名或密码错误");
        }

        StpUtil.login(dbUser.getUserId());
        StpUtil.getSession().set(CacheConstant.USER_ID,dbUser.getUserId());
        StpUtil.getSession().set(CacheConstant.USER_NAME,dbUser.getUsername());
        StpUtil.getSession().set(CacheConstant.TENANT_ID,dbUser.getTenantId());
        StpUtil.getSession().set(CacheConstant.USER_DETAIL,dbUser);

        SysUserVo userVo=new SysUserVo();
        BeanUtil.copyProperties(dbUser,userVo);
        userVo.setToken(StpUtil.getTokenValue());
        return R.ok(userVo);

    }

    @SaIgnore
    @PostMapping("/register")
    public Object register(SysUser user,String code) {
        if(StrUtil.isBlank(user.getTel())||StrUtil.isBlank(code)||StrUtil.isBlank(user.getPassword())){
            return R.failed("所填数据不完整");
        }
        //验证验证码
        if(!VerifyCodeCacheUtil.isValidCode(user.getTel(),code)){
            return R.failed("验证码错误");
        }

        return sysUserService.saveSysUser(user);
    }


    @PostMapping("/logout")
    public Object logout(String userId){
        if(StrUtil.isBlank(userId)){
            return R.failed("参数不合法");
        }
        StpUtil.logout(userId);

        return R.ok();
    }

    @SaIgnore
    @GetMapping("/isLogin")
    public Object isLogin(){
        return R.ok(StpUtil.isLogin());
    }

    /**
     * 找回密码
     */
    @SaIgnore
    @PostMapping("/findPassword")
    public Object   findPassword(String phone,String code,String newPassword ){
        if(StrUtil.isBlank(phone)||StrUtil.isBlank(code)||StrUtil.isBlank(newPassword)){
            return R.failed("输入信息不完整");
        }

        //验证验证码
        if(!VerifyCodeCacheUtil.isValidCode(phone,code)){
            return R.failed("验证码错误");
        }

        //设置新密码
        SysUser dbUser=sysUserService.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getTel,phone));
        dbUser.setPassword(BCrypt.hashpw(newPassword));
        sysUserService.updateById(dbUser);

        return R.ok();
    }
}
