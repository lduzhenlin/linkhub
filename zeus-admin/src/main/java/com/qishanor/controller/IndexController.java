package com.qishanor.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Data
@RestController
public class IndexController {


    @SaIgnore
    @RequestMapping("/")
    public Object index(ModelAndView mv, HttpServletRequest request){

        mv.setViewName("front/index");
        return mv;
    }
}
