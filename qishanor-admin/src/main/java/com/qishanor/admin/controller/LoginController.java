package com.qishanor.admin.controller;

import com.qishanor.admin.entity.SysUser;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@Data
@RestController
@RequestMapping("/api")
public class LoginController {

    @RequestMapping("/login")
    public Object login(SysUser sysUser, ModelAndView mv){

        if(null==sysUser.getUsername()||null==sysUser.getPassword()){
            mv.setViewName("front/login");
            return mv;
        }

        mv.setViewName("front/index");
        return mv;
    }
}
