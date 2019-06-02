package com.senti.controller;

import com.senti.model.codeComment.ClassNote;
import com.senti.model.codeComment.ClassSenti;
import com.senti.model.codeComment.MessageSenti;
import com.senti.serivce.GitService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 代码模块的
 */
@Controller
@RequestMapping(value = "/git")
public class GitController {

    @Autowired
    GitService gitService;

    /**
     *
     * @param projectInfo
     * @param request
     * @return 项目搜索，三个模块整合前使用的，现在是在issueController中进行
     */
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
        List<String> dealClasses=new ArrayList<>(map1.keySet());

        List<List<String>> tops = gitService.getTopClasses(relatedClass, mlist, dealClasses);//平均值推荐

        List<String> Commitdates = new ArrayList<>();//Commit的每一次日期
        List<String> highs = new ArrayList<>();//Commit每一次的正面情绪
        List<String> lows = new ArrayList<>();//Commit的负面情绪
        List<String> messages = new ArrayList<>();//Commit的Message

        List<String> HighCount = new ArrayList<>();//用于饼图占比，正面和负面
        int[] hightemp = new int[5];
        List<String> LowCount = new ArrayList<>();
        int[] lowtemp = new int[5];

        List<String> relatedClasses = new ArrayList<>();
        Map<String,String> relatedClassesMap=new HashMap<>();//SHA为key
        Map<String,String> messagesMap=new HashMap<>();//SHA为key


        MessageSenti m = null;
        for (int i = 0; i < mlist.size(); i++) {//遍历Commit，给各个List填入信息
            m = mlist.get(i);
            Commitdates.add(m.getDate());

            List<String> tempClasses = relatedClass.get(m.getSha());
            String classes = "";
            if(tempClasses!=null){
                for (String s : tempClasses) {
                    if(dealClasses.contains(s)){
                        classes = classes+"<a href='showCode.html?selectClass="+s+"'>"+s+"</a>" + "<br>";
                    }

                }
            }

            relatedClasses.add(classes);
            relatedClassesMap.put(m.getSha(),classes);

            highs.add(String.valueOf(m.getHigh()));
            hightemp[(int) m.getHigh()]++;
            lows.add(String.valueOf(m.getLow()));
            lowtemp[-(int) m.getLow()]++;


            messages.add(m.getComment());
            messagesMap.put(m.getSha(),m.getComment());
        }
        session.setAttribute("relatedClassesMap",relatedClassesMap);
        session.setAttribute("messagesMap",messagesMap);

        for (int i = 0; i < 5; i++) {
            HighCount.add(String.valueOf(hightemp[i]));
            LowCount.add(String.valueOf(lowtemp[i]));
        }

        List<List<String>> comtops = gitService.getTopCombines(owner,repo);//占比推荐


        Map<String, List<String>> res = new HashMap<>();
        res.put("topCombineHigh", comtops.get(0));
        res.put("topCombineLow", comtops.get(1));
        res.put("topCombineHighScore", comtops.get(2));
        res.put("topCombineLowScore", comtops.get(3));

        res.put("Commitdates", Commitdates);
        res.put("highs", highs);
        res.put("lows", lows);
        res.put("commitMessage", messages);
        res.put("HighCount", HighCount);
        res.put("LowCount", LowCount);
        res.put("relatedClasses", relatedClasses);
        res.put("topHigh", tops.get(0));
        res.put("topLow", tops.get(1));
        res.put("topHighScore", tops.get(2));
        res.put("topLowScore", tops.get(3));


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

        selectClass = selectClass.substring(0,selectClass.length()-5);
        selectClass = selectClass.replace(".", "/") + ".java";//传入的时候因为“/”分隔符会出错，该成了“.”，这里是复原
        List<String> toOpen = new ArrayList<>(Arrays.asList(selectClass.split("/")));//需要展开显示的

        /*
            文件结构的Json树形结构数据
         */
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

        String owner = (String) session.getAttribute("owner");
        String repo = (String) session.getAttribute("repo");
        int userid = (Integer) session.getAttribute("userid");

        Map<String,List<ClassNote>> allnotes= gitService.getNotes(owner,repo,userid);

        Map<String, List<String>> res = new HashMap<>();
        List<String> dates = new ArrayList<>();//修改日期
        List<String> highs = new ArrayList<>();//正面情绪
        List<String> lows = new ArrayList<>();//负面情绪
        List<String> codeComments = new ArrayList<>();//注释变更

        String path = "";

