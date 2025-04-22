package com.qishanor.admin.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qishanor.admin.Service.CategoryService;
import com.qishanor.admin.entity.Category;
import com.qishanor.admin.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {


}
