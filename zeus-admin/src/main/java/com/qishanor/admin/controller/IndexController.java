package com.qishanor.admin.controller;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Data
@RestController
public class IndexController {

    @RequestMapping(value={"","/"})
    public Object index(ModelAndView mv){
        System.out.println("进入。。。。");
        mv.addObject("msg","hello world!");
        mv.setViewName("front/index");
        return mv;
    }
}
