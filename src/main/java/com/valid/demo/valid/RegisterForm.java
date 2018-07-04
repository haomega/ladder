package com.valid.demo.valid;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class RegisterForm {
    //注意：notempty 和notnull不同
    @NotEmpty(message = "账户不能为空")
    @Size(min = 2,max = 10,message = "名称长度需要在2到10字符之间")
    private String name;

    @NotEmpty(message = "密码不能为空！")
    private String password;

    @NotEmpty(message = "请输入验证码！")
    private String kaptchaId;

}
