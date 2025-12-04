package com.qishanor.Service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qishanor.entity.Link;
import com.qishanor.Service.LinkService;
import com.qishanor.mapper.LinkMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Override
    @Transactional
    public boolean saveLinkOrder(String categoryId, List<Long> linkIds) {

        // 1. 前置校验：避免空指针/无效循环
        if (CollectionUtils.isEmpty(linkIds) || categoryId == null) {
            return false; // 无数据直接返回，无需执行后续逻辑
        }

        // 2. 批量查询所有link，仅1次数据库操作（代替循环getById）
        List<Link> linkList = this.listByIds(linkIds);
        if (CollectionUtils.isEmpty(linkList)) {
            return false;
        }


        // 构建ID到Link的映射，解决顺序不一致问题
        Map<Long, Link> linkIdMap = linkList.stream()
                .filter(Objects::nonNull) // 过滤null元素
                .collect(Collectors.toMap(
                        Link::getLinkId, // key：link的ID
                        Function.identity(), // value：link本身
                        (existing, replacement) -> existing // 处理重复ID：保留第一个
                ));

        // 3. 过滤+赋值sort，收集需要更新的link
        List<Link> updateLinkList = new ArrayList<>();
        for (int i = 0; i < linkIds.size(); i++) {
            // 按linkIds的顺序匹配对应的link（listByIds返回顺序与入参ID顺序一致）
            Link link = linkIdMap.get(linkIds.get(i));
            // 过滤：link非空且分类ID匹配
            if (link != null && categoryId.equals(link.getCategoryId())) {
                link.setSort(i + 1); // 赋值排序值
                updateLinkList.add(link);
            }
        }

        // 4. 批量更新，仅1次数据库操作（代替循环updateById）
        if (!CollectionUtils.isEmpty(updateLinkList)) {
            this.updateBatchById(updateLinkList);
        }


        return true;
    }
}
