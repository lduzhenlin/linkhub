package com.qishanor.Service;

import com.qishanor.entity.Link;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface LinkService extends IService<Link> {
    
    /**
     * 共享链接
     * @param linkId 链接ID
     * @return 操作结果
     */
    boolean shareLink(Long linkId);
    
    /**
     * 取消共享链接
     * @param linkId 链接ID
     * @return 操作结果
     */
    boolean unshareLink(Long linkId);
    
    /**
     * 获取所有共享的链接
     * @return 共享链接列表
     */
    List<Link> getSharedLinks();
    
    /**
     * 复制共享链接到个人分类
     * @param sharedLinkId 共享链接ID
     * @param targetCategoryId 目标分类ID
     * @param currentUsername 当前用户名
     * @return 操作结果
     */
    boolean copySharedLink(Long sharedLinkId, String targetCategoryId, String currentUsername);
}
