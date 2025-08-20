package com.qishanor.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.qishanor.Service.LinkService;
import com.qishanor.common.file.FileTemplate;
import com.qishanor.entity.Link;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.qishanor.common.core.util.R;
import com.qishanor.framework.util.DomainUtil;
import com.qishanor.framework.util.ProxyUrlToMultipartFile;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import cn.dev33.satoken.stp.StpUtil;
import com.qishanor.common.core.constant.CacheConstant;

@Data
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/link")
public class LinkController {

    private final FileTemplate fileTemplate;

    private final LinkService linkService;


    @GetMapping("/list")
    public Object list(String categoryId,String linkTitle){

        // 检查用户是否登录
        boolean isLogin = StpUtil.isLogin();



        // 构建查询条件
        var queryWrapper = Wrappers.<Link>lambdaQuery()
                .eq(ObjUtil.isNotEmpty(categoryId),Link::getCategoryId,categoryId)
                .like(StrUtil.isNotEmpty(linkTitle), Link::getTitle, linkTitle);


        // 如果用户未登录，只返回共享链接
        if (!isLogin) {
            queryWrapper.eq(Link::getIsShared, "1");
        }
        //否则 如果用户已登录，返回共享链接 + 个人链接

        List<Link> lists =linkService.list(queryWrapper);
        lists.forEach(link->{
            //如果图标为空，则使用默认图标
            if(StrUtil.isBlank(link.getIcon())){
                //https://other-bucket-1300160460.cos.ap-beijing.myqcloud.com/nav%2Flink-default-icon.svg
                link.setIconUrl("https://other-bucket-1300160460.cos.ap-beijing.myqcloud.com/nav%2Flink-default-icon2.svg");
            }else{
                link.setIconUrl("/api/file/"+link.getIcon());
            }
        });

        return R.ok(lists);
    }

    @GetMapping("/getById")
    public Object getById(String linkId){
        Link link =linkService.getById(linkId);
        if(StrUtil.isNotBlank(link.getIcon())){
            link.setIconUrl("/api/file/"+link.getIcon());
        }

        return R.ok(link);
    }


    @SaCheckLogin
    @PostMapping
    public Object save(Link link) throws IOException {
        if(ObjUtil.isEmpty(link) ||StrUtil.isBlank(link.getTitle())||StrUtil.isBlank(link.getUrl())){
            return R.failed("请填写标题和URL");
        }

        if(StrUtil.startWithAny(link.getIconUrl(), "http", "https")){
            MultipartFile file=ProxyUrlToMultipartFile.convert(link.getIconUrl());
            String filename=fileTemplate.uploadFile(file);
            link.setIcon(filename);
        }

        //确保协议包含http或https
        link.setUrl(DomainUtil.ensureProtocol(link.getUrl()));

        linkService.save(link);
        return R.ok();
    }

    @SaCheckLogin
    @PutMapping
    public Object edit(Link link) throws IOException {
        if(ObjUtil.isEmpty(link) ||StrUtil.isBlank(link.getTitle())||StrUtil.isBlank(link.getUrl())){
            return R.failed("请填写标题和URL");
        }

        Link dbLink=linkService.getById(link.getLinkId());
        if(ObjUtil.isEmpty(dbLink)){
            return R.failed("没有查到此数据");
        }

//        Console.log(DomainUtil.isSameDomain(link.getUrl(),dbLink.getUrl()));
//        Console.log(StrUtil.startWithAny(link.getIconUrl(), "http", "https"));

        /**
         * 相同域名下：
         * 1.前端以前没有获取图标，则可以重新上传
         * 2.前端以前获取过图标，则不可以修改
         *
         * 不同域名下：
         * 1.如果前端获取图标，则保存图标
         * 2.如果前端没有获取图标，则不保存图标
         */

        if(DomainUtil.isSameDomain(link.getUrl(),dbLink.getUrl())){
            //相同域名下, 前端以前没有获取图标，则可以重新上传
            if(StrUtil.isBlank(dbLink.getIcon())&&StrUtil.startWithAny(link.getIconUrl(), "http", "https")) {
                MultipartFile file=ProxyUrlToMultipartFile.convert(link.getIconUrl());
                String filename=fileTemplate.uploadFile(file);
                link.setIcon(filename);
            }
        }else{
            //不同域名下,如果前端获取图标，则保存图标
            if(StrUtil.startWithAny(link.getIconUrl(), "http", "https")){
                MultipartFile file=ProxyUrlToMultipartFile.convert(link.getIconUrl());
                String filename=fileTemplate.uploadFile(file);
                link.setIcon(filename);
            }
        }


        //确保协议包含http或https
        link.setUrl(DomainUtil.ensureProtocol(link.getUrl()));

        linkService.updateById(link);
        return R.ok();
    }

    @SaCheckLogin
    @DeleteMapping
    public Object delete(Long linkId){

        Link link=linkService.getById(linkId);
        if(ObjUtil.isEmpty(link)){
            return R.failed("找不到此数据");
        }

        linkService.removeById(link);
        return R.ok();
    }

    /**
     * 共享链接
     */
    @SaCheckLogin
    @PostMapping("/share")
    public Object shareLink(Long linkId) {
        if (ObjUtil.isEmpty(linkId)) {
            return R.failed("链接ID不能为空");
        }
        
        boolean success = linkService.shareLink(linkId);
        if (success) {
            return R.ok("共享成功");
        } else {
            return R.failed("共享失败");
        }
    }

    /**
     * 取消共享链接
     */
    @SaCheckLogin
    @PostMapping("/unshare")
    public Object unshareLink(Long linkId) {
        if (ObjUtil.isEmpty(linkId)) {
            return R.failed("链接ID不能为空");
        }
        
        boolean success = linkService.unshareLink(linkId);
        if (success) {
            return R.ok("取消共享成功");
        } else {
            return R.failed("取消共享失败");
        }
    }

    /**
     * 获取所有共享的链接
     */
    @GetMapping("/shared")
    public Object getSharedLinks() {
        List<Link> sharedLinks = linkService.getSharedLinks();
        sharedLinks.forEach(link -> {
            //如果图标为空，则使用默认图标
            if(StrUtil.isBlank(link.getIcon())){
                link.setIconUrl("https://other-bucket-1300160460.cos.ap-beijing.myqcloud.com/nav%2Flink-default-icon2.svg");
            }else{
                link.setIconUrl("/api/file/"+link.getIcon());
            }
        });
        
        return R.ok(sharedLinks);
    }

    /**
     * 复制共享链接到个人分类
     */
    @SaCheckLogin
    @PostMapping("/copy")
    public Object copySharedLink(Long sharedLinkId, String targetCategoryId) {
        if (ObjUtil.isEmpty(sharedLinkId) || StrUtil.isBlank(targetCategoryId)) {
            return R.failed("参数不能为空");
        }
        
        // 获取当前登录用户信息
        String currentUsername = (String) StpUtil.getSession().get(CacheConstant.USER_NAME);
        if (StrUtil.isBlank(currentUsername)) {
            return R.failed("用户未登录");
        }
        
        boolean success = linkService.copySharedLink(sharedLinkId, targetCategoryId, currentUsername);
        if (success) {
            return R.ok("复制成功");
        } else {
            return R.failed("复制失败：该链接已经属于您，无需重复复制");
        }
    }
}
