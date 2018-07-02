package com.valid.demo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegisterForm {
    @NotNull
    @Size(min = 2,max = 10,message = "名字再2-10之间")
    private String name;

    @NotNull
    private String password;

}
