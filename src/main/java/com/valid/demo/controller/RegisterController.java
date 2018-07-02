package com.valid.demo.controller;


import com.valid.demo.valid.BodyValid;
import com.valid.demo.valid.RegisterForm;
import com.valid.demo.valid.reps.RepValidError;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.UUID;

@Controller
public class RegisterController {


    @GetMapping("")
    public String register() {
        return "register";
    }

    //获取主键
    public String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    //表单验证
    @PostMapping("")
    public ModelAndView valid(@Valid RegisterForm registerForm, BindingResult result) {

        RepValidError rve = new BodyValid().validErros(result);
        ModelAndView mv;
        if (rve !=null) {
            mv = new ModelAndView("register");
            mv.addObject("errors", rve);
            return mv;
        }else{
            return new ModelAndView("result");
        }

    }
    //TODO: token 令牌的使用
    //TODO: DAO层添加
    //TODO: druid德鲁伊

}

