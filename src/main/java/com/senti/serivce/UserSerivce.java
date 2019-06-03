package com.senti.serivce;

import com.senti.model.User;

public interface UserSerivce {
    int Login(String account,String password);

    boolean userExist(String name);

    void register(String account,String password);
}
