package com.senti.controller;

import com.senti.model.GithubUser;
import com.senti.model.codeComment.Commits;
import com.senti.model.codeComment.MessageSenti;
import com.senti.serivce.GitService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class ContributorsController  {

    @Autowired
    GitService gitService;
    @GetMapping("/contributors")
    public String get(){
        List<Commits> list=gitService.getCommitByAuthor("mxgmn","WaveFunctionCollapse","ExUtumno");
        GithubUser githubUser=gitService.findGithubUserbyName("ExUtumno");
        System.out.println(githubUser.getAvatar_url());
//        for (Commits c:list){
//            System.out.println(c.getMessage()+"  "+c.getAuthor());
//        }
//        System.out.println(list);
        return "/contributors";


    }
}
