package com.senti.controller;

import com.senti.model.GithubUser;
import com.senti.model.codeComment.Commits;
import com.senti.model.codeComment.MessageSenti;
import com.senti.model.codeComment.MessageSentihht;
import com.senti.model.codeComment.author;
import com.senti.serivce.GitService;
import com.senti.serivce.GithubService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ContributorsController  {

    @Autowired
    GithubService githubService;

    @Autowired
    GitService gitService;

    @GetMapping("/contributors/{owner}/{repo}")
    public String getwithdate(@PathVariable("owner") String owner,@PathVariable("repo") String repo, Model model, @RequestParam(required=false) String date){
        List<MessageSentihht> messageSentis;
        Map<author, List<MessageSentihht>> map;

        if (date!=null) {

            messageSentis= gitService.getCommitSentihht(owner, repo, date);

           map = gitService.getCommitSentiSortbyAuthor(owner, repo, date);
        }else{
            messageSentis=gitService.getCommitSentihht(owner,repo);
             map=gitService.getCommitSentiSortbyAuthor(owner,repo);
        }


        model.addAttribute("messlist",messageSentis);
        model.addAttribute("map",map);
        model.addAttribute("owner",owner);
        model.addAttribute("repo",repo);

        return "/contributors";

    }



    @GetMapping("/contributors/{owner}/{repo}/{author}")
    public String get(@PathVariable("owner") String owner,@PathVariable("repo") String repo,@PathVariable("author") String author, Model model){
        List<MessageSentihht> messageSentis=gitService.getCommitSentibyAuthor(owner,repo,author);

        model.addAttribute("list",messageSentis);

        model.addAttribute("owner",owner);
        model.addAttribute("repo",repo);

        return "/author";
    }

}
