package com.valid.demo.controller;


import com.google.code.kaptcha.Producer;
import com.valid.demo.kaptcha.Kaptcha;
import com.valid.demo.valid.BodyValid;
import com.valid.demo.valid.RegisterForm;
import com.valid.demo.valid.reps.RepValidError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.UUID;

@Controller
public class RegisterController extends Kaptcha {


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
    public ModelAndView valid(@Valid RegisterForm registerForm, BindingResult result, HttpServletRequest req) {

        RepValidError rve = new BodyValid().validErros(result);
        ModelAndView mv;
        if (rve !=null) {
            mv = new ModelAndView("register");
            mv.addObject("errors", rve);
            return mv;
        }else{
            return new ModelAndView("result");
        }
        //TODO:验证码验证
    }

    @GetMapping("/kapcha")
    public void getKapcha(HttpServletRequest req, HttpServletResponse resp) {

        //生成验证码内容
        String kapText = super.generateKapText();

        try {
            //生成验证码图片，并输出
            super.kaptcha(req, resp, kapText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //TODO: token 令牌的使用
    //TODO: DAO层添加
    //TODO: druid德鲁伊

}

