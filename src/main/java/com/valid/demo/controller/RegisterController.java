package com.valid.demo.controller;


import com.google.code.kaptcha.Producer;
import com.valid.demo.kaptcha.CaptchaDto;
import com.valid.demo.kaptcha.Kaptcha;
import com.valid.demo.valid.BodyValid;
import com.valid.demo.valid.RegisterForm;
import com.valid.demo.valid.reps.RepValidError;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.UUID;

@Controller
public class RegisterController extends Kaptcha {


    @GetMapping("")
    public String register(HttpServletRequest req, HttpSession session,HttpServletResponse resp) {
        String uuid = UUID.randomUUID().toString();
        String kapText = super.generateKapText();
        CaptchaDto cap = new CaptchaDto(uuid, kapText);
        //数据放入session中
        session.setAttribute("kaptcha",cap);
        Cookie cookie = new Cookie("uuid", uuid);
        resp.addCookie(cookie);
        return "register";
    }


    //表单验证
    @PostMapping("")
    public ModelAndView valid(@Valid RegisterForm registerForm, BindingResult result,HttpSession session) {

        //表单字段验证
        RepValidError rve = new BodyValid().validErros(result);
        ModelAndView mv;
        if (rve !=null) {
            mv = new ModelAndView("register");
            mv.addObject("errors", rve.getErrors());
            return mv;
        }else{
            //验证码验证
            String kaptcha = registerForm.getKaptchaId();
            CaptchaDto cap = (CaptchaDto) session.getAttribute("kaptcha");
            String s = cap.getKaptcha();
            if (!kaptcha.equals(s)) {
                mv = new ModelAndView("register");
                mv.addObject("kaptcha", "验证码输入错误");
                return mv;
            }
            //验证完成后进行删除
            session.removeAttribute("kaptcha");
            return new ModelAndView("result");
        }
        //TODO:持久化
    }

    @GetMapping("/kapcha")
    public void getKapcha(HttpServletRequest req, HttpServletResponse resp,HttpSession session) {
//TODO:cookie可以去掉了
        CaptchaDto cap = (CaptchaDto) session.getAttribute("kaptcha");
        Cookie[] cookies = req.getCookies();
        String uuid = null ;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("uuid")) {
                uuid = cookie.getValue();
            }
        }
        //判断uuid
        if (uuid == null || uuid.isEmpty()) {
            return;
        }

        String kapText = cap.getKaptcha();
        //生成验证码内容


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

