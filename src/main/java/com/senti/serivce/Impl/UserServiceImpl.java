package com.senti.serivce.Impl;

import com.senti.dao.UserDao;
import com.senti.model.User;
import com.senti.serivce.UserSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="UserService")
public class UserServiceImpl implements UserSerivce {

    @Autowired
    private UserDao userDao;

    @Override
    public int Login(String account ,String password) {
        User user=new User();
        user.setName(account);
        user.setPassword(password);

        User u=userDao.Login(user);
        if(u==null)
            return -1;
        else
            return u.getUserid();
    }
}
