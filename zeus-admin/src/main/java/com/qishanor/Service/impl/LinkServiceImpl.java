package com.qishanor.Service.impl;

import com.qishanor.Service.LinkService;
import com.qishanor.entity.Link;
import com.qishanor.mapper.LinkMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {
}
