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
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserSerivce userSerivce;

    @RequestMapping("/login/{loginInfo}")
    @ResponseBody
    public String login(@PathVariable("loginInfo") List<String> loginInfo, HttpServletRequest request){
        String account = loginInfo.get(0);
        String password = loginInfo.get(1);

        int id = userSerivce.Login(account,password);
        if (id!=-1) {
            HttpSession session = request.getSession(true);
            session.setAttribute("userid", id);
            session.setAttribute("username",account);
            return "success";
        }else{
            return "not Exist";
        }
    }

    @RequestMapping("/register/{registerInfo}")
    @ResponseBody
    public String register(@PathVariable("registerInfo") List<String> registerInfo){
        String account = registerInfo.get(0);
        String password = registerInfo.get(1);

        if (!userSerivce.userExist(account)) {
            userSerivce.register(account,password);
            return "success";
        }else{
            return "Exist";
        }
    }
}
