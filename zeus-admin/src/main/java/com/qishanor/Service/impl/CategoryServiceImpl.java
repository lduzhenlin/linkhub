package com.qishanor.Service.impl;

import com.qishanor.Service.CategoryService;
import com.qishanor.entity.Category;
import com.qishanor.mapper.CategoryMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {


}
