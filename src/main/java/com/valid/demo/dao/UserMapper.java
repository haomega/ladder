package com.valid.demo.dao;


import com.valid.demo.entity.User;

public interface UserMapper {
    void save(User user);

    User findUserByName(String name);
}
