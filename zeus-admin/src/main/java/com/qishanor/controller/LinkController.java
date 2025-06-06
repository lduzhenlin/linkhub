package com.qishanor.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.qishanor.Service.LinkService;
import com.qishanor.entity.Link;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.qishanor.common.core.util.R;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RequiredArgsConstructor
@SaCheckLogin
@RestController
@RequestMapping("/api/link")
public class LinkController {

    private final LinkService linkService;


    @GetMapping("/list")
    public Object list(String categoryId,String linkTitle){
        List<Link> lists =linkService.list(Wrappers.<Link>lambdaQuery().eq(StrUtil.isNotBlank(categoryId),Link::getCategoryId,categoryId).like(StrUtil.isNotBlank(linkTitle), Link::getTitle,linkTitle));

        return R.ok(lists);
    }

    @GetMapping("/getById")
    public Object getById(String linkId){
        Link link =linkService.getById(linkId);

        return R.ok(link);
    }


    @PostMapping
    public Object saveOrUpdate(Link link){
        if(ObjUtil.isEmpty(link) ||StrUtil.isBlank(link.getTitle())||StrUtil.isBlank(link.getUrl())){
            return R.failed("请填写标题和URL");
        }
//        Long tenantId=(Long) StpUtil.getSession().get(CacheConstant.TENANT_ID);
//        if(ObjUtil.isEmpty(link.getTenantId())){
//            link.setTenantId(tenantId);
//        }

        linkService.saveOrUpdate(link);
        return R.ok();
    }
    @PutMapping
    public Object edit(Link link){
        if(ObjUtil.isEmpty(link) ||StrUtil.isBlank(link.getTitle())||StrUtil.isBlank(link.getUrl())){
            return R.failed("请填写标题和URL");
        }

        Link dbLink=linkService.getById(link.getLinkId());
        if(ObjUtil.isEmpty(dbLink)){
            return R.failed("没有查到此数据");
        }

        linkService.updateById(link);
        return R.ok();
    }

    @DeleteMapping
    public Object delete(Long linkId){

        Link link=linkService.getById(linkId);
        if(ObjUtil.isEmpty(link)){
            return R.failed("找不到此数据");
        }

        linkService.removeById(link);

        return R.ok();
    }

}
