package com.qishanor.Service;

import com.qishanor.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;


import java.util.List;

public interface CategoryService extends IService<Category> {

    /**
     * 批量保存分类排序
     * @param categoryIds 排序后的分类ID列表
     * @return 是否保存成功
     */
    boolean saveCategoryOrder(List<Long> categoryIds);
}
