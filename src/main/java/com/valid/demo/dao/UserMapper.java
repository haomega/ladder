package com.valid.demo.dao;


import com.valid.demo.entity.User;

public interface UserMapper {
    void save(User user);

    User findUserByName(String name);
}
//TODO: 使用generator工具生成Mapper和xml