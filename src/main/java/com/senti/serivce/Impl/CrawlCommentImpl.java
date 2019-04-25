package com.senti.serivce.Impl;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.plugin.net.OkHttpRequester;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import com.senti.serivce.Database;
import jdk.nashorn.internal.objects.annotations.Constructor;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Crawling comments from github
 * @author zhuhao
 *
 */

public class CrawlCommentImpl extends BreadthCrawler {

    static ArrayList<ArrayList<String>> res=new ArrayList<ArrayList<String>>();
    Database dao=new DatabaseImpl();
    SentiStrengthAnalyse SSA=new SentiStrengthAnalyse();
    // 自定义的请求插件
    // 可以自定义User-Agent和Cookie
    public static class MyRequester extends OkHttpRequester {

        String userAgent = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36";
        String cookie = "_ga=GA1.2.903118938.1524621612; _octo=GH1.1.1003503721.1524621613; user_session=k-Ve9zhdaoOQPV5wGlrll8j-e8xTeLFUadBfxmFt_fUMcAcI; __Host-user_session_same_site=k-Ve9zhdaoOQPV5wGlrll8j-e8xTeLFUadBfxmFt_fUMcAcI; logged_in=yes; dotcom_user=Takumiyo; _device_id=775e90697cd9a927f1d88b8748c9f2a0; has_recent_activity=1; tz=Asia%2FShanghai; _gat=1; _gh_sess=d3NzZGE5a1d5bDFMekJQUVQvNGJEUnF0bVBTUVVjQUM1UnY5RHhkU2ttSHVqYVR6YUNVOEROeExTWEdDS2gwelVhNVdVcXVrWFY3Zks5S2xEaUVaVnBNTmdFVmRiWnFNMy9LNUkvbDI4L1FKYnBSeXVIdUxrVStOd1lIMVE4SVlhazZjbHJ1VGExUUJoUnJzdFp1cTVrYlg3TldjSWViUnhMeHd4dVg5NHhyY2JaM2N0OWkzdUZ3RVJ2Ty9LdEtCTytuWGErQWtyUjhXYTdwMmo2SzIzeEtLWm1DeHpyeHZ0MmdUTmpnK0c1cHRmcHhnRGt6cUQ0cGk3bVZVUk1kNjF1RWxCS0ZJS01ocDdwK2tIVjBzZ05yUXFvbFdVWU1hYytwQlVFSEdBdnJVWUthNGs2a2ZXK2JoL2orWmJ0NloxS0lTb0UvTFZGVXpzcEszTHpndzNnPT0tLWRna1VaRkxIeDFvVUJiQ1NkelNKVXc9PQ%3D%3D--471c6e2940bbce203f6e239bd195a9fe3edf4b29";

        // 每次发送请求前都会执行这个方法来构建请求
        @Override
        public Request.Builder createRequestBuilder(CrawlDatum crawlDatum) {
            // 这里使用的是OkHttp中的Request.Builder
            // 可以参考OkHttp的文档来修改请求头
//            System.out.println("request with cookie: " + cookie);
            return super.createRequestBuilder(crawlDatum)
                    .header("User-Agent", userAgent)
                    .header("Cookie", cookie);
        }

    }

    //这里用于添加种子并添加限制

    public CrawlCommentImpl(String crawlPath, String weburl) {
        super(crawlPath, true);

        // 设置请求插件
        setRequester(new MyRequester());

        // 爬取github
        for(int i=1;i<=10;i++) {
            addSeed(weburl+"/issues?page="+i+"&q=is%3Aissue+is%3Aopen");
            addSeed(weburl+"/issues?page="+i+"&q=is%3Aissue+is%3Aclosed");
        }
        addRegex(weburl+"/issues/[0-9][0-9]*");
    }

    //爬取符合规则的网页
    public void visit(Page page, CrawlDatums crawlDatums) {
        try {
            String url = page.url();//issue的网址
            String weburl=url.substring(0, url.indexOf("issues")-1);
            if(page.matchUrl(weburl+"/issues/[0-9][0-9]*")){
//    		System.out.println(page.selectText("h1.gh-header-title"));//issue名称
//    		System.out.println(page.selectText("div.js-discussion.js-socket-channel"));//issue里的评论
                String issueName=page.selectText("h1.gh-header-title");
                String comments=page.selectText("div.js-discussion.js-socket-channel");
                res=dealData(url, issueName, comments);
/*    		for(int i=0;i<res.size();i++){
            	for(int j=0;j<5;j++)
            	System.out.println(res.get(i).get(j));
            }*/

            }
        }catch(Exception e) {

        }
    }

