package com.senti.controller;




import com.senti.model.codeComment.MessageSentihht;
import com.senti.model.codeComment.author;
import com.senti.serivce.GitService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class ContributorsController  {



    @Autowired
    GitService gitService;

    @GetMapping("/contributors")
    public String getwithdate(Model model, @RequestParam(required=false) String date, HttpServletRequest request){
        HttpSession session=request.getSession();
        String owner=(String)session.getAttribute("owner");
        String repo=(String)session.getAttribute("repo");
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




    @GetMapping("/contributors/{owner}/{repo}")
    public String doGet(@PathVariable("owner") String owner, @PathVariable("repo") String repo, Model model, @RequestParam(required=false) String date){

        List<MessageSentihht> messageSentis;
        Map<author, List<MessageSentihht>> map;

        if (date!=null) {

            messageSentis= gitService.getCommitSentihht(owner, repo, date);

            map = gitService.getCommitSentiSortbyAuthor(owner, repo, date);
        }else{

            map=gitService.getCommitSentiSortbyAuthor(owner,repo);
            messageSentis=gitService.getCommitSentihht(owner,repo);
        }

        model.addAttribute("owner",owner);
        model.addAttribute("messlist",messageSentis);
        model.addAttribute("map",map);

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
