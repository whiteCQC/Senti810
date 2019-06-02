package com.senti.controller;

import com.senti.model.codeComment.MessageSentihht;

import com.senti.serivce.GitService;
import com.senti.serivce.GithubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Controller
public class ContributorsController  {

    @Autowired
    GithubService githubService;

    @Autowired
    GitService gitService;
    @GetMapping("/contributors/{owner}/{repo}")
    public String get(@PathVariable("owner") String owner,@PathVariable("repo") String repo, Model model){

        List<MessageSentihht> messageSentis=gitService.getCommitSentihht(owner,repo);
        Map<String,List<MessageSentihht>> map=gitService.getCommitSentiSortbyAuthor(owner,repo);


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



        return "/author";
    }

}
