package com.qishanor.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.qishanor.entity.Category;
import com.qishanor.Service.CategoryService;
import com.qishanor.common.core.util.R;
import com.qishanor.entity.req.CategoryRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@SaCheckLogin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/list")
    public Object list(){
       List<Category> categoryList = categoryService.lambdaQuery()
               .orderByAsc(Category::getSort)
               .list();
       return R.ok(categoryList);
    }
    @PostMapping()
    public Object save(@Validated CategoryRequest request){
        Category category = new Category();
        BeanUtil.copyProperties(request,category);

        Category maxSortCategory = categoryService.lambdaQuery()
                .select(Category::getSort)
                .orderByDesc(Category::getSort)
                .last("limit 1")
                .one();

        int nextSort = (maxSortCategory == null || maxSortCategory.getSort() == null) ? 1 : maxSortCategory.getSort() + 1;
        category.setSort(nextSort);
        categoryService.save(category);
        return R.ok();
    }

    @PutMapping()
    public Object edit(@Validated CategoryRequest request){
        Category category = new Category();
        BeanUtil.copyProperties(request,category);

        Category dbCategory=categoryService.getById(category.getCategoryId());
        if(ObjUtil.isEmpty(dbCategory)){
            return R.failed("数据不存在");
        }

        dbCategory.setName(category.getName());
        categoryService.updateById(dbCategory);
        return R.ok();
    }

    @DeleteMapping
    public Object remove(Long categoryId){
        categoryService.removeById(categoryId);

        return R.ok();
    }

    @PostMapping("/sort")
    public Object sort(String categoryIds){
        if(StrUtil.isBlank(categoryIds)){
            return R.failed("排序数据不能为空");
        }

        try{
            List<Long> ids = JSONUtil.toList(categoryIds, Long.class);
            boolean success = categoryService.saveCategoryOrder(ids);
            return success ? R.ok() : R.failed("分类排序失败");
        }catch (Exception e){
            return R.failed("分类排序失败:" + e.getMessage());
        }
    }
}
