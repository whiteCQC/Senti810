package com.senti.controller;

import com.senti.serivce.UserSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserSerivce userSerivce;

    @RequestMapping("/login/{loginInfo}")
    @ResponseBody
    public String login(@PathVariable("loginInfo") ArrayList<String> loginInfo, HttpServletRequest request){
        String account = loginInfo.get(0);
        String password = loginInfo.get(1);

        int id = userSerivce.Login(account,password);
        if (id!=-1) {
            HttpSession session = request.getSession(true);
            session.setAttribute("userId", id);
            session.setAttribute("Account",account);
            return "success";
        }else{
            return "not Exist";
        }
    }
}