    //将爬取下来的信息处理，返回的是一个issue下的所有信息(编号,url,issueName,人名,时间,评论)
    public ArrayList<ArrayList<String>> dealData(String url, String issueName, String comment){
        ArrayList<ArrayList<String>> result= new ArrayList<ArrayList<String>>();
        try{
            ArrayList<String> temp = new ArrayList<String>();
            BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"));
            writer.write(comment);
            writer.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("data.txt"), "UTF-8"));
            String lines=null;
            while ((lines = br.readLine()) != null) {
                if(lines.contains("Originally posted by")||lines.contains("Copy link Quote reply")){
                    temp.add(lines);
                }
            }
            br.close();
            for(int i=0;i<temp.size();i++){
                String line=temp.get(i);
//            	System.out.println(line);
                String allInfor;
                if(line.contains("Originally posted by")){
                    int startIndex=line.indexOf("Create issue");
                    int endIndex=line.length();
                    if(line.contains("This comment has been minimized")){
                        endIndex=line.indexOf("This comment has been minimized");
                    }
                    allInfor=line.substring(startIndex+12, endIndex);
                }else{
                    int startIndex=line.indexOf("Copy link Quote reply");
                    int endIndex=line.length();
                    if(line.contains("This comment has been minimized")){
                        endIndex=line.indexOf("This comment has been minimized");
                    }
                    allInfor=line.substring(startIndex+21, endIndex);
                }
                allInfor=allInfor.replaceAll("Pick your reaction 👍 👎 😄 🎉 😕 ❤️ 🚀 👀", "");
                allInfor=allInfor.replaceAll("Pick your reaction", "");
                allInfor=allInfor.replaceAll("👍 ", "");
                allInfor=allInfor.replaceAll("👎 ", "");
                allInfor=allInfor.replaceAll("😄 ", "");
                allInfor=allInfor.replaceAll("🎉 ", "");
                allInfor=allInfor.replaceAll("😕 ", "");
                allInfor=allInfor.replaceAll("❤️ ", "");
                allInfor=allInfor.replaceAll("🚀 ", "");
                allInfor=allInfor.replaceAll("👀", "");
                allInfor=allInfor.replaceAll("'", " ");
                int comIndex=allInfor.indexOf("commented");
                int commaIndex=allInfor.indexOf(",");
                int[] score=SSA.getScore(allInfor.substring(commaIndex+6));//情绪得分
/*            	ArrayList<String> singleInfor=new ArrayList<String>();//将人名，时间，评论分开
            	singleInfor.add(url);
            	singleInfor.add(issueName);
            	singleInfor.add(allInfor.substring(0,comIndex));//人名
            	singleInfor.add(allInfor.substring(comIndex+9,commaIndex+6));//时间
            	singleInfor.add(allInfor.substring(commaIndex+6));//评论
            	result.add(singleInfor);*/
                System.out.println(url);
                System.out.println(issueName);
                System.out.println(allInfor.substring(0,comIndex));
                System.out.println(allInfor.substring(comIndex+9,commaIndex+6));//时间
                System.out.println(allInfor.substring(commaIndex+6));
                System.out.println(url.substring(0, url.indexOf("issues")-1));
                System.out.println(i);
                String sql="insert into githubtable values ('"+url+"', '"+issueName+"', '"+allInfor.substring(0,comIndex)+"', '"+allInfor.substring(comIndex+9,commaIndex+6)+"', '"+allInfor.substring(commaIndex+6)+"', '"+url.substring(0, url.indexOf("issues")-1)+"', '"+i+"', '"+score[0]+"', '"+score[1]+"');";
                dao.update(sql);
            }
            String sql2="insert into gitissue values ('"+url.substring(0, url.indexOf("issues")-1)+"', '"+url+"');";
                      dao.update(sql2);
        }catch(Exception e){
            System.out.println("处理Github数据失败");
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        CrawlCommentImpl crawler = new CrawlCommentImpl("crawl","https://github.com/TheAlgorithms/C");
        crawler.start(2);

    }
}
