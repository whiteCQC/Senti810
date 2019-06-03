package com.senti.dao;

import com.senti.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
    User Login(User user);

    void register(User user);

    int getUser(@Param("name")String name);
}