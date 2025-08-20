package com.qishanor.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qishanor.entity.Link;
import com.qishanor.Service.LinkService;
import com.qishanor.mapper.LinkMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    @Transactional
    public boolean shareLink(Long linkId) {
        Link link = this.getById(linkId);
        if (link != null) {
            link.setIsShared("1");
            link.setSharedTime(LocalDateTime.now());
            link.setOriginalLinkId(link.getLinkId());
            return this.updateById(link);
        }
        return false;
    }

    @Override
    @Transactional
    public boolean unshareLink(Long linkId) {
        Link link = this.getById(linkId);
        if (link != null) {
            link.setIsShared("0");
            link.setSharedTime(null);
            return this.updateById(link);
        }
        return false;
    }

    @Override
    public List<Link> getSharedLinks() {
        return this.list(Wrappers.<Link>lambdaQuery()
                .eq(Link::getIsShared, "1")
                .orderByDesc(Link::getSharedTime));
    }

    @Override
    @Transactional
    public boolean copySharedLink(Long sharedLinkId, String targetCategoryId, String currentUsername) {
        Link sharedLink = this.getById(sharedLinkId);
        if (sharedLink != null) {
            // 检查链接是否已经属于当前用户
            if (sharedLink.getCreateBy().equals(currentUsername)) {
                return false; // 链接已经属于当前用户，不能复制
            }
            
            // 创建新的链接副本
            Link newLink = new Link();
            newLink.setTitle(sharedLink.getTitle());
            newLink.setUrl(sharedLink.getUrl());
            newLink.setDescription(sharedLink.getDescription());
            newLink.setIcon(sharedLink.getIcon());
            newLink.setCategoryId(targetCategoryId);
            newLink.setIsShared("0"); // 新复制的链接默认不共享
            newLink.setOriginalLinkId(sharedLink.getLinkId()); // 记录原始共享链接ID
            
            return this.save(newLink);
        }
        return false;
    }
}
