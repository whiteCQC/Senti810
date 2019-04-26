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

    @RequestMapping("/input")
    public String input(){
        return "SearchView";
    }

    @RequestMapping("/history")
    public String test(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        //将输入的网址处理(输入的格式为https://github.com/TheAlgorithms/Java)
        try {
            String webContent = request.getParameter("content");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String time= df.format(new Date());// new Date()为获取当前系统时间
            ArrayList<String> rs = new ArrayList<String>();
            HttpSession session=request.getSession();
            session.setAttribute("time", time);                   //时间在session里1
            if(webContent!=null) {
                if (webContent.contains("DERBY")) {
                    DealComment DC = new DealCommentImpl();
// 情绪值低于-3                   rs = DC.getLow("DERBY");
                    session.setAttribute("name", "DERBY");       //名字在session里2
                }
                if (webContent.contains("DROOLS")) {
                    DealComment DC = new DealCommentImpl();
// 情绪值低于-3                   rs = DC.getLow("DROOLS");
                    session.setAttribute("name", "DROOLS");
                }
                if (webContent.contains("GROOVY")) {
                    DealComment DC = new DealCommentImpl();
//情绪值低于-3                     rs = DC.getLow("GROOVY");
                    session.setAttribute("name", "GROOVY");
                }
                if (webContent.contains("ISPN")) {
                    DealComment DC = new DealCommentImpl();
//情绪值低于-3                     rs = DC.getLow("ISPN");
                    session.setAttribute("name", "ISPN");
                }
                if (webContent.contains("MNG")) {
                    DealComment DC = new DealCommentImpl();
//情绪值低于-3                     rs = DC.getLow("MNG");
                    session.setAttribute("name", "MNG");
                }
                if (webContent.contains("PIG")) {
                    DealComment DC = new DealCommentImpl();
//情绪值低于-3                     rs = DC.getLow("PIG");
                    session.setAttribute("name", "PIG");
                }
                if (webContent.contains("JBSEAM")) {
                    DealComment DC = new DealCommentImpl();
//情绪值低于-3                     rs = DC.getLow("JBSEAM");
                    session.setAttribute("name", "JBSEAM");//传递数据库名
                }
                if (webContent.contains("https://github.com")) {
                    Database dat = new DatabaseImpl();
                    if (dat.getSameTypeIssue(webContent).size() == 0) {//如果数据库中没有记录则需要爬取
                        CrawlCommentImpl crawler = new CrawlCommentImpl("crawl", webContent);
                        crawler.start(2);
                    }
                    DealComment DC = new DealCommentImpl();
//情绪值低于-2.5                    rs = DC.getGitLow(webContent);
                    session.setAttribute("name", "github");//传递数据库名
                    session.setAttribute("url", webContent);//查找的网址
                }
//低情绪值的url                session.setAttribute("issueNo", rs);//传递情绪值较低的issueNo
//			for(int i=0;i<rs.size();i++) {
//				System.out.print(rs.get(i));
//			}
//            request.getRequestDispatcher("History.jsp").forward(request,response);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return "details";
    }

    @RequestMapping("/details")
    @ResponseBody
    public ArrayList<ArrayList<String>> yeartop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        ArrayList<ArrayList<String>> res=new ArrayList<ArrayList<String>>();
        try {
            HttpSession session=request.getSession();
            String time =(String)session.getAttribute("time");//搜索的时间
            String name =(String)session.getAttribute("name");//搜索的数据库名
            DealComment DC=new DealCommentImpl();
            if(name.equals("github")){
                String url=(String)session.getAttribute("url");//输入的url（格式如https://github.com/TheAlgorithms/C）
                res=DC.getGYearTop(url);//获取GitHub的年度top5

            }else if(name.equals("DERBY") || name.equals("DROOLS") || name.equals("GROOVY") || name.equals("PIG") ||
                    name.equals("MNG") || name.equals("ISPN") || name.equals("JBSEAM") ){
                res=DC.getLYdata(name);
            }
            session.setAttribute("yeartop",res); //年度top5
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

    @RequestMapping("/details_month")
    @ResponseBody
    public ArrayList<ArrayList<String>> monthtop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        ArrayList<ArrayList<String>> res=new ArrayList<ArrayList<String>>();
        try {
            HttpSession session=request.getSession();
            String time =(String)session.getAttribute("time");//搜索的时间
            String name =(String)session.getAttribute("name");//搜索的数据库名
            DealComment DC=new DealCommentImpl();
            if(name.equals("github")){
                String url=(String)session.getAttribute("url");
                res=DC.getGMonthTop(url);
                System.out.println(res.get(0).get(0));
            }else if(name.equals("DERBY") || name.equals("DROOLS") || name.equals("GROOVY") || name.equals("PIG") ||
                    name.equals("MNG") || name.equals("ISPN") || name.equals("JBSEAM") ){
                res=((DealCommentImpl) DC).getLMdata(name);
            }
            session.setAttribute("monthtop",res); //年度top5
        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @RequestMapping("/month_chart")
    @ResponseBody
    public double[][] monthchart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        double[][] res=new double[2][27];
        try{
            HttpSession session=request.getSession();
            String time =(String)session.getAttribute("time");//搜索的时间
            String name =(String)session.getAttribute("name");//搜索的数据库名
            DealComment DC=new DealCommentImpl();
            if(name.equals("github")){
                String url=(String)session.getAttribute("url");
                res=DC.getGChange(url);
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

    @RequestMapping("/issueComments")
    public String turn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        try {
            HttpSession session=request.getSession();
            String time =(String)session.getAttribute("time");//搜索的时间
            String name =(String)session.getAttribute("name");//搜索的数据库名
            String issueweb = request.getParameter("issueweb");//传递的参数
            if(name.equals("github")) {
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

    @RequestMapping("/getComments")
    @ResponseBody
    public ArrayList<ArrayList<String>> showComments(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        String issueWeb="";
        ArrayList<ArrayList<String>> res=new ArrayList<ArrayList<String>>();
        try{
            HttpSession session=request.getSession();
            String time =(String)session.getAttribute("time");//搜索的时间
            String name =(String)session.getAttribute("name");//搜索的数据库名
            DealComment DC=new DealCommentImpl();
            Database da=new DatabaseImpl();
            issueWeb=(String)session.getAttribute("issueWeb");
            if(name.equals("github")) {
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
