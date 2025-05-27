package com.qishanor.admin.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qishanor.admin.Service.LinkService;
import com.qishanor.admin.entity.Link;
import com.qishanor.admin.mapper.LinkMapper;
import org.springframework.stereotype.Service;

@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {
}