        if (null != paths && null != paths.get(selectClass))
            path = paths.get(selectClass);
        else {
            selectClass = selectClass.substring(0,selectClass.length()-5);
            path = selectClass.replace(".", "/") + ".java";
        }

        System.out.println(path);
        List<ClassSenti> lcs = map.get(path);

        List<String> shas=new ArrayList<>();
        for (int i = 0; i < lcs.size(); i++) {
            ClassSenti c = lcs.get(i);
            dates.add(c.getDate());
            highs.add(String.valueOf(c.getHigh()));
            lows.add(String.valueOf(c.getLow()));
            codeComments.add(c.getComment());
            shas.add(c.getSha());
        }

        res.put("dates", dates);
        res.put("codeHighs", highs);
        res.put("codeLows", lows);
        res.put("codeComments", codeComments);

        List<String> notes=new ArrayList<>();
        List<String> times=new ArrayList<>();

        if(allnotes.containsKey(path)){
            List<ClassNote> list=allnotes.get(path);

            for(ClassNote cn:list){
                notes.add(cn.getNote());
                String time=cn.getTime().replaceFirst("\\/","年").replace("/","月").replace(" ","日");
                times.add(time);
            }
        }
        res.put("notes",notes);//留言
        res.put("noteTimes",times);//留言时间


        Map<String, List<String>> allCodes = (Map<String, List<String>>) session.getAttribute("Allcodes");
        List<String> codes = allCodes.get(path);

        res.put("codes", codes);

        Map<String,String> relatedClassesMap=(Map<String,String>)session.getAttribute("relatedClassesMap");
        Map<String,String> messagesMap=(Map<String,String>)session.getAttribute("messagesMap");
        List<String> classes=new ArrayList<>();
        List<String> messages=new ArrayList<>();
        for(String sha:shas){//显示指定的某次修改对应的Commit的message以及涉及到的所有的类
            if(null!=relatedClassesMap.get(sha)){
                classes.add(relatedClassesMap.get(sha));
                messages.add(messagesMap.get(sha));
            }

        }

        res.put("CommitClasses",classes);
        res.put("CommitMessages",messages);

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

    /**
     *
     * @param time
     * @param selectClass
     * @param note
     * @param request
     * 添加留言
     */
    @RequestMapping("/addNote/{timers}")
    @ResponseBody
    public void addNote(@PathVariable("timers") String time, @RequestParam("selectClass") String selectClass
            , @RequestParam("note") String note,HttpServletRequest request) {
        time=time.replace("日"," ").replace("月","/").replace("年","/");

        selectClass = selectClass.replace("%20", " ");

        HttpSession session = request.getSession(true);
        Map<String, String> paths = (Map<String, String>) session.getAttribute("TreePaths");

        String path = paths.get(selectClass);

        String owner=(String)session.getAttribute("owner");
        String repo=(String)session.getAttribute("repo");
        int userid=(Integer)session.getAttribute("userid");
        if(note.trim().length()>0&&selectClass.trim().length()>0)
            gitService.addNote(note,time,path,owner,repo,userid);

    }

    /**
     *
     * @param times
     * @param request
     * @return 代码类按时间段进行推荐
     */
    @RequestMapping("/commitbyTime/{times}")
    @ResponseBody
    public Map<String, List<String>> commitbyTime(@PathVariable("times") List<String> times,HttpServletRequest request) {
        String start= times.get(0);
        String end=times.get(1);

        HttpSession session = request.getSession(true);
        String owner=(String)session.getAttribute("owner");
        String repo=(String)session.getAttribute("repo");
        Map<String, List<String>> relatedClass = gitService.getCommitRelatedClasses(owner, repo);


        Map<String, List<ClassSenti>> map1=(Map<String, List<ClassSenti>>)session.getAttribute("ClassSenti");

        List<MessageSenti> mlist=gitService.getCommitSentiWithTime(owner,repo,start,end);

        List<List<String>> tops = gitService.getTopClasses(relatedClass, mlist, new ArrayList<>(map1.keySet()));
        List<List<String>> comtops = gitService.getTopCombinesWithTime(owner,repo,start,end);

        Map<String, List<String>> res=new HashMap<>();

        res.put("topHigh", tops.get(0));
        res.put("topLow", tops.get(1));
        res.put("topHighScore", tops.get(2));
        res.put("topLowScore", tops.get(3));

        res.put("topCombineHigh", comtops.get(0));
        res.put("topCombineLow", comtops.get(1));
        res.put("topCombineHighScore", comtops.get(2));
        res.put("topCombineLowScore", comtops.get(3));

        return res;
    }
}
