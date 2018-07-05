package com.valid.demo.service.impl;

import com.valid.demo.dao.UserMapper;
import com.valid.demo.entity.User;
import com.valid.demo.form.UserForm;
import com.valid.demo.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    UserMapper userMapper;

    @Override
    public void save(UserForm userForm) {
        User user = new User();
        user.setName(userForm.getName());
        user.setPassword(userForm.getPassword());
        userMapper.save(user);
    }

    @Override
    public User findUserByname(String name) {
        return userMapper.findUserByName(name);
    }
}
