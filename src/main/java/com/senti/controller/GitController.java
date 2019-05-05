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
import java.util.*;

@Controller
@RequestMapping(value = "/git")
public class GitController {

    @Autowired
    GitService gitService;

    @RequestMapping("/search/{projectInfo}")
    @ResponseBody
    public String searchProject(@PathVariable("projectInfo") ArrayList<String> projectInfo, HttpServletRequest request) {
        String owner = projectInfo.get(0);
        String repo = projectInfo.get(1);

        boolean res = gitService.ProjectDeal(owner, repo);
        if (res) {
            HttpSession session = request.getSession(true);

            session.removeAttribute("Allcodes");
            session.removeAttribute("ClassSenti");

            session.setAttribute("owner", owner);
            session.setAttribute("repo", repo);
            return "success";
        } else {
            return "not Exist";
        }
    }

    @RequestMapping("/commitSenti")
    @ResponseBody
    public Map<String, List<String>> commitSenti(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String owner = (String) session.getAttribute("owner");
        String repo = (String) session.getAttribute("repo");

        Map<String, List<ClassSenti>> map1 = gitService.getClassSenti(owner, repo);
        if (null == session.getAttribute("ClassSenti"))
            session.setAttribute("ClassSenti", map1);
        if (null == session.getAttribute("Allcodes"))
            session.setAttribute("Allcodes", gitService.getClassCode(owner, repo));

        List<MessageSenti> mlist = gitService.getCommitSenti(owner, repo);

        Map<String, List<String>> relatedClass = gitService.getCommitRelatedClasses(owner, repo);
        List<String> dealClasses=new ArrayList<String>(map1.keySet());

        List<List<String>> tops = gitService.getTopClasses(relatedClass, mlist, dealClasses);

        List<String> Commitdates = new ArrayList<>();
        List<String> highs = new ArrayList<>();
        List<String> lows = new ArrayList<>();
        List<String> messages = new ArrayList<>();

        List<String> HighCount = new ArrayList<>();
        List<String> LowCount = new ArrayList<>();
        List<String> relatedClasses = new ArrayList<>();
        int[] hightemp = new int[4];
        int[] lowtemp = new int[4];

        MessageSenti m = null;
        for (int i = 0; i < mlist.size(); i++) {
            m = mlist.get(i);
            Commitdates.add(m.getDate());

            List<String> tempClasses = relatedClass.get(m.getSha());
            String classes = "";
            for (String s : tempClasses) {
                if(dealClasses.contains(s)){
                    classes = classes+"<a href='showCode.html?selectClass="+s+"'>"+s+"</a>" + "<br>";
                }

            }
            relatedClasses.add(classes);

            highs.add(String.valueOf(m.getHigh()));
            hightemp[(int) m.getHigh()]++;
            lows.add(String.valueOf(m.getLow()));
            lowtemp[-(int) m.getLow()]++;


            messages.add(m.getComment());
        }
        for (int i = 0; i < 4; i++) {
            HighCount.add(String.valueOf(hightemp[i]));
            LowCount.add(String.valueOf(lowtemp[i]));
        }


        Map<String, List<String>> res = new HashMap<>();
        res.put("Commitdates", Commitdates);
        res.put("highs", highs);
        res.put("lows", lows);
        res.put("commitMessage", messages);
        res.put("HighCount", HighCount);
        res.put("LowCount", LowCount);
        res.put("relatedClasses", relatedClasses);
        res.put("topHigh", tops.get(0));
        res.put("topLow", tops.get(1));


        return res;
    }


    @RequestMapping("/codeSenti/{selectClass}")
    @ResponseBody
    public String codeSenti(@PathVariable("selectClass") String selectClass, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Map<String, List<ClassSenti>> map1 = (Map<String, List<ClassSenti>>) session.getAttribute("ClassSenti");
        Map<String, List<String>> map2;

        if (map1 == null) {
            String owner = (String) session.getAttribute("owner");
            String repo = (String) session.getAttribute("repo");

            map1 = gitService.getClassSenti(owner, repo);
            map2 = gitService.getClassCode(owner, repo);

            session.setAttribute("Allcodes", map2);
            session.setAttribute("ClassSenti", map1);
        }

        selectClass = selectClass.replace(".java", "");
        selectClass = selectClass.replace(".", "/") + ".java";
        List<String> toOpen = new ArrayList<>(Arrays.asList(selectClass.split("/")));

        JSONArray json = new JSONArray();
        List<String> classes = new ArrayList<>(map1.keySet());

        List<String> list = new ArrayList<>();
        Map<String, String> paths = new HashMap<>();

        JSONObject n = new JSONObject();
        n.put("opened", true);

        for (String s : classes) {

            String[] temp = ("#/" + s).split("/");
            int len = temp.length;
            for (int i = 0; i < len - 2; i++) {
                String ss = temp[i] + "/" + temp[i + 1];
                if (!list.contains(ss)) {
                    list.add(ss);
                    JSONObject node = new JSONObject();
                    node.put("id", temp[i + 1]);
                    node.put("parent", temp[i]);
                    node.put("text", temp[i + 1]);
                    if (toOpen.contains(temp[i + 1])) {
                        node.put("state", n);
                    }
                    json.add(node);
                }
            }
            String ss = temp[len - 2] + "/" + temp[len - 1];
            if (!list.contains(ss)) {
                list.add(ss);
                JSONObject node = new JSONObject();
                node.put("id", temp[len - 1]);
                node.put("parent", temp[len - 2]);
                node.put("text", temp[len - 1]);
                if (toOpen.contains(temp[len - 1])) {
                    node.put("state", n);
                }
                json.add(node);
                paths.put(temp[len - 1], s);
            }

        }

        session.setAttribute("TreePaths", paths);
        return json.toString();
    }

    @RequestMapping("/getSingleSenti/{selectClass}")
    @ResponseBody
    public Map<String, List<String>> getSingleSenti(@PathVariable("selectClass") String selectClass, HttpServletRequest request) {

        selectClass = selectClass.replace("%20", " ");

        HttpSession session = request.getSession(true);

        Map<String, String> paths = (Map<String, String>) session.getAttribute("TreePaths");

        Map<String, List<ClassSenti>> map = (Map<String, List<ClassSenti>>) session.getAttribute("ClassSenti");

        Map<String, List<String>> res = new HashMap<>();
        List<String> dates = new ArrayList<>();
        List<String> highs = new ArrayList<>();
        List<String> lows = new ArrayList<>();
        List<String> codeComments = new ArrayList<>();

        String path = "";

        if (null != paths && null != paths.get(selectClass))
            path = paths.get(selectClass);
        else {
            selectClass = selectClass.replace(".java", "");
            path = selectClass.replace(".", "/") + ".java";
        }


        List<ClassSenti> lcs = map.get(path);

        for (int i = 0; i < lcs.size(); i++) {
            ClassSenti c = lcs.get(i);
            dates.add(c.getDate());
            highs.add(String.valueOf(c.getHigh()));
            lows.add(String.valueOf(c.getLow()));
            codeComments.add(c.getComment());
        }

        res.put("dates", dates);
        res.put("codeHighs", highs);
        res.put("codeLows", lows);
        res.put("codeComments", codeComments);

        Map<String, List<String>> allCodes = (Map<String, List<String>>) session.getAttribute("Allcodes");
        List<String> codes = allCodes.get(path);

        res.put("codes", codes);

        return res;
    }

    @RequestMapping("/projectExist")
    @ResponseBody
    public String HasSearch(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        if (null == session.getAttribute("owner"))
            return "no";
        else
            return "yes";

    }

}
