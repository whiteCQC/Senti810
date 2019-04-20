package com.senti.controller;

import com.senti.model.codeComment.ClassSenti;
import com.senti.model.codeComment.MessageSenti;
import com.senti.serivce.GitService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/git")
public class GitController {

    @Autowired
    GitService gitService;

    @RequestMapping("/search/{projectInfo}")
    @ResponseBody
    public String searchProject(@PathVariable("projectInfo") ArrayList<String> projectInfo, HttpServletRequest request){
        String owner = projectInfo.get(0);
        String repo = projectInfo.get(1);

        boolean res=gitService.ProjectDeal(owner,repo);
        if (res) {
            HttpSession session = request.getSession(true);
            session.setAttribute("owner", owner);
            session.setAttribute("repo",repo);
            return "success";
        }else{
            return "not Exist";
        }
    }

    @RequestMapping("/commitSenti")
    @ResponseBody
    public Map<String,List<String>> commitSenti(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        String owner=(String)session.getAttribute("owner");
        String repo=(String)session.getAttribute("repo");

        List<MessageSenti> mlist=gitService.getCommitSenti(owner, repo);
        List<String> Commitdates=new ArrayList<>();
        List<String> highs=new ArrayList<>();
        List<String> lows=new ArrayList<>();
        List<String> messages=new ArrayList<>();

        MessageSenti m=null;
        for(int i=0;i<mlist.size();i++) {
            m=mlist.get(i);
            Commitdates.add(m.getDate());
            highs.add(String.valueOf(m.getHigh()));
            lows.add(String.valueOf(m.getLow()));
            messages.add(m.getComment());
        }
        Map<String,List<String>> res=new HashMap<>();
        res.put("Commitdates",Commitdates);
        res.put("highs",highs);
        res.put("lows",lows);
        res.put("commitMessage",messages);

        return res;
    }


    @RequestMapping("/codeSenti")
    @ResponseBody
    public String codeSenti(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        String owner=(String)session.getAttribute("owner");
        String repo=(String)session.getAttribute("repo");

        Map<String, List<ClassSenti>> map1=gitService.getClassSenti(owner, repo);
        Map<String, List<String>> map2=gitService.getClassCode(owner, repo);

        session.setAttribute("Allcodes",map2);
        session.setAttribute("ClassSenti",map1);
        JSONArray json=new JSONArray();
        List<String> classes=new ArrayList<>(map1.keySet());

        for(String s:classes){
            JSONObject node = new JSONObject();
            node.put("name",s);

            json.add(node);
        }
        return json.toString();
    }

    @RequestMapping("/getSingleSenti/{selectClass}")
    @ResponseBody
    public Map<String,List<String>> getSingleSenti(@PathVariable("selectClass")String selectClass,HttpServletRequest request){
        HttpSession session = request.getSession(true);

        selectClass=selectClass.replace(".java","");
        selectClass=selectClass.replace(".","/")+".java";

        Map<String, List<ClassSenti>> map=(Map<String, List<ClassSenti>>)session.getAttribute("ClassSenti");

        Map<String,List<String>> res=new HashMap<>();
        List<String> dates=new ArrayList<>();
        List<String> highs=new ArrayList<>();
        List<String> lows=new ArrayList<>();
        List<String> codeComments=new ArrayList<>();

        List<ClassSenti> lcs=map.get(selectClass);

        for(ClassSenti c:lcs){
            dates.add(c.getDate());
            highs.add(String.valueOf(c.getHigh()));
            lows.add(String.valueOf(c.getLow()));
            codeComments.add(c.getComment());
        }

        res.put("dates",dates);
        res.put("codeHighs",highs);
        res.put("codeLows",lows);
        res.put("codeComments",codeComments);

        Map<String, List<String>> allCodes=(Map<String, List<String>>)session.getAttribute("Allcodes");
        List<String> codes=allCodes.get(selectClass.replace("/","\\"));

        res.put("codes",codes);

        return res;
    }
}
