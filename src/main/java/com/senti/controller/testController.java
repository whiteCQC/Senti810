package com.senti.controller;

import com.senti.serivce.Database;
import com.senti.serivce.DealComment;
import com.senti.serivce.Impl.CrawlCommentImpl;
import com.senti.serivce.Impl.DatabaseImpl;
import com.senti.serivce.Impl.DealCommentImpl;
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
public class testController {
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
        return "mylogin";
    }

    @RequestMapping("/login")//登录界面
    public String login(){
        return "mylogin";
    }

    @RequestMapping("/input")//主页搜索界面
    public String input(HttpServletRequest request){
        HttpSession session=request.getSession();
        String userid=(String)session.getAttribute("userid");
        if(userid==null){
            return "mylogin";
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
            String userid=(String)session.getAttribute("userid");//userID在session里
            if(userid==null){
                return "mylogin";
            }
            Database da= new DatabaseImpl();
            String url="";
            if(webContent!=null) {
                if (webContent.contains("DERBY")) {
//                    session.setAttribute("name", "DERBY");       //名字在session里2
                    url="DERBY";
                }
                if (webContent.contains("DROOLS")) {
//                    session.setAttribute("name", "DROOLS");
                    url="DROOLS";
                }
                if (webContent.contains("GROOVY")) {
//                    session.setAttribute("name", "GROOVY");
                    url="GROOVY";
                }
                if (webContent.contains("ISPN")) {
 //                   session.setAttribute("name", "ISPN");
                    url="ISPN";
                }
                if (webContent.contains("MNG")) {
 //                   session.setAttribute("name", "MNG");
                    url="MNG";
                }
                if (webContent.contains("PIG")) {
//                    session.setAttribute("name", "PIG");
                    url="PIG";
                }
                if (webContent.contains("JBSEAM")) {
 //                   session.setAttribute("name", "JBSEAM");//传递数据库名
                    url="JBSEAM";
                }
                if (webContent.contains("https://github.com")) {
                    url=webContent;
                    /*爬取数据*/
                    if (da.getSameTypeIssue(webContent).size() == 0) {//如果数据库中没有记录则需要爬取
                        CrawlCommentImpl crawler = new CrawlCommentImpl("crawl", webContent);
                        crawler.start(2);
                    }
//                    session.setAttribute("url", url);
//                    session.setAttribute("name","github");

                    /*计算出年度top，月度top，monthchart*/
                    DealComment DC=new DealCommentImpl();
                    ArrayList<ArrayList<String>> yeardata=new ArrayList<ArrayList<String>>();
                    ArrayList<ArrayList<String>> monthdata=new ArrayList<ArrayList<String>>();
                    yeardata=DC.getGYearTop(url);//年度数据
                    monthdata=DC.getGMonthTop(url);//月度数据
                    da.connSQL();
                    for(int i=0;i<3;i++){
                        for(int j=0;j<10;j++){
                            da.updateYear(Integer.parseInt(userid), url, time, yeardata.get(i).get(j), i*10+j+1);//更新历史年度top表

                         //   System.out.println("ok");
                        }
                        for(int m=0;m<24;m++){
                            da.updateMonth(Integer.parseInt(userid), url, time, monthdata.get(i).get(m), i*24+m+1);//更新历史月度top表
                        //     System.out.println("ok2");
                        }
                    }
                    da.deconnSQL();
                }
                da.connSQL();
                da.updatehistory(Integer.parseInt(userid), url, time);//更新历史搜索表
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
            String userid=(String)session.getAttribute("userid");//userID在session里
            System.out.println(userid);
            Database da=new DatabaseImpl();
            res=da.getHistory(Integer.parseInt(userid));
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
        String userid=(String)session.getAttribute("userid");
        if(userid==null){
            return "mylogin";
        }
        String issueweb=request.getParameter("issueweb");//url
        String searchtime=request.getParameter("searchtime");//time
        if(issueweb!=null&&searchtime!=null) {
 //           HttpSession session = request.getSession();
            session.setAttribute("url", issueweb);
            session.setAttribute("time", searchtime);
        }
        return "details";
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
            int userid=Integer.parseInt((String)session.getAttribute("userid"));
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
                res=((DealCommentImpl) DC).getLMdata(url);
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
    public double[][] monthchart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        double[][] res=new double[2][27];
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
                res=DC.getGChange(webContent);
//                System.out.println(res[0][0]);
            }else if(name.equals("DERBY") || name.equals("DROOLS") || name.equals("GROOVY") || name.equals("PIG") ||
                    name.equals("MNG") || name.equals("ISPN") || name.equals("JBSEAM") ){
                res=DC.getLChange(name);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return res;

    }

    @RequestMapping("/issueComments")//评论页面
    public String turn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        try {
            HttpSession session=request.getSession();
            String userid=(String)session.getAttribute("userid");
            if(userid==null){
                return "mylogin";
            }
            String name =(String)session.getAttribute("url");//搜索的数据库名
            String issueweb = request.getParameter("issueweb");//传递的参数
            if(name.contains("github")) {
                issueweb = "https://github.com/" + issueweb;
                session.setAttribute("issueWeb", issueweb);
            }else if(name.equals("DERBY") || name.equals("DROOLS") || name.equals("GROOVY") || name.equals("PIG") ||
                    name.equals("MNG") || name.equals("ISPN") || name.equals("JBSEAM") ){
                int startIndex=issueweb.indexOf(name);
                issueweb=issueweb.substring(startIndex);
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
                int issueNo=Integer.parseInt(issueWeb.substring(issueWeb.indexOf("-")+1));
//                System.out.println(issueNo);
                res = da.getLIn_order(name, issueNo);
//                System.out.println(res.get(0).get(2));
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }
}
