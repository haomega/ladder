package com.valid.demo.controller;


import com.valid.demo.dao.UserMapper;
import com.valid.demo.entity.User;
import com.valid.demo.kaptcha.Kaptcha;
import com.valid.demo.service.UserServices;
import com.valid.demo.valid.BodyValid;
import com.valid.demo.form.UserForm;
import com.valid.demo.valid.reps.RepValidError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

@Controller
public class RegisterController extends Kaptcha {

    @Autowired
    UserServices userServices;

    private static final Logger logger = LogManager.getLogger();

//TODO:确定要用uuid做主键?no
    @GetMapping("/reg")
    public String register(HttpSession session) {
/*        String uuid = UUID.randomUUID().toString();
        CaptchaDto cap = new CaptchaDto(uuid, kapText);
        Cookie cookie = new Cookie("uuid", uuid);
        resp.addCookie(cookie);*/
        //数据放入session中
        String kapText = super.generateKapText();
        session.setAttribute("kaptcha",kapText);
        return "register";
    }

    @GetMapping("/index")
    public String index() {
        return "result";
    }

    //表单验证
    @PostMapping("/register")
    public ModelAndView valid(@Valid UserForm userForm, BindingResult result, HttpSession session) {

        //表单字段验证
        RepValidError rve = new BodyValid().validErros(result);
        ModelAndView mv;
        if (rve !=null) {
            mv = new ModelAndView("register");
            mv.addObject("errors", rve.getErrors());
            return mv;
        }else{
            //验证码验证
            String kaptcha = userForm.getKaptchaId();
            String kapText = (String)session.getAttribute("kaptcha");
            if (!kaptcha.equals(kapText)) {
                mv = new ModelAndView("register");
                mv.addObject("kaptcha", "验证码输入错误");
                return mv;
            }
            //验证完成后进行删除
            session.removeAttribute("kaptcha");
        }
        //TODO:持久化
        userServices.save(userForm);
        User user = userServices.findUserByname(userForm.getName());

        ModelAndView mvo = new ModelAndView("result");
        mvo.addObject("user", user);
        return mvo;
    }

    @GetMapping("/kapcha")
    public void getKapcha(HttpServletRequest req, HttpServletResponse resp,HttpSession session) {
//TODO:cookie可以去掉了
        String kapText= (String)session.getAttribute("kaptcha");
       /* Cookie[] cookies = req.getCookies();
        String uuid = null ;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("uuid")) {
                uuid = cookie.getValue();
            }
        }
        //判断uuid
        if (uuid == null || uuid.isEmpty()) {
            return;
        }*/
//        String kapText = cap.getKaptcha();

        //生成验证码内容

        try {
            //生成验证码图片，并输出
            super.kaptcha(req, resp, kapText);
            logger.info("生成验证码");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO: Logger配置
    //TODO: token 令牌的使用
    //TODO: druid德鲁伊

}

