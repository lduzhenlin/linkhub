package com.qishanor.Service.impl;

import cn.hutool.core.collection.CollUtil;
import com.qishanor.Service.CategoryService;
import com.qishanor.entity.Category;
import com.qishanor.mapper.CategoryMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveCategoryOrder(List<Long> categoryIds) {
        if (CollUtil.isEmpty(categoryIds)) {
            return false;
        }

        List<Category> categories = this.listByIds(categoryIds);
        if (CollUtil.isEmpty(categories)) {
            return false;
        }

        Map<Long, Category> categoryMap = categories.stream()
                .collect(Collectors.toMap(Category::getCategoryId, category -> category));

        List<Category> needUpdate = new ArrayList<>();
        for (int i = 0; i < categoryIds.size(); i++) {
            Long categoryId = categoryIds.get(i);
            Category category = categoryMap.get(categoryId);
            if (category == null) {
                continue;
            }
            category.setSort(i + 1);
            needUpdate.add(category);
        }

        if (needUpdate.isEmpty()) {
            return false;
        }

        return this.updateBatchById(needUpdate);
    }
}
