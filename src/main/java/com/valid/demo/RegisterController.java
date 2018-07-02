package com.valid.demo;


import com.valid.demo.reps.RepValidError;
import com.valid.demo.valid.BodyValid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RegisterController {

    @GetMapping("")
    public String register() {
        return "register";
    }

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
}

