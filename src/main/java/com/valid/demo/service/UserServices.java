package com.valid.demo.service;

import com.valid.demo.entity.User;
import com.valid.demo.form.UserForm;

public interface UserServices {
    void save(UserForm userForm);

    User findUserByname(String name);
}
