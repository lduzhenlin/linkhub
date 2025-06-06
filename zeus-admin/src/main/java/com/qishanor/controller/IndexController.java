package com.qishanor.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Data
@RestController
public class IndexController {

    @Value("${app.base-url}")
    private String baseUrl;

    @RequestMapping(value={"","/"})
    public Object index(ModelAndView mv, HttpServletRequest request){

        // 构建完整应用地址
//        String baseUrl = request.getScheme() + "://" +
//                request.getServerName() +
//                (request.getServerPort() != 80 ? ":" + request.getServerPort() : "") +
//                request.getContextPath();

        mv.addObject("baseUrl", baseUrl);

        mv.addObject("msg","hello world!");
        mv.setViewName("front/index");
        return mv;
    }
}
