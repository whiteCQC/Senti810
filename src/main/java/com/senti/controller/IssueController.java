package com.senti.controller;

import com.senti.serivce.Database;
import com.senti.serivce.DealComment;
import com.senti.serivce.GitService;
import com.senti.serivce.Impl.CrawlCommentImpl;
import com.senti.serivce.Impl.DatabaseImpl;
import com.senti.serivce.Impl.DealCommentImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
@RequestMapping("/search")
public class IssueController {

    @Autowired
    GitService gitService;


    @RequestMapping("/home")
    public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {//登录模块
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        try{
            String name=request.getParameter("username");
            String password=request.getParameter(("password"));
            Database da= new DatabaseImpl();
            ArrayList<String> info=da.getUser(name);
            if(info.size()!=0 && info.get(2).equals(password)){//存在该用户
                HttpSession session=request.getSession();
                session.setAttribute("username",name);  //用户名存入session
                session.setAttribute(("userid"),info.get(0));//用户名对应编号存入session
                System.out.println(name+info.get(0));
                return "SearchView";
            }else{
                System.out.println("用户名或密码错误");
                response.getWriter().print("<script> alert(\"请确认您的账号密码!\"); </script>");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "index";
    }

//    @RequestMapping("/login")//登录界面
//    public String login(){
//        return "mylogin";
//    }

    @RequestMapping("/input")//主页搜索界面
    public String input(HttpServletRequest request){
        HttpSession session=request.getSession();
        if(session.getAttribute("userid").toString()==null){
            return "index";
        }

        return "SearchView";
    }


    @RequestMapping("/history")//搜索模块
    public String test(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        //将输入的网址处理(输入的格式为https://github.com/TheAlgorithms/Java)
        try {
            String webContent = request.getParameter("url");//获取搜索框传来的url
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String time= df.format(new Date());// new Date()为获取当前系统时间
//            System.out.println(time);
            HttpSession session=request.getSession();
//            session.setAttribute("time", time);                   //时间在session里1

            if(session.getAttribute("userid").toString()==null){
                return "index";
            }
            int userid=(int)session.getAttribute("userid");//userID在session里

            Database da= new DatabaseImpl();
            String url="";
            if(webContent!=null) {
                if (webContent.contains("DERBY")) {
//                    session.setAttribute("name", "DERBY");       //名字在session里2
                    url="DERBY";
                }
                else if (webContent.contains("DROOLS")) {
//                    session.setAttribute("name", "DROOLS");
                    url="DROOLS";
                }
                else if (webContent.contains("GROOVY")) {
//                    session.setAttribute("name", "GROOVY");
                    url="GROOVY";
                }
                else if (webContent.contains("ISPN")) {
 //                   session.setAttribute("name", "ISPN");
                    url="ISPN";
                }
                else if (webContent.contains("MNG")) {
 //                   session.setAttribute("name", "MNG");
                    url="MNG";
                }
                else if (webContent.contains("PIG")) {
//                    session.setAttribute("name", "PIG");
                    url="PIG";
                }
                else if (webContent.contains("JBSEAM")) {
 //                   session.setAttribute("name", "JBSEAM");//传递数据库名
                    url="JBSEAM";
                }
                else if (webContent.contains("https://github.com")) {
                    url=webContent;
                    /*爬取数据*/
//                    if (da.getSameTypeIssue(webContent).size() == 0) {//如果数据库中没有记录则需要爬取
                    CrawlCommentImpl crawler = new CrawlCommentImpl("crawl", webContent);
                    crawler.start(2);
                    crawler.start(2);
 //                   }
//                    session.setAttribute("url", url);
//                    session.setAttribute("name","github");

                    String [] tempurl=webContent.split("/");
                    String owner=tempurl[tempurl.length-2];
                    String repo=tempurl[tempurl.length-1];
                    gitService.ProjectDeal(owner,repo);
                    gitService.getCommitSenti(owner,repo);
                    gitService.getClassSenti(owner,repo);

                    /*计算出年度top，月度top，monthchart*/
                    DealComment DC=new DealCommentImpl();
                    ArrayList<Object> yeardata=new ArrayList<Object>();
                    ArrayList<Object> monthdata=new ArrayList<Object>();
                    yeardata=DC.getGYearTop(url);//年度数据
                    ArrayList<ArrayList<String>> issueNumber=(ArrayList<ArrayList<String>>)yeardata.get(0);
                    ArrayList<ArrayList<String>> issueDescription=(ArrayList<ArrayList<String>>)yeardata.get(1);
                    monthdata=DC.getGMonthTop(url);//月度数据
                    if(monthdata.size()==0){
                        response.getWriter().print("<script> alert(\"请输入正确的检索项!\"); </script>");
                        return "SearchView";
                    }
                    ArrayList<ArrayList<String>> issueNumber2=(ArrayList<ArrayList<String>>)monthdata.get(0);
                    ArrayList<ArrayList<String>> issueDescription2=(ArrayList<ArrayList<String>>)monthdata.get(1);
                    da.connSQL();
                    for(int i=0;i<3;i++){
                        for(int j=0;j<10;j++){
                            da.updateYear(userid, url, time, issueNumber.get(i).get(j), i*10+j+1, issueDescription.get(i).get(j));//更新历史年度top表

                         //   System.out.println("ok");
                        }
                        for(int m=0;m<24;m++){
                            da.updateMonth(userid, url, time, issueNumber2.get(i).get(m), i*24+m+1,issueDescription2.get(i).get(m));//更新历史月度top表
                        //     System.out.println("ok2");
                        }
                    }
                    da.deconnSQL();
                }
                else{
                    response.getWriter().print("<script> alert(\"请输入正确的检索项!\"); </script>");
                    return "SearchView";
                }
                da.connSQL();
                da.updatehistory(userid, url, time);//更新历史搜索表
                da.insertHistoryComment(userid, url, time,"","");//更新评论表
                da.deconnSQL();
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return "SearchView";
//        return "details";
    }

    @RequestMapping("/hisSearch")//历史模块
    @ResponseBody
    public ArrayList<ArrayList<String>> hisSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        ArrayList<ArrayList<String>> res=new ArrayList<ArrayList<String>>();
        try{
            HttpSession session=request.getSession();
            int userid=(int)session.getAttribute("userid");//userID在session里
            System.out.println(userid);
            Database da=new DatabaseImpl();
            res=da.getHistory(userid);
/*            for(int i=0;i<3;i++){
                System.out.println(res.get(i).get(2));
            }*/
        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @RequestMapping("/more")//detail的页面
    public String turn(HttpServletRequest request){
        HttpSession session=request.getSession();
        if(session.getAttribute("userid").toString()==null){
            return "index";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String viewtime = df.format(new Date());// new Date()为获取当前系统时间
        String issueweb=request.getParameter("issueweb");//url
        String searchtime=request.getParameter("searchtime");//time
        String userid=(String)session.getAttribute("userid");
        if(issueweb!=null&&searchtime!=null) {
 //           HttpSession session = request.getSession();

            String [] tempurl=issueweb.split("/");
            String owner=tempurl[tempurl.length-2];
            String repo=tempurl[tempurl.length-1];
            session.setAttribute("owner",owner);
            session.setAttribute("repo",repo);

            session.setAttribute("url", issueweb);
            session.setAttribute("time", searchtime);
            Database da=new DatabaseImpl();
            da.connSQL();
            da.insertHistoryView(Integer.parseInt(userid), issueweb, searchtime, viewtime);
            da.deconnSQL();
        }
        return "chooseType";
 //       return "details";
    }

    @RequestMapping("/details")//年度模块
    @ResponseBody
    public ArrayList<ArrayList<String>> yeartop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        ArrayList<ArrayList<String>> res=new ArrayList<ArrayList<String>>();
        try {
            HttpSession session=request.getSession();
            String time =(String)session.getAttribute("time");//搜索的时间
            int userid=(Integer)session.getAttribute("userid");
            String url=(String)session.getAttribute("url");
            DealComment DC=new DealCommentImpl() ;
            if(url.equals("DERBY") || url.equals("DROOLS") || url.equals("GROOVY") || url.equals("PIG") ||
                    url.equals("MNG") || url.equals("ISPN") || url.equals("JBSEAM") ){
                res=DC.getLYdata(url);
            }else {
                res = DC.getHisYear(userid, url, time);
            }
//            session.setAttribute("yeartop",res); //年度top5
/*            for(int i=0;i<res.size();i++){
                for(int j=0;j<res.get(0).size();j++) {
                    System.out.println(i);
                    System.out.println(res.get(i).get(j));
                }
            }*/
        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @RequestMapping("/details_month")//月度模块
    @ResponseBody
    public ArrayList<ArrayList<String>> monthtop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        ArrayList<ArrayList<String>> res=new ArrayList<ArrayList<String>>();
        try {
            HttpSession session=request.getSession();
            String time =(String)session.getAttribute("time");//搜索的时间
            int userid=Integer.parseInt((String)session.getAttribute("userid"));
            String url=(String)session.getAttribute("url");
            DealComment DC=new DealCommentImpl();
            if(url.equals("DERBY") || url.equals("DROOLS") || url.equals("GROOVY") || url.equals("PIG") ||
                    url.equals("MNG") || url.equals("ISPN") || url.equals("JBSEAM") ){
                res=DC.getLMdata(url);
  //              System.out.println(res);
            }else {
                res = DC.getHisMonth(userid, url, time);
            }
//            session.setAttribute("monthtop",res); //年度top5
        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @RequestMapping("/month_chart")//月度图
    @ResponseBody
    public ArrayList<Object> monthchart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        double[][] res=new double[2][27];
        ArrayList<Object> list=new ArrayList<Object>();
        try{
            HttpSession session=request.getSession();
            String webContent =(String)session.getAttribute("url");//搜索的数据库名
            String name="";
            if (webContent.contains("DERBY")) {
//                    session.setAttribute("name", "DERBY");       //名字在session里2
                name="DERBY";
            }
            if (webContent.contains("DROOLS")) {
//                    session.setAttribute("name", "DROOLS");
                name="DROOLS";
            }
            if (webContent.contains("GROOVY")) {
//                    session.setAttribute("name", "GROOVY");
                name="GROOVY";
            }
            if (webContent.contains("ISPN")) {
                //                   session.setAttribute("name", "ISPN");
                name="ISPN";
            }
            if (webContent.contains("MNG")) {
                //                   session.setAttribute("name", "MNG");
                name="MNG";
            }
            if (webContent.contains("PIG")) {
//                    session.setAttribute("name", "PIG");
                name="PIG";
            }
            if (webContent.contains("JBSEAM")) {
                //                   session.setAttribute("name", "JBSEAM");//传递数据库名
                name="JBSEAM";
            }
            if (webContent.contains("https://github.com")) {
                name="github";
            }
            DealComment DC=new DealCommentImpl();
            if(name.equals("github")){
                list=DC.getGChange(webContent);
//                System.out.println(res[0][0]);
            }else if(name.equals("DERBY") || name.equals("DROOLS") || name.equals("GROOVY") || name.equals("PIG") ||
                    name.equals("MNG") || name.equals("ISPN") || name.equals("JBSEAM") ){
                list=DC.getLChange(name);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;

    }

    @RequestMapping("/issueComments")//评论页面
    public String turn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        try {
            HttpSession session=request.getSession();
            if(session.getAttribute("userid").toString()==null){
                return "index";
            }
            String name =(String)session.getAttribute("url");//搜索的数据库名
            String issueweb = request.getParameter("issueweb");//传递的参数
            if(name.contains("github")) {
                issueweb = "https://github.com/" + issueweb;
                session.setAttribute("issueWeb", issueweb);
            }else if(name.equals("DERBY") || name.equals("DROOLS") || name.equals("GROOVY") || name.equals("PIG") ||
                    name.equals("MNG") || name.equals("ISPN") || name.equals("JBSEAM") ){
                int startIndex=issueweb.lastIndexOf("/");
                issueweb=issueweb.substring(startIndex+1);
                session.setAttribute("issueWeb", issueweb);

            }
//        System.out.println(issueweb);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "issueComments";
    }

    @RequestMapping("/getComments")//评论模块
    @ResponseBody
    public ArrayList<ArrayList<String>> showComments(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        String issueWeb="";
        ArrayList<ArrayList<String>> res=new ArrayList<ArrayList<String>>();
        try{
            HttpSession session=request.getSession();
            String name =(String)session.getAttribute("url");//搜索的数据库名
            DealComment DC=new DealCommentImpl();
            Database da=new DatabaseImpl();
            issueWeb=(String)session.getAttribute("issueWeb");
            if(name.contains("github")) {
                res = DC.getGIn_order(issueWeb);
            }else if(name.equals("DERBY") || name.equals("DROOLS") || name.equals("GROOVY") || name.equals("PIG") ||
                    name.equals("MNG") || name.equals("ISPN") || name.equals("JBSEAM") ){
                int issueNo=Integer.parseInt(issueWeb);
//                System.out.println(issueNo);
                res = da.getLIn_order(name, issueNo);
//                System.out.println(res.get(0).get(2));
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @RequestMapping("/yeartop")//年度页面
    public String yeartop(HttpServletRequest request){
        HttpSession session=request.getSession();
        if(session.getAttribute("userid").toString()==null){
            return "index";
        }
        return "yeartop";
    }

    @RequestMapping("/monthtop")//月度页面
    public String monthtop(HttpServletRequest request){
        HttpSession session=request.getSession();
        if(session.getAttribute("userid").toString()==null){
            return "index";
        }

        return "monthtop";
    }

    @RequestMapping("/monthchart")//月度图页面
    public String monthchart(HttpServletRequest request){
        HttpSession session=request.getSession();
        if(session.getAttribute("userid").toString()==null){
            return "index";
        }

        return "monthchart";
    }

    @RequestMapping("/turnPage")//跳转
    public String turnPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        try {
            HttpSession session=request.getSession();
            if(session.getAttribute("userid").toString()==null){
                return "index";
            }

            String name =(String)session.getAttribute("url");//搜索的数据库名
            String issueNo = request.getParameter("issueNo");//传递的参数
            System.out.println(name);
            if(name.contains("github")) {
                issueNo = name+"/issues/" + issueNo;
                System.out.println(issueNo);
                session.setAttribute("issueWeb", issueNo);
            }else if(name.equals("DERBY") || name.equals("DROOLS") || name.equals("GROOVY") || name.equals("PIG") ||
                    name.equals("MNG") || name.equals("ISPN") || name.equals("JBSEAM") ){
                session.setAttribute("issueWeb", issueNo);

            }
//        System.out.println(issueweb);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "issueComments";
    }

    @RequestMapping("/choose")//选择issue, commit, contribute
    public String choose(HttpServletRequest request){
        HttpSession session=request.getSession();
        if(session.getAttribute("userid").toString()==null){
            return "index";
        }

        return "chooseType";
    }

    @RequestMapping("/updateComment")//更新monthchart那里的用户评论
    public String updateComment(HttpServletRequest request){
        try {
            String comment = request.getParameter("comment");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String nowtime = df.format(new Date());// new Date()为获取当前系统时间
            HttpSession session = request.getSession();
            if(session.getAttribute("userid").toString()==null){
                return "index";
            }
            int userid = (Integer) session.getAttribute("userid");//搜索的数据库名

            String url = (String) session.getAttribute("url");
            String time = (String) session.getAttribute("time");
            Database da = new DatabaseImpl();
            comment=comment.replaceAll("'", "\\\\\'");
            comment=comment.replaceAll("\"", "\\\\\"");
            da.connSQL();
            da.updateHistoryComment(userid, url, time, comment, nowtime);
            da.deconnSQL();
        }catch(Exception e){
            e.printStackTrace();
        }
        return "monthchart";
    }

    @RequestMapping("/historyComment")
    @ResponseBody
    public ArrayList<String> getComment(HttpServletRequest request){
        ArrayList<String> res=new ArrayList<String>();
        try{
            HttpSession session = request.getSession();
            int userid = (Integer) session.getAttribute("userid");//搜索的数据库名
            String url = (String) session.getAttribute("url");
            String time = (String) session.getAttribute("time");
            Database da=new DatabaseImpl();
            ArrayList<String> comment=da.getHistoryComment(userid, url, time);
            res=comment;
        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @RequestMapping("/Recent")
    @ResponseBody
    public ArrayList<ArrayList<String>> recent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        ArrayList<ArrayList<String>> res=new ArrayList<ArrayList<String>>();
        try{
            HttpSession session=request.getSession();
            int userid=(Integer)session.getAttribute("userid");//userID在session里
            Database da=new DatabaseImpl();
            res=da.getRecent(userid);
/*            for(int i=0;i<3;i++){
                System.out.println(res.get(i).get(2));
            }*/
        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @RequestMapping("/recenttop")
    public String recenttop(HttpServletRequest request){
        String fromtime=request.getParameter("fromtime");//开始时间
        String totime=request.getParameter("totime");//结束时间
        if((fromtime==null)||(totime==null)){//如果未设置开始和结束时间，则规定开始和结束时间
            fromtime="2017-09-29";
            totime="2018-10-10";
        }
        HttpSession session = request.getSession();
        session.setAttribute("fromtime", fromtime);
        session.setAttribute("totime", totime);
        return "recenttop";
    }

    @RequestMapping("/recentdata")
    @ResponseBody
    public ArrayList<Object> getBodong(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        ArrayList<Object> res=new ArrayList<>();
        try {
            HttpSession session = request.getSession();
            String userid = (String) session.getAttribute("userid");//搜索的数据库名
            String url = (String) session.getAttribute("url");
            String time = (String) session.getAttribute("time");
            String fromtime=(String)session.getAttribute("fromtime");
            System.out.println(url);
            String totime=(String)session.getAttribute("totime");
            if((fromtime==null)||(totime==null)){//如果未设置开始和结束时间，则规定开始和结束时间
                fromtime="2017-09-29";
                totime="2018-10-10";
            }
            DealComment dc=new DealCommentImpl() ;
            if(url.equals("DERBY") || url.equals("DROOLS") || url.equals("GROOVY") || url.equals("PIG") ||
                    url.equals("MNG") || url.equals("ISPN") || url.equals("JBSEAM") ){
                ArrayList<Object> fangcha=dc.getVarL(fromtime, totime, url);
                ArrayList<Object> bianhua=dc.getSelectChangeL(fromtime, totime, url);
                res.add(fangcha);
                res.add(bianhua);
            }else {
                ArrayList<Object> fangcha=dc.getVar(fromtime, totime, url);
                ArrayList<Object> bianhua=dc.getSelectChange(fromtime, totime, url);
                res.add(fangcha);
                res.add(bianhua);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @RequestMapping("/recentview")
    @ResponseBody
    public ArrayList<ArrayList<String>> hisView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        ArrayList<ArrayList<String>> res=new ArrayList<ArrayList<String>>();
        try{
            HttpSession session=request.getSession();
            String userid=(String)session.getAttribute("userid");//userID在session里
            Database da=new DatabaseImpl();
            res=da.getRecentView(Integer.parseInt(userid));
/*            for(int i=0;i<3;i++){
                System.out.println(res.get(i).get(2));
            }*/
        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }

}
