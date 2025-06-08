package com.qishanor.common.security;

import cn.dev33.satoken.stp.StpInterface;
import java.util.List;

/**
 * @description: Sa-Token自定义权限验证接口扩展

 **/

//@Component
public class StpInterfaceImpl implements StpInterface {


//    @Autowired
//    private SysUserRoleMapper sysUserRoleMapper;
//
//    @Autowired
//    private SysRoleMapper sysRoleMapper;
//
//    @Autowired
//    private SysMenuMapper sysMenuMapper;
//
//    @Autowired
//    private SysRoleMenuMapper sysRoleMenuMapper;


    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
//        //通过用户ID 获取到角色ID
//        List<Integer> roleIds= sysUserRoleMapper.selectObjs(Wrappers.lambdaQuery(SysUserRole.class)
//                        .select(SysUserRole::getRoleId).eq(SysUserRole::getUserId,loginId))
//                .stream().map(u->(Integer)u).collect(Collectors.toList());
//
//        //通过角色ID 获取到菜单ID
//        List<Integer> menuIds=sysRoleMenuMapper.selectObjs(Wrappers.lambdaQuery(SysRoleMenu.class)
//                        .select(SysRoleMenu::getMenuId)
//                        .in(SysRoleMenu::getRoleId,roleIds))
//                .stream().map(u->(Integer)u).collect(Collectors.toList());
//
//        //通过菜单ID 查询所有权限
//        List<String> list=sysMenuMapper.selectBatchIds(menuIds)
//                .stream().map(u-> u.getPermission()).filter(u->StrUtil.isNotBlank(u)).collect(Collectors.toList());
//
//        return list;
        return null;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {

        //通过用户ID 查询角色ID
//        List<Integer> roleIds= sysUserRoleMapper.selectObjs(Wrappers.lambdaQuery(SysUserRole.class)
//                        .select(SysUserRole::getRoleId).eq(SysUserRole::getUserId,loginId))
//                .stream().map(u->(Integer)u).collect(Collectors.toList());
//
//        //通过角色ID 查询角色名称
//        List<String> list=sysRoleMapper.selectBatchIds(roleIds).stream().map(u->u.getCode()).collect(Collectors.toList());
//
//        StpUtil.getSession().set(CacheConstant.ROLE_DETAIL,roleIds);
//        return list;
        return null;
    }
}