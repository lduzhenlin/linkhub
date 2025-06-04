package com.qishanor.admin.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.util.ObjUtil;
import com.qishanor.admin.Service.CategoryService;
import com.qishanor.admin.entity.Category;
import com.qishanor.common.core.util.R;
import lombok.Data;
import lombok.RequiredArgsConstructor;
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
       List<Category> categoryList=categoryService.list();
       return R.ok(categoryList);
    }
    @PostMapping()
    public Object save(Category category){
//        Long tenantId=(Long)StpUtil.getSession().get(CacheConstant.TENANT_ID);
//        category.setTenantId(tenantId);

        categoryService.save(category);
        return R.ok();
    }

    @PutMapping()
    public Object edit(Category category){
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
}
