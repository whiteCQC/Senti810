package com.senti.serivce.Impl;

import java.util.ArrayList;

import com.senti.serivce.Database;
import com.senti.serivce.DealComment;
import org.springframework.stereotype.Service;

@Service
public class DealCommentImpl implements DealComment{
    Database dao=new DatabaseImpl();
    SentiStrengthAnalyse SSA=new SentiStrengthAnalyse();

    /*输入的网址为本地已下载的，则直接从数据库调取数据，无需重新爬取
     * 消极的低于-3的issue
     * */
    public ArrayList<String> getLow(String name){
        ArrayList<ArrayList<String>> Comment=new ArrayList<ArrayList<String>>();
        ArrayList<String> NegIssueNo= new ArrayList<String>();        //消极issue的Number
        try {
            if(name.equals("DERBY")) {
                for(int k=1;k<=7028;k++) {                            //Derby的issue数量
                    System.out.println(k);
                    Comment=dao.getDerbyComment(k);                   //每一个issue的评论
                    double negativeScore=0;                              //每一个issue对应的总的负面情绪值
                    if(Comment.size()!=0) {
                        for(int i=0;i<Comment.size();i++) {
                            int[] score= {Integer.parseInt(Comment.get(i).get(4)), Integer.parseInt(Comment.get(i).get(5))};
                            negativeScore+=score[1];
                        }
                        negativeScore=negativeScore/Comment.size();
                        System.out.println(negativeScore);
                    }
                    if(negativeScore<=-3) {
                        NegIssueNo.add(String.valueOf(k));            //issue情绪值低于-3时归为消极
                    }
                }
            }
            if(name.equals("DROOLS")) {
                for(int k=1;k<=100;k++) {
                    System.out.println(k);
                    Comment=dao.getDroolsComment(k);                   //每一个issue的评论
                    double negativeScore=0;                              //每一个issue对应的总的负面情绪值
                    if(Comment.size()!=0) {
                        for(int i=0;i<Comment.size();i++) {
                            int[] score={Integer.parseInt(Comment.get(i).get(4)), Integer.parseInt(Comment.get(i).get(5))};
                            negativeScore+=score[1];
                        }
                        negativeScore=negativeScore/Comment.size();
                    }
                    if(negativeScore<=-3) {
                        NegIssueNo.add(String.valueOf(k));            //issue情绪值低于-3时归为消极
                    }
                }
            }
            if(name.equals("GROOVY")) {
                for(int k=1;k<=100;k++) {
                    System.out.println(k);
                    Comment=dao.getGroovyComment(k);                   //每一个issue的评论
                    double negativeScore=0;                              //每一个issue对应的总的负面情绪值
                    if(Comment.size()!=0) {
                        for(int i=0;i<Comment.size();i++) {
                            int[] score={Integer.parseInt(Comment.get(i).get(4)), Integer.parseInt(Comment.get(i).get(5))};
                            negativeScore+=score[1];
                        }
                        negativeScore=negativeScore/Comment.size();
                    }
                    if(negativeScore<=-3) {
                        NegIssueNo.add(String.valueOf(k));            //issue情绪值低于-3时归为消极
                    }
                }
            }
            if(name.equals("ISPN")) {
                for(int k=1;k<=100;k++) {
                    System.out.println(k);
                    Comment=dao.getIspnComment(k);                   //每一个issue的评论
                    double negativeScore=0;                              //每一个issue对应的总的负面情绪值
                    if(Comment.size()!=0) {
                        for(int i=0;i<Comment.size();i++) {
                            int[] score={Integer.parseInt(Comment.get(i).get(4)), Integer.parseInt(Comment.get(i).get(5))};
                            negativeScore+=score[1];
                        }
                        negativeScore=negativeScore/Comment.size();
                    }
                    if(negativeScore<=-3) {
                        NegIssueNo.add(String.valueOf(k));            //issue情绪值低于-3时归为消极
                    }
                }
            }
            if(name.equals("MNG")) {
                for(int k=1;k<=100;k++) {
                    System.out.println(k);
                    Comment=dao.getMngComment(k);                   //每一个issue的评论
                    double negativeScore=0;                              //每一个issue对应的总的负面情绪值
                    if(Comment.size()!=0) {
                        for(int i=0;i<Comment.size();i++) {
                            int[] score={Integer.parseInt(Comment.get(i).get(4)), Integer.parseInt(Comment.get(i).get(5))};
                            negativeScore+=score[1];
                        }
                        negativeScore=negativeScore/Comment.size();
                    }
                    if(negativeScore<=-3) {
                        NegIssueNo.add(String.valueOf(k));            //issue情绪值低于-3时归为消极
                    }
                }
            }
            if(name.equals("PIG")) {
                for(int k=1;k<=100;k++) {
                    System.out.println(k);
                    Comment=dao.getPigComment(k);                   //每一个issue的评论
                    double negativeScore=0;                              //每一个issue对应的总的负面情绪值
                    if(Comment.size()!=0) {
                        for(int i=0;i<Comment.size();i++) {
                            int[] score={Integer.parseInt(Comment.get(i).get(4)), Integer.parseInt(Comment.get(i).get(5))};
                            negativeScore+=score[1];
                        }
                        negativeScore=negativeScore/Comment.size();
                    }
                    if(negativeScore<=-3) {
                        NegIssueNo.add(String.valueOf(k));            //issue情绪值低于-3时归为消极
                    }
                }
            }
            if(name.equals("JBSEAM")) {
                for(int k=1;k<=100;k++) {
                    System.out.println(k);
                    Comment=dao.getJbseamComment(k);                   //每一个issue的评论
                    double negativeScore=0;                              //每一个issue对应的总的负面情绪值
                    if(Comment.size()!=0) {
                        for(int i=0;i<Comment.size();i++) {
                            int[] score={Integer.parseInt(Comment.get(i).get(4)), Integer.parseInt(Comment.get(i).get(5))};
                            negativeScore+=score[1];
                        }
                        negativeScore=negativeScore/Comment.size();
                    }
                    if(negativeScore<=-3) {
                        NegIssueNo.add(String.valueOf(k));            //issue情绪值低于-3时归为消极
                    }
                }
            }
        }catch(Exception e) {
            System.out.println(name+"获取情绪值较低的评论失败");
        }
        return NegIssueNo;
    }

    /*相应的数据库和issueNo对应的情绪值随时间变化需要的数据
     * name对应名字，如Derby
     * */
    public ArrayList<ArrayList<String>> sentiChange(String name, int issueNo) {
        ArrayList<ArrayList<String>> result= new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> comment = new ArrayList<ArrayList<String>>();
        if(name.equals("DERBY")) {
            comment=dao.getDerbyComment(issueNo);
        }
        if(name.equals("DROOLS")) {
            comment=dao.getDroolsComment(issueNo);
        }
        if(name.equals("GROOVY")) {
            comment=dao.getGroovyComment(issueNo);
        }
        if(name.equals("ISPN")) {
            comment=dao.getIspnComment(issueNo);
        }
        if(name.equals("MNG")) {
            comment=dao.getMngComment(issueNo);
        }
        if(name.equals("PIG")) {
            comment=dao.getPigComment(issueNo);
        }
        if(name.equals("JBSEAM")) {
            comment=dao.getJbseamComment(issueNo);
        }
        for(int i=0;i<comment.size();i++) {
            ArrayList<String> temp=new ArrayList<String>();
            temp.add(comment.get(i).get(2));              //时间
            temp.add(comment.get(i).get(4));         //积极得分
            temp.add(comment.get(i).get(5));         //消极得分
            result.add(temp);
        }
        return result;
    }


    /*处理GitHub爬取的数据(此处的url格式如https://github.com/TheAlgorithms/Java)*/
    public ArrayList<String> getGitLow(String url){
        ArrayList<String> result= new ArrayList<String>();
        try {
            ArrayList<ArrayList<String>> issueWeb=dao.getSameTypeIssue(url);//通过输入的网址获取其下的issue网址
            if(issueWeb.size()!=0) {
                for(int i=0;i<issueWeb.size();i++) {
                    ArrayList<ArrayList<String>> comment=dao.getGithubComment(issueWeb.get(i).get(1));
                    double negativeScore=0;
                    if(comment.size()!=0) {
                        for(int j=0;j<comment.size();j++) {
                            int[] score= {Integer.parseInt(comment.get(j).get(7)),Integer.parseInt(comment.get(j).get(8))};
                            negativeScore+=score[1];
                        }
                        negativeScore=negativeScore/comment.size();
                        System.out.println(negativeScore);
                    }
                    if(negativeScore<=-2.5) {
                        result.add(String.valueOf(issueWeb.get(i).get(1)));            //issue情绪值低于-2.5时归为消极
                    }
                }
            }
        }catch(Exception e) {
            System.out.println("GitHub获取情绪值较低的评论失败");
        }
        return result;
    }


    /*github单个issue内的情绪变化(此处url为具体到某个页面，格式如https://github.com/TheAlgorithms/Java/issues/1*/
    public ArrayList<ArrayList<String>> sentiChangeGit(String url){
        ArrayList<ArrayList<String>> result= new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> data=dao.getGithubComment(url);
        for(int i=0;i<data.size();i++) {
            ArrayList<String> temp=new ArrayList<String>();
            temp.add(data.get(i).get(3));              //时间
            temp.add(data.get(i).get(7));         //积极得分
            temp.add(data.get(i).get(8));         //消极得分
            result.add(temp);
        }
        return result;
    }


    /*年度好坏top5(本地已下好的数据库)统一以2015起步到2018,该方法用于计算数据，不调用，数据存放在另一个方法中*/
    /*DERBY年份2004.9到 2019.1
     * drools 2008.3-2019.1
     * groovy 2003.10-2019-1
     * ispn  2009.3-2019.1
     * mng  2003.8-2019.1
     * pig  2007.10-2019.1
     * jbseam  2005.1-2018.8
     */
    public ArrayList<ArrayList<String>> getLYearTop(String name){
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> comment=new ArrayList<ArrayList<String>>();
        try {
            comment=dao.getAllComment(name);
            String qianzhui=null;
            double[] Asort15=new double[comment.size()];
            double[] Nsort15=new double[comment.size()];
            double[] Asort16=new double[comment.size()];
            double[] Nsort16=new double[comment.size()];
            double[] Asort17=new double[comment.size()];
            double[] Nsort17=new double[comment.size()];
            double[] Asort18=new double[comment.size()];
            double[] Nsort18=new double[comment.size()];
            String[] issuesweb=new String[comment.size()];
            for(int i=1;i<=comment.size();i++) {
                ArrayList<ArrayList<String>> singleIssue=new ArrayList<ArrayList<String>>();//单个issue
                if(name.equals("DERBY")) {
                    singleIssue=dao.getDerbyComment(i);
                    qianzhui="https://issues.apache.org/jira/browse/DERBY-";
                }else if(name.equals("DROOLS")) {
                    singleIssue=dao.getDroolsComment(i);
                    qianzhui="https://issues.jboss.org/browse/DROOLS-";
                }else if(name.equals("GROOVY")) {
                    singleIssue=dao.getGroovyComment(i);
                    qianzhui="https://issues.apache.org/jira/browse/GROOVY-";
                }else if(name.equals("ISPN")) {
                    singleIssue=dao.getIspnComment(i);
                    qianzhui="https://issues.jboss.org/browse/ISPN-";
                }else if(name.equals("MNG")) {
                    singleIssue=dao.getMngComment(i);
                    qianzhui="https://issues.apache.org/jira/browse/MNG-";
                }else if(name.equals("PIG")) {
                    singleIssue=dao.getPigComment(i);
                    qianzhui="https://issues.apache.org/jira/browse/PIG-";
                }else if(name.equals("JBSEAM")) {
                    singleIssue=dao.getJbseamComment(i);
                    qianzhui="https://issues.jboss.org/browse/JBSEAM-";
                }
                double activeScore15=0;//2015年度积极分均值
                double negativeScore15=0;//2015年度消极分均值
                int count15=0;//2015年的评论数
                double activeScore16=0;//2016年度积极分均值
                double negativeScore16=0;//2016年度消极分均值
                int count16=0;//2016年的评论数
                double activeScore17=0;//2017年度积极分均值
                double negativeScore17=0;//2017年度消极分均值
                int count17=0;//2017年的评论数
                double activeScore18=0;//2018年度积极分均值
                double negativeScore18=0;//2018年度消极分均值
                int count18=0;//2018年的评论数
                for(int j=0;j<singleIssue.size();j++) {
                    if(singleIssue.get(j).get(2).substring(0, 4).equals("2015")) {
                        count15++;
                        activeScore15+=Integer.parseInt(singleIssue.get(j).get(4));
                        negativeScore15+=Integer.parseInt(singleIssue.get(j).get(5));
                    }
                    if(singleIssue.get(j).get(2).substring(0, 4).equals("2016")) {
                        count16++;
                        activeScore16+=Integer.parseInt(singleIssue.get(j).get(4));
                        negativeScore16+=Integer.parseInt(singleIssue.get(j).get(5));
                    }
                    if(singleIssue.get(j).get(2).substring(0, 4).equals("2017")) {
                        count17++;
                        activeScore17+=Integer.parseInt(singleIssue.get(j).get(4));
                        negativeScore17+=Integer.parseInt(singleIssue.get(j).get(5));
                    }
                    if(singleIssue.get(j).get(2).substring(0, 4).equals("2018")) {
                        count18++;
                        activeScore18+=Integer.parseInt(singleIssue.get(j).get(4));
                        negativeScore18+=Integer.parseInt(singleIssue.get(j).get(5));
                    }
                }
                if(count15!=0) {
                    activeScore15=activeScore15/count15;
                    negativeScore15=negativeScore15/count15;
                }
                if(count16!=0) {
                    activeScore16=activeScore16/count16;
                    negativeScore16=negativeScore16/count16;
                }
                if(count17!=0) {
                    activeScore17=activeScore17/count17;
                    negativeScore17=negativeScore17/count17;
                }
                if(count18!=0) {
                    activeScore18=activeScore18/count18;
                    negativeScore18=negativeScore18/count18;
                }
                issuesweb[i-1]=qianzhui+i;//issue网址
                Asort15[i-1]=activeScore15;//15年积极均值
                Nsort15[i-1]=negativeScore15;//15年消极均值
                Asort16[i-1]=activeScore16;//16年积极均值
                Nsort16[i-1]=negativeScore16;//16年消极均值
                Asort17[i-1]=activeScore17;//17年积极均值
                Nsort17[i-1]=negativeScore17;//17年消极均值
                Asort18[i-1]=activeScore18;//18年积极均值
                Nsort18[i-1]=negativeScore18;//18年消极均值

            }
            String afterASort15[]=new String[issuesweb.length];
            String afterNSort15[]=new String[issuesweb.length];
            String afterASort16[]=new String[issuesweb.length];
            String afterNSort16[]=new String[issuesweb.length];
            String afterASort17[]=new String[issuesweb.length];
            String afterNSort17[]=new String[issuesweb.length];
            String afterASort18[]=new String[issuesweb.length];
            String afterNSort18[]=new String[issuesweb.length];
            for(int i=0;i<issuesweb.length;i++) {
                afterASort15[i]=issuesweb[i];
                afterNSort15[i]=issuesweb[i];
                afterASort16[i]=issuesweb[i];
                afterNSort16[i]=issuesweb[i];
                afterASort17[i]=issuesweb[i];
                afterNSort17[i]=issuesweb[i];
                afterASort18[i]=issuesweb[i];
                afterNSort18[i]=issuesweb[i];
            }
            afterASort15=bubbleSort(Asort15, afterASort15);
            afterNSort15=bubbleSort(Nsort15, afterNSort15);
            afterASort16=bubbleSort(Asort16, afterASort16);
            afterNSort16=bubbleSort(Nsort16, afterNSort16);
            afterASort17=bubbleSort(Asort17, afterASort17);
            afterNSort17=bubbleSort(Nsort17, afterNSort17);
            afterASort18=bubbleSort(Asort18, afterASort18);
            afterNSort18=bubbleSort(Nsort18, afterNSort18);
            ArrayList<String> temp15=new ArrayList<String>();
            ArrayList<String> temp16=new ArrayList<String>();
            ArrayList<String> temp17=new ArrayList<String>();
            ArrayList<String> temp18=new ArrayList<String>();
            temp15.add(afterASort15[0]);
            temp15.add(afterASort15[1]);
            temp15.add(afterASort15[2]);
            temp15.add(afterASort15[3]);
            temp15.add(afterASort15[4]);
            temp15.add(afterNSort15[afterNSort15.length-1]);
            temp15.add(afterNSort15[afterNSort15.length-2]);
            temp15.add(afterNSort15[afterNSort15.length-3]);
            temp15.add(afterNSort15[afterNSort15.length-4]);
            temp15.add(afterNSort15[afterNSort15.length-5]);
            temp16.add(afterASort16[0]);
            temp16.add(afterASort16[1]);
            temp16.add(afterASort16[2]);
            temp16.add(afterASort16[3]);
            temp16.add(afterASort16[4]);
            temp16.add(afterNSort16[afterNSort16.length-1]);
            temp16.add(afterNSort16[afterNSort16.length-2]);
            temp16.add(afterNSort16[afterNSort16.length-3]);
            temp16.add(afterNSort16[afterNSort16.length-4]);
            temp16.add(afterNSort16[afterNSort16.length-5]);
            temp17.add(afterASort17[0]);
            temp17.add(afterASort17[1]);
            temp17.add(afterASort17[2]);
            temp17.add(afterASort17[3]);
            temp17.add(afterASort17[4]);
            temp17.add(afterNSort17[afterNSort17.length-1]);
            temp17.add(afterNSort17[afterNSort17.length-2]);
            temp17.add(afterNSort17[afterNSort17.length-3]);
            temp17.add(afterNSort17[afterNSort17.length-4]);
            temp17.add(afterNSort17[afterNSort17.length-5]);
            temp18.add(afterASort18[0]);
            temp18.add(afterASort18[1]);
            temp18.add(afterASort18[2]);
            temp18.add(afterASort18[3]);
            temp18.add(afterASort18[4]);
            temp18.add(afterNSort18[afterNSort18.length-1]);
            temp18.add(afterNSort18[afterNSort18.length-2]);
            temp18.add(afterNSort18[afterNSort18.length-3]);
            temp18.add(afterNSort18[afterNSort18.length-4]);
            temp18.add(afterNSort18[afterNSort18.length-5]);
            result.add(temp15);
            result.add(temp16);
            result.add(temp17);
            result.add(temp18);
        }catch (Exception e) {
            System.out.println("数据库年度信息失败");
        }
        return result;
    }

    /*17,18,19年度top5*/
    public ArrayList<ArrayList<String>> getLYdata(String name){
        ArrayList<ArrayList<String>> res=new ArrayList<ArrayList<String>>();
        ArrayList<String> temp17=new ArrayList<String>();
        ArrayList<String> temp18=new ArrayList<String>();
        ArrayList<String> temp19=new ArrayList<String>();
        if(name.equals("JBSEAM")){
            temp17.add("https://issues.jboss.org/browse/JBSEAM-4945");
            temp17.add("https://issues.jboss.org/browse/JBSEAM-3247");
            temp17.add("https://issues.jboss.org/browse/JBSEAM-1");
            temp17.add("https://issues.jboss.org/browse/JBSEAM-2");
            temp17.add("https://issues.jboss.org/browse/JBSEAM-3");
            temp17.add("https://issues.jboss.org/browse/JBSEAM-4945");
            temp17.add("https://issues.jboss.org/browse/JBSEAM-3247");
            temp17.add("https://issues.jboss.org/browse/JBSEAM-1");
            temp17.add("https://issues.jboss.org/browse/JBSEAM-2");
            temp17.add("https://issues.jboss.org/browse/JBSEAM-3");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-5148");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-1");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-2");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-3");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-4945");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-5148");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-1");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-2");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-3");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-4945");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
        }else if(name.equals("DROOLS")){
            temp17.add("https://issues.jboss.org/browse/DROOLS-1765");
            temp17.add("https://issues.jboss.org/browse/DROOLS-329");
            temp17.add("https://issues.jboss.org/browse/DROOLS-662");
            temp17.add("https://issues.jboss.org/browse/DROOLS-738");
            temp17.add("https://issues.jboss.org/browse/DROOLS-1130");
            temp17.add("https://issues.jboss.org/browse/DROOLS-1943");
            temp17.add("https://issues.jboss.org/browse/DROOLS-1869");
            temp17.add("https://issues.jboss.org/browse/DROOLS-1631");
            temp17.add("https://issues.jboss.org/browse/DROOLS-1567");
            temp17.add("https://issues.jboss.org/browse/DROOLS-1543");
            temp18.add("https://issues.jboss.org/browse/DROOLS-2964");
            temp18.add("https://issues.jboss.org/browse/DROOLS-2319");
            temp18.add("https://issues.jboss.org/browse/DROOLS-650");
            temp18.add("https://issues.jboss.org/browse/DROOLS-783");
            temp18.add("https://issues.jboss.org/browse/DROOLS-1380");
            temp18.add("https://issues.jboss.org/browse/DROOLS-2755");
            temp18.add("https://issues.jboss.org/browse/DROOLS-3081");
            temp18.add("https://issues.jboss.org/browse/DROOLS-2967");
            temp18.add("https://issues.jboss.org/browse/DROOLS-2803");
            temp18.add("https://issues.jboss.org/browse/DROOLS-2688");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
        }else if(name.equals("GROOVY")){
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-7763");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-3493");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-7423");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-7679");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-8096");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-6655");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-8320");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-8250");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-8178");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-8146");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-4214");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-7202");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-8241");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-8329");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-7601");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-3942");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-8298");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-6864");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-4151");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-8329");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
        }else if(name.equals("MNG")){
            temp17.add("https://issues.apache.org/jira/browse/MNG-1378");
            temp17.add("https://issues.apache.org/jira/browse/MNG-2802");
            temp17.add("https://issues.apache.org/jira/browse/MNG-3328");
            temp17.add("https://issues.apache.org/jira/browse/MNG-5585");
            temp17.add("https://issues.apache.org/jira/browse/MNG-5894");
            temp17.add("https://issues.apache.org/jira/browse/MNG-3328");
            temp17.add("https://issues.apache.org/jira/browse/MNG-6320");
            temp17.add("https://issues.apache.org/jira/browse/MNG-6317");
            temp17.add("https://issues.apache.org/jira/browse/MNG-6297");
            temp17.add("https://issues.apache.org/jira/browse/MNG-6241");
            temp18.add("https://issues.apache.org/jira/browse/MNG-6159");
            temp18.add("https://issues.apache.org/jira/browse/MNG-5668");
            temp18.add("https://issues.apache.org/jira/browse/MNG-6169");
            temp18.add("https://issues.apache.org/jira/browse/MNG-6294");
            temp18.add("https://issues.apache.org/jira/browse/MNG-6300");
            temp18.add("https://issues.apache.org/jira/browse/MNG-4917");
            temp18.add("https://issues.apache.org/jira/browse/MNG-6506");
            temp18.add("https://issues.apache.org/jira/browse/MNG-6447");
            temp18.add("https://issues.apache.org/jira/browse/MNG-6437");
            temp18.add("https://issues.apache.org/jira/browse/MNG-6422");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
        }else if(name.equals("PIG")){
            temp17.add("https://issues.apache.org/jira/browse/PIG-3498");
            temp17.add("https://issues.apache.org/jira/browse/PIG-4449");
            temp17.add("https://issues.apache.org/jira/browse/PIG-5268");
            temp17.add("https://issues.apache.org/jira/browse/PIG-5294");
            temp17.add("https://issues.apache.org/jira/browse/PIG-4324");

            temp17.add("https://issues.apache.org/jira/browse/PIG-5260");
            temp17.add("https://issues.apache.org/jira/browse/PIG-5172");
            temp17.add("https://issues.apache.org/jira/browse/PIG-5086");
            temp17.add("https://issues.apache.org/jira/browse/PIG-5072");
            temp17.add("https://issues.apache.org/jira/browse/PIG-4631");

            temp18.add("https://issues.apache.org/jira/browse/PIG-4373");
            temp18.add("https://issues.apache.org/jira/browse/PIG-5320");
            temp18.add("https://issues.apache.org/jira/browse/PIG-5329");
            temp18.add("https://issues.apache.org/jira/browse/PIG-5348");
            temp18.add("https://issues.apache.org/jira/browse/PIG-5360");

            temp18.add("https://issues.apache.org/jira/browse/PIG-5348");
            temp18.add("https://issues.apache.org/jira/browse/PIG-4373");
            temp18.add("https://issues.apache.org/jira/browse/PIG-5253");
            temp18.add("https://issues.apache.org/jira/browse/PIG-5372");
            temp18.add("https://issues.apache.org/jira/browse/PIG-5369");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
        }else if(name.equals("DERBY")){
            temp17.add("https://issues.apache.org/jira/browse/DERBY-6933");
            temp17.add("https://issues.apache.org/jira/browse/DERBY-4842");
            temp17.add("https://issues.apache.org/jira/browse/DERBY-6937");
            temp17.add("https://issues.apache.org/jira/browse/DERBY-6936");
            temp17.add("https://issues.apache.org/jira/browse/DERBY-6904");

            temp17.add("https://issues.apache.org/jira/browse/DERBY-3219");
            temp17.add("https://issues.apache.org/jira/browse/DERBY-6766");
            temp17.add("https://issues.apache.org/jira/browse/DERBY-4842");
            temp17.add("https://issues.apache.org/jira/browse/DERBY-6944");
            temp17.add("https://issues.apache.org/jira/browse/DERBY-6979");

            temp18.add("https://issues.apache.org/jira/browse/DERBY-7018");
            temp18.add("https://issues.apache.org/jira/browse/DERBY-6996");
            temp18.add("https://issues.apache.org/jira/browse/DERBY-7017");
            temp18.add("https://issues.apache.org/jira/browse/DERBY-6991");
            temp18.add("https://issues.apache.org/jira/browse/DERBY-6983");

            temp18.add("https://issues.apache.org/jira/browse/DERBY-7025");
            temp18.add("https://issues.apache.org/jira/browse/DERBY-6993");
            temp18.add("https://issues.apache.org/jira/browse/DERBY-6983");
            temp18.add("https://issues.apache.org/jira/browse/DERBY-7003");
            temp18.add("https://issues.apache.org/jira/browse/DERBY-6985");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
        }else if(name.equals("ISPN")){
            temp17.add("https://issues.jboss.org/browse/ISPN-3849");
            temp17.add("https://issues.jboss.org/browse/ISPN-3988");
            temp17.add("https://issues.jboss.org/browse/ISPN-3992");
            temp17.add("https://issues.jboss.org/browse/ISPN-6827");
            temp17.add("https://issues.jboss.org/browse/ISPN-7024");

            temp17.add("https://issues.jboss.org/browse/ISPN-7863");
            temp17.add("https://issues.jboss.org/browse/ISPN-8519");
            temp17.add("https://issues.jboss.org/browse/ISPN-8298");
            temp17.add("https://issues.jboss.org/browse/ISPN-8263");
            temp17.add("https://issues.jboss.org/browse/ISPN-7537");

            temp18.add("https://issues.jboss.org/browse/ISPN-9588");
            temp18.add("https://issues.jboss.org/browse/ISPN-2442");
            temp18.add("https://issues.jboss.org/browse/ISPN-6924");
            temp18.add("https://issues.jboss.org/browse/ISPN-7977");
            temp18.add("https://issues.jboss.org/browse/ISPN-8173");

            temp18.add("https://issues.jboss.org/browse/ISPN-9809");
            temp18.add("https://issues.jboss.org/browse/ISPN-9542");
            temp18.add("https://issues.jboss.org/browse/ISPN-9501");
            temp18.add("https://issues.jboss.org/browse/ISPN-9410");
            temp18.add("https://issues.jboss.org/browse/ISPN-9173");

            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
        }
        res.add(temp17);
        res.add(temp18);
        res.add(temp19);
        return res;
    }

    /*月度好坏top1(本地已下好的数据库15年到19年)只用于计算数据，不调用*/
    public ArrayList<ArrayList<String>> getLMonthTop(String name){
        ArrayList<ArrayList<String>> result= new ArrayList<ArrayList<String>>();
        try {
            ArrayList<ArrayList<String>> issueWeb=dao.getAllComment(name);//通过输入的网址获取其下的issue网址
            String qianzhui="";
            if(issueWeb.size()!=0) {
                for(int y=18;y<=18;y++) {//年份
                    String[] issuesweb=new String[issueWeb.size()];
                    double[] Asort1=new double[issueWeb.size()];
                    double[] Asort2=new double[issueWeb.size()];
                    double[] Asort3=new double[issueWeb.size()];
                    double[] Asort4=new double[issueWeb.size()];
                    double[] Asort5=new double[issueWeb.size()];
                    double[] Asort6=new double[issueWeb.size()];
                    double[] Asort7=new double[issueWeb.size()];
                    double[] Asort8=new double[issueWeb.size()];
                    double[] Asort9=new double[issueWeb.size()];
                    double[] Asort10=new double[issueWeb.size()];
                    double[] Asort11=new double[issueWeb.size()];
                    double[] Asort12=new double[issueWeb.size()];
                    double[] Nsort1=new double[issueWeb.size()];
                    double[] Nsort2=new double[issueWeb.size()];
                    double[] Nsort3=new double[issueWeb.size()];
                    double[] Nsort4=new double[issueWeb.size()];
                    double[] Nsort5=new double[issueWeb.size()];
                    double[] Nsort6=new double[issueWeb.size()];
                    double[] Nsort7=new double[issueWeb.size()];
                    double[] Nsort8=new double[issueWeb.size()];
                    double[] Nsort9=new double[issueWeb.size()];
                    double[] Nsort10=new double[issueWeb.size()];
                    double[] Nsort11=new double[issueWeb.size()];
                    double[] Nsort12=new double[issueWeb.size()];
                    for(int i=0;i<issueWeb.size();i++) {
                        ArrayList<ArrayList<String>> singleIssue=new ArrayList<ArrayList<String>>();
                        if(name.equals("DERBY")) {
                            singleIssue=dao.getDerbyComment(i);//精确到单个issue
                            qianzhui="https://issues.apache.org/jira/browse/DERBY-";
                        }else if(name.equals("DROOLS")) {
                            singleIssue=dao.getDroolsComment(i);//精确到单个issue
                            qianzhui="https://issues.jboss.org/browse/DROOLS-";
                        }else if(name.equals("GROOVY")) {
                            singleIssue=dao.getGroovyComment(i);//精确到单个issue
                            qianzhui="https://issues.apache.org/jira/browse/GROOVY-";
                        }else if(name.equals("ISPN")) {
                            singleIssue=dao.getIspnComment(i);//精确到单个issue
                            qianzhui="https://issues.jboss.org/browse/ISPN-";
                        }else if(name.equals("MNG")) {
                            singleIssue=dao.getMngComment(i);//精确到单个issue
                            qianzhui="https://issues.apache.org/jira/browse/MNG-";
                        }else if(name.equals("PIG")) {
                            singleIssue=dao.getPigComment(i);//精确到单个issue
                            qianzhui="https://issues.apache.org/jira/browse/PIG-";
                        }else if(name.equals("JBSEAM")) {
                            singleIssue=dao.getJbseamComment(i);//精确到单个issue
                            qianzhui="https://issues.jboss.org/browse/JBSEAM-";
                        }
                        double activeScore1=0;//1月积极分均值
                        double negativeScore1=0;//1月消极分均值
                        double activeScore2=0;//2月积极分均值
                        double negativeScore2=0;//2月消极分均值
                        double activeScore3=0;//3月积极分均值
                        double negativeScore3=0;//3月消极分均值
                        double activeScore4=0;//4月积极分均值
                        double negativeScore4=0;//4月消极分均值
                        double activeScore5=0;//5月积极分均值
                        double negativeScore5=0;//5月消极分均值
                        double activeScore6=0;//6月积极分均值
                        double negativeScore6=0;//6月消极分均值
                        double activeScore7=0;//7月积极分均值
                        double negativeScore7=0;//7月消极分均值
                        double activeScore8=0;//8月积极分均值
                        double negativeScore8=0;//8月消极分均值
                        double activeScore9=0;//9月积极分均值
                        double negativeScore9=0;//9月消极分均值
                        double activeScore10=0;//10月积极分均值
                        double negativeScore10=0;//10月消极分均值
                        double activeScore11=0;//11月积极分均值
                        double negativeScore11=0;//11月消极分均值
                        double activeScore12=0;//12月积极分均值
                        double negativeScore12=0;//12月消极分均值
                        int count1,count2,count3,count4,count5,count6,count7,count8,count9,count10,count11,count12;
                        count1=count2=count3=count4=count5=count6=count7=count8=count9=count10=count11=count12=0;
                        for(int j=0;j<singleIssue.size();j++) {
                            String datetime=singleIssue.get(j).get(2);
                            String month=datetime.substring(5, 7);//月份
                            String year=datetime.substring(0, 4);//年份
                            if(year.equals("20"+y)) {
                                if(month.equals("01")) {//一月
                                    count1++;
                                    activeScore1+=Integer.parseInt(singleIssue.get(j).get(4));
                                    negativeScore1+=Integer.parseInt(singleIssue.get(j).get(5));
                                }else if(month.equals("02")) {
                                    count2++;
                                    activeScore2+=Integer.parseInt(singleIssue.get(j).get(4));
                                    negativeScore2+=Integer.parseInt(singleIssue.get(j).get(5));
                                }else if(month.equals("03")) {
                                    count3++;
                                    activeScore3+=Integer.parseInt(singleIssue.get(j).get(4));
                                    negativeScore3+=Integer.parseInt(singleIssue.get(j).get(5));
                                }else if(month.equals("04")) {
                                    count4++;
                                    activeScore4+=Integer.parseInt(singleIssue.get(j).get(4));
                                    negativeScore4+=Integer.parseInt(singleIssue.get(j).get(5));
                                }else if(month.equals("05")) {
                                    count5++;
                                    activeScore5+=Integer.parseInt(singleIssue.get(j).get(4));
                                    negativeScore5+=Integer.parseInt(singleIssue.get(j).get(5));
                                }else if(month.equals("06")) {
                                    count6++;
                                    activeScore6+=Integer.parseInt(singleIssue.get(j).get(4));
                                    negativeScore6+=Integer.parseInt(singleIssue.get(j).get(5));
                                }else if(month.equals("07")) {
                                    count7++;
                                    activeScore7+=Integer.parseInt(singleIssue.get(j).get(4));
                                    negativeScore7+=Integer.parseInt(singleIssue.get(j).get(5));
                                }else if(month.equals("08")) {
                                    count8++;
                                    activeScore8+=Integer.parseInt(singleIssue.get(j).get(4));
                                    negativeScore8+=Integer.parseInt(singleIssue.get(j).get(5));
                                }else if(month.equals("09")) {
                                    count9++;
                                    activeScore9+=Integer.parseInt(singleIssue.get(j).get(4));
                                    negativeScore9+=Integer.parseInt(singleIssue.get(j).get(5));
                                }else if(month.equals("10")) {
                                    count10++;
                                    activeScore10+=Integer.parseInt(singleIssue.get(j).get(4));
                                    negativeScore10+=Integer.parseInt(singleIssue.get(j).get(5));
                                }else if(month.equals("11")) {
                                    count11++;
                                    activeScore11+=Integer.parseInt(singleIssue.get(j).get(4));
                                    negativeScore11+=Integer.parseInt(singleIssue.get(j).get(5));
                                }else if(month.equals("12")) {
                                    count12++;
                                    activeScore12+=Integer.parseInt(singleIssue.get(j).get(4));
                                    negativeScore12+=Integer.parseInt(singleIssue.get(j).get(5));
                                }
                            }

                        }
                        if(count1!=0) {
                            activeScore1=activeScore1/count1;
                            negativeScore1=negativeScore1/count1;
                        }
                        if(count2!=0) {
                            activeScore2=activeScore2/count2;
                            negativeScore2=negativeScore2/count2;
                        }
                        if(count3!=0) {
                            activeScore3=activeScore3/count3;
                            negativeScore3=negativeScore3/count3;
                        }
                        if(count4!=0) {
                            activeScore4=activeScore4/count4;
                            negativeScore4=negativeScore4/count4;
                        }
                        if(count5!=0) {
                            activeScore5=activeScore5/count5;
                            negativeScore5=negativeScore5/count5;
                        }
                        if(count6!=0) {
                            activeScore6=activeScore6/count6;
                            negativeScore6=negativeScore6/count6;
                        }
                        if(count7!=0) {
                            activeScore7=activeScore7/count7;
                            negativeScore7=negativeScore7/count7;
                        }
                        if(count8!=0) {
                            activeScore8=activeScore8/count8;
                            negativeScore8=negativeScore8/count8;
                        }
                        if(count9!=0) {
                            activeScore9=activeScore9/count9;
                            negativeScore9=negativeScore9/count9;
                        }
                        if(count10!=0) {
                            activeScore10=activeScore10/count10;
                            negativeScore10=negativeScore10/count10;
                        }
                        if(count11!=0) {
                            activeScore11=activeScore11/count11;
                            negativeScore11=negativeScore11/count11;
                        }
                        if(count12!=0) {
                            activeScore12=activeScore12/count12;
                            negativeScore12=negativeScore12/count12;
                        }
                        issuesweb[i]=qianzhui+i;//issue网址
                        Asort1[i]=activeScore1;//1月积极均值
                        Nsort1[i]=negativeScore1;//1月消极均值
                        Asort2[i]=activeScore2;//2月积极均值
                        Nsort2[i]=negativeScore2;//2月消极均值
                        Asort3[i]=activeScore3;//3月积极均值
                        Nsort3[i]=negativeScore3;//3月消极均值
                        Asort4[i]=activeScore4;//4月积极均值
                        Nsort4[i]=negativeScore4;//4月消极均值
                        Asort5[i]=activeScore5;//5月积极均值
                        Nsort5[i]=negativeScore5;//5月消极均值
                        Asort6[i]=activeScore6;//6月积极均值
                        Nsort6[i]=negativeScore6;//6月消极均值
                        Asort7[i]=activeScore7;//7月积极均值
                        Nsort7[i]=negativeScore7;//7月消极均值
                        Asort8[i]=activeScore8;//8月积极均值
                        Nsort8[i]=negativeScore8;//8月消极均值
                        Asort9[i]=activeScore9;//9月积极均值
                        Nsort9[i]=negativeScore9;//9月消极均值
                        Asort10[i]=activeScore10;//10月积极均值
                        Nsort10[i]=negativeScore10;//10月消极均值
                        Asort11[i]=activeScore11;//11月积极均值
                        Nsort11[i]=negativeScore11;//11月消极均值
                        Asort12[i]=activeScore12;//12月积极均值
                        Nsort12[i]=negativeScore12;//12月消极均值

                    }
                    String afterASort1[]=new String[issuesweb.length];
                    String afterNSort1[]=new String[issuesweb.length];
                    String afterASort2[]=new String[issuesweb.length];
                    String afterNSort2[]=new String[issuesweb.length];
                    String afterASort3[]=new String[issuesweb.length];
                    String afterNSort3[]=new String[issuesweb.length];
                    String afterASort4[]=new String[issuesweb.length];
                    String afterNSort4[]=new String[issuesweb.length];
                    String afterASort5[]=new String[issuesweb.length];
                    String afterNSort5[]=new String[issuesweb.length];
                    String afterASort6[]=new String[issuesweb.length];
                    String afterNSort6[]=new String[issuesweb.length];
                    String afterASort7[]=new String[issuesweb.length];
                    String afterNSort7[]=new String[issuesweb.length];
                    String afterASort8[]=new String[issuesweb.length];
                    String afterNSort8[]=new String[issuesweb.length];
                    String afterASort9[]=new String[issuesweb.length];
                    String afterNSort9[]=new String[issuesweb.length];
                    String afterASort10[]=new String[issuesweb.length];
                    String afterNSort10[]=new String[issuesweb.length];
                    String afterASort11[]=new String[issuesweb.length];
                    String afterNSort11[]=new String[issuesweb.length];
                    String afterASort12[]=new String[issuesweb.length];
                    String afterNSort12[]=new String[issuesweb.length];
                    for(int i=0;i<issuesweb.length;i++) {
                        afterASort1[i]=issuesweb[i];
                        afterNSort1[i]=issuesweb[i];
                        afterASort2[i]=issuesweb[i];
                        afterNSort2[i]=issuesweb[i];
                        afterASort3[i]=issuesweb[i];
                        afterNSort3[i]=issuesweb[i];
                        afterASort4[i]=issuesweb[i];
                        afterNSort4[i]=issuesweb[i];
                        afterASort5[i]=issuesweb[i];
                        afterNSort5[i]=issuesweb[i];
                        afterASort6[i]=issuesweb[i];
                        afterNSort6[i]=issuesweb[i];
                        afterASort7[i]=issuesweb[i];
                        afterNSort7[i]=issuesweb[i];
                        afterASort8[i]=issuesweb[i];
                        afterNSort8[i]=issuesweb[i];
                        afterASort9[i]=issuesweb[i];
                        afterNSort9[i]=issuesweb[i];
                        afterASort10[i]=issuesweb[i];
                        afterNSort10[i]=issuesweb[i];
                        afterASort11[i]=issuesweb[i];
                        afterNSort11[i]=issuesweb[i];
                        afterASort12[i]=issuesweb[i];
                        afterNSort12[i]=issuesweb[i];
                    }
                    afterASort1=bubbleSort(Asort1, afterASort1);
                    afterNSort1=bubbleSort(Nsort1, afterNSort1);
                    afterASort2=bubbleSort(Asort2, afterASort2);
                    afterNSort2=bubbleSort(Nsort2, afterNSort2);
                    afterASort3=bubbleSort(Asort3, afterASort3);
                    afterNSort3=bubbleSort(Nsort3, afterNSort3);
                    afterASort4=bubbleSort(Asort4, afterASort4);
                    afterNSort4=bubbleSort(Nsort4, afterNSort4);
                    afterASort5=bubbleSort(Asort5, afterASort5);
                    afterNSort5=bubbleSort(Nsort5, afterNSort5);
                    afterASort6=bubbleSort(Asort6, afterASort6);
                    afterNSort6=bubbleSort(Nsort6, afterNSort6);
                    afterASort7=bubbleSort(Asort7, afterASort7);
                    afterNSort7=bubbleSort(Nsort7, afterNSort7);
                    afterASort8=bubbleSort(Asort8, afterASort8);
                    afterNSort8=bubbleSort(Nsort8, afterNSort8);
                    afterASort9=bubbleSort(Asort9, afterASort9);
                    afterNSort9=bubbleSort(Nsort9, afterNSort9);
                    afterASort10=bubbleSort(Asort10, afterASort10);
                    afterNSort10=bubbleSort(Nsort10, afterNSort10);
                    afterASort11=bubbleSort(Asort11, afterASort11);
                    afterNSort11=bubbleSort(Nsort11, afterNSort11);
                    afterASort12=bubbleSort(Asort12, afterASort12);
                    afterNSort12=bubbleSort(Nsort12, afterNSort12);
                    ArrayList<String> temp=new ArrayList<String>();
                    temp.add(afterASort1[0]);//1月top1积极
                    temp.add(afterNSort1[afterNSort1.length-1]);//1月top1消极
                    temp.add(afterASort2[0]);//2月top1积极
                    temp.add(afterNSort2[afterNSort2.length-1]);//2月top1消极
                    temp.add(afterASort3[0]);//3月top1积极
                    temp.add(afterNSort3[afterNSort3.length-1]);//3月top1消极
                    temp.add(afterASort4[0]);//4月top1积极
                    temp.add(afterNSort4[afterNSort4.length-1]);//4月top1消极
                    temp.add(afterASort5[0]);//5月top1积极
                    temp.add(afterNSort5[afterNSort5.length-1]);//5月top1消极
                    temp.add(afterASort6[0]);//6月top1积极
                    temp.add(afterNSort6[afterNSort6.length-1]);//6月top1消极
                    temp.add(afterASort7[0]);//7月top1积极
                    temp.add(afterNSort7[afterNSort7.length-1]);//7月top1消极
                    temp.add(afterASort8[0]);//8月top1积极
                    temp.add(afterNSort8[afterNSort8.length-1]);//8月top1消极
                    temp.add(afterASort9[0]);//9月top1积极
                    temp.add(afterNSort9[afterNSort9.length-1]);//9月top1消极
                    temp.add(afterASort10[0]);//10月top1积极
                    temp.add(afterNSort10[afterNSort10.length-1]);//10月top1消极
                    temp.add(afterASort11[0]);//11月top1积极
                    temp.add(afterNSort11[afterNSort11.length-1]);//11月top1消极
                    temp.add(afterASort12[0]);//12月top1积极
                    temp.add(afterNSort12[afterNSort12.length-1]);//12月top1消极
                    result.add(temp);
                }
            }
        }catch(Exception e) {
            System.out.println(name+"月度top1出错");
        }
        return result;
    }

    /*17,18,19月度top1*/
    public ArrayList<ArrayList<String>> getLMdata(String name){
        ArrayList<ArrayList<String>> res=new ArrayList<ArrayList<String>>();
        ArrayList<String> temp17=new ArrayList<String>();
        ArrayList<String> temp18=new ArrayList<String>();
        ArrayList<String> temp19=new ArrayList<String>();
        //前12个积极，后12个消极
        if(name.equals("DROOLS")){
            temp17.add("https://issues.jboss.org/browse/DROOLS-329");//积极
            temp17.add("https://issues.jboss.org/browse/DROOLS-662");
            temp17.add("https://issues.jboss.org/browse/DROOLS-1461");
            temp17.add("https://issues.jboss.org/browse/DROOLS-1130");
            temp17.add("https://issues.jboss.org/browse/DROOLS-1543");
            temp17.add("https://issues.jboss.org/browse/DROOLS-1388");
            temp17.add("https://issues.jboss.org/browse/DROOLS-1662");
            temp17.add("https://issues.jboss.org/browse/DROOLS-1943");
            temp17.add("https://issues.jboss.org/browse/DROOLS-1737");
            temp17.add("https://issues.jboss.org/browse/DROOLS-1765");
            temp17.add("https://issues.jboss.org/browse/DROOLS-1745");
            temp17.add("https://issues.jboss.org/browse/DROOLS-1949");
            temp17.add("https://issues.jboss.org/browse/DROOLS-1391");//消极
            temp17.add("https://issues.jboss.org/browse/DROOLS-1448");
            temp17.add("https://issues.jboss.org/browse/DROOLS-1502");
            temp17.add("https://issues.jboss.org/browse/DROOLS-1534");
            temp17.add("https://issues.jboss.org/browse/DROOLS-1576");
            temp17.add("https://issues.jboss.org/browse/DROOLS-1869");
            temp17.add("https://issues.jboss.org/browse/DROOLS-1654");
            temp17.add("https://issues.jboss.org/browse/DROOLS-1943");
            temp17.add("https://issues.jboss.org/browse/DROOLS-1927");
            temp17.add("https://issues.jboss.org/browse/DROOLS-1769");
            temp17.add("https://issues.jboss.org/browse/DROOLS-2138");
            temp17.add("https://issues.jboss.org/browse/DROOLS-2170");

            temp18.add("https://issues.jboss.org/browse/DROOLS-783");//积极
            temp18.add("https://issues.jboss.org/browse/DROOLS-2319");
            temp18.add("https://issues.jboss.org/browse/DROOLS-2411");
            temp18.add("https://issues.jboss.org/browse/DROOLS-2426");
            temp18.add("https://issues.jboss.org/browse/DROOLS-2431");
            temp18.add("https://issues.jboss.org/browse/DROOLS-2156");
            temp18.add("https://issues.jboss.org/browse/DROOLS-2714");
            temp18.add("https://issues.jboss.org/browse/DROOLS-2457");
            temp18.add("https://issues.jboss.org/browse/DROOLS-2964");
            temp18.add("https://issues.jboss.org/browse/DROOLS-3037");
            temp18.add("https://issues.jboss.org/browse/DROOLS-3221");
            temp18.add("https://issues.jboss.org/browse/DROOLS-2313");//
            temp18.add("https://issues.jboss.org/browse/DROOLS-2160");//消极
            temp18.add("https://issues.jboss.org/browse/DROOLS-2343");
            temp18.add("https://issues.jboss.org/browse/DROOLS-2411");
            temp18.add("https://issues.jboss.org/browse/DROOLS-2504");
            temp18.add("https://issues.jboss.org/browse/DROOLS-2595");
            temp18.add("https://issues.jboss.org/browse/DROOLS-2586");
            temp18.add("https://issues.jboss.org/browse/DROOLS-2755");
            temp18.add("https://issues.jboss.org/browse/DROOLS-2803");
            temp18.add("https://issues.jboss.org/browse/DROOLS-1937");
            temp18.add("https://issues.jboss.org/browse/DROOLS-3081");
            temp18.add("https://issues.jboss.org/browse/DROOLS-3271");
            temp18.add("https://issues.jboss.org/browse/DROOLS-3430");

            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");

        }else if(name.equals("GROOVY")){
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-7423");//积极
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-3493");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-8082");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-7646");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-8175");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-5227");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-8270");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-7763");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-8329");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-8329");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-7407");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-7087");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-6220");//消极
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-7405");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-5122");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-8146");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-8178");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-8227");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-6655");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-8250");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-8329");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-8329");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-8301");
            temp17.add("https://issues.apache.org/jira/browse/GROOVY-8241");

            temp18.add("https://issues.apache.org/jira/browse/GROOVY-5169");//积极
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-8241");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-8113");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-7202");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-7154");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-8195");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-7404");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-4360");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-4214");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-8329");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-7854");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-6532");//
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-3942");//消极
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-8241");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-8298");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-8301");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-4151");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-8008");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-8212");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-7891");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-7764");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-8329");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-7854");
            temp18.add("https://issues.apache.org/jira/browse/GROOVY-4063");

            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");

        }else if(name.equals("PIG")){
            temp17.add("https://issues.apache.org/jira/browse/PIG-3498");//积极
            temp17.add("https://issues.apache.org/jira/browse/PIG-4748");
            temp17.add("https://issues.apache.org/jira/browse/PIG-5155");
            temp17.add("https://issues.apache.org/jira/browse/PIG-5164");
            temp17.add("https://issues.apache.org/jira/browse/PIG-4449");
            temp17.add("https://issues.apache.org/jira/browse/PIG-5268");
            temp17.add("https://issues.apache.org/jira/browse/PIG-5273");
            temp17.add("https://issues.apache.org/jira/browse/PIG-5268");
            temp17.add("https://issues.apache.org/jira/browse/PIG-5290");
            temp17.add("https://issues.apache.org/jira/browse/PIG-5310");
            temp17.add("https://issues.apache.org/jira/browse/PIG-4324");
            temp17.add("https://issues.apache.org/jira/browse/PIG-5110");//
            temp17.add("https://issues.apache.org/jira/browse/PIG-5086");//消极
            temp17.add("https://issues.apache.org/jira/browse/PIG-5156");
            temp17.add("https://issues.apache.org/jira/browse/PIG-5172");
            temp17.add("https://issues.apache.org/jira/browse/PIG-5199");
            temp17.add("https://issues.apache.org/jira/browse/PIG-5225");
            temp17.add("https://issues.apache.org/jira/browse/PIG-5260");
            temp17.add("https://issues.apache.org/jira/browse/PIG-5278");
            temp17.add("https://issues.apache.org/jira/browse/PIG-5256");
            temp17.add("https://issues.apache.org/jira/browse/PIG-4621");
            temp17.add("https://issues.apache.org/jira/browse/PIG-5310");
            temp17.add("https://issues.apache.org/jira/browse/PIG-5312");
            temp17.add("https://issues.apache.org/jira/browse/PIG-5318");

            temp18.add("https://issues.apache.org/jira/browse/PIG-5320");//积极
            temp18.add("https://issues.apache.org/jira/browse/PIG-5331");
            temp18.add("https://issues.apache.org/jira/browse/PIG-2557");
            temp18.add("https://issues.apache.org/jira/browse/PIG-5333");
            temp18.add("https://issues.apache.org/jira/browse/PIG-5338");
            temp18.add("https://issues.apache.org/jira/browse/PIG-2599");
            temp18.add("https://issues.apache.org/jira/browse/PIG-4373");
            temp18.add("https://issues.apache.org/jira/browse/PIG-5253");
            temp18.add("https://issues.apache.org/jira/browse/PIG-5342");
            temp18.add("https://issues.apache.org/jira/browse/PIG-5360");
            temp18.add("https://issues.apache.org/jira/browse/PIG-5368");
            temp18.add("https://issues.apache.org/jira/browse/PIG-5371");//
            temp18.add("https://issues.apache.org/jira/browse/PIG-5253");//消极
            temp18.add("https://issues.apache.org/jira/browse/PIG-5331");
            temp18.add("https://issues.apache.org/jira/browse/PIG-5331");
            temp18.add("https://issues.apache.org/jira/browse/PIG-5338");
            temp18.add("https://issues.apache.org/jira/browse/PIG-5338");
            temp18.add("https://issues.apache.org/jira/browse/PIG-2599");
            temp18.add("https://issues.apache.org/jira/browse/PIG-5348");
            temp18.add("https://issues.apache.org/jira/browse/PIG-766");
            temp18.add("https://issues.apache.org/jira/browse/PIG-5354");
            temp18.add("https://issues.apache.org/jira/browse/PIG-3038");
            temp18.add("https://issues.apache.org/jira/browse/PIG-5369");
            temp18.add("https://issues.apache.org/jira/browse/PIG-5372");

            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");



        }else if(name.equals("MNG")){
            temp17.add("https://issues.apache.org/jira/browse/MNG-5894");//积极
            temp17.add("https://issues.apache.org/jira/browse/MNG-2199");
            temp17.add("https://issues.apache.org/jira/browse/MNG-5585");
            temp17.add("https://issues.apache.org/jira/browse/MNG-6112");
            temp17.add("https://issues.apache.org/jira/browse/MNG-6007");
            temp17.add("https://issues.apache.org/jira/browse/MNG-6186");
            temp17.add("https://issues.apache.org/jira/browse/MNG-3328");
            temp17.add("https://issues.apache.org/jira/browse/MNG-5937");
            temp17.add("https://issues.apache.org/jira/browse/MNG-6287");
            temp17.add("https://issues.apache.org/jira/browse/MNG-1378");
            temp17.add("https://issues.apache.org/jira/browse/MNG-6007");
            temp17.add("https://issues.apache.org/jira/browse/MNG-6112");//
            temp17.add("https://issues.apache.org/jira/browse/MNG-6162");//消极
            temp17.add("https://issues.apache.org/jira/browse/MNG-5315");
            temp17.add("https://issues.apache.org/jira/browse/MNG-5315");
            temp17.add("https://issues.apache.org/jira/browse/MNG-6223");
            temp17.add("https://issues.apache.org/jira/browse/MNG-5728");
            temp17.add("https://issues.apache.org/jira/browse/MNG-6241");
            temp17.add("https://issues.apache.org/jira/browse/MNG-6262");
            temp17.add("https://issues.apache.org/jira/browse/MNG-5868");
            temp17.add("https://issues.apache.org/jira/browse/MNG-5728");
            temp17.add("https://issues.apache.org/jira/browse/MNG-1867");
            temp17.add("https://issues.apache.org/jira/browse/MNG-6297");
            temp17.add("https://issues.apache.org/jira/browse/MNG-6320");

            temp18.add("https://issues.apache.org/jira/browse/MNG-6296");//积极
            temp18.add("https://issues.apache.org/jira/browse/MNG-6311");
            temp18.add("https://issues.apache.org/jira/browse/MNG-5588");
            temp18.add("https://issues.apache.org/jira/browse/MNG-6390");
            temp18.add("https://issues.apache.org/jira/browse/MNG-6364");
            temp18.add("https://issues.apache.org/jira/browse/MNG-6399");
            temp18.add("https://issues.apache.org/jira/browse/MNG-6386");
            temp18.add("https://issues.apache.org/jira/browse/MNG-6441");
            temp18.add("https://issues.apache.org/jira/browse/MNG-6159");
            temp18.add("https://issues.apache.org/jira/browse/MNG-5951");
            temp18.add("https://issues.apache.org/jira/browse/MNG-6294");
            temp18.add("https://issues.apache.org/jira/browse/MNG-6450");//
            temp18.add("https://issues.apache.org/jira/browse/MNG-4917");//消极
            temp18.add("https://issues.apache.org/jira/browse/MNG-6361");
            temp18.add("https://issues.apache.org/jira/browse/MNG-6380");
            temp18.add("https://issues.apache.org/jira/browse/MNG-6400");
            temp18.add("https://issues.apache.org/jira/browse/MNG-6028");
            temp18.add("https://issues.apache.org/jira/browse/MNG-6422");
            temp18.add("https://issues.apache.org/jira/browse/MNG-6447");
            temp18.add("https://issues.apache.org/jira/browse/MNG-6442");
            temp18.add("https://issues.apache.org/jira/browse/MNG-6480");
            temp18.add("https://issues.apache.org/jira/browse/MNG-6465");
            temp18.add("https://issues.apache.org/jira/browse/MNG-6506");
            temp18.add("https://issues.apache.org/jira/browse/MNG-6532");

            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");

        }else if(name.equals("ISPN")){
            temp17.add("https://issues.jboss.org/browse/ISPN-7264");//积极
            temp17.add("https://issues.jboss.org/browse/ISPN-7387");
            temp17.add("https://issues.jboss.org/browse/ISPN-7554");
            temp17.add("https://issues.jboss.org/browse/ISPN-7359");
            temp17.add("https://issues.jboss.org/browse/ISPN-7368");
            temp17.add("https://issues.jboss.org/browse/ISPN-7863");
            temp17.add("https://issues.jboss.org/browse/ISPN-7024");
            temp17.add("https://issues.jboss.org/browse/ISPN-3992");
            temp17.add("https://issues.jboss.org/browse/ISPN-3849");
            temp17.add("https://issues.jboss.org/browse/ISPN-8257");
            temp17.add("https://issues.jboss.org/browse/ISPN-8258");
            temp17.add("https://issues.jboss.org/browse/ISPN-7098");//
            temp17.add("https://issues.jboss.org/browse/ISPN-7425");//消极
            temp17.add("https://issues.jboss.org/browse/ISPN-7526");
            temp17.add("https://issues.jboss.org/browse/ISPN-7537");
            temp17.add("https://issues.jboss.org/browse/ISPN-7542");
            temp17.add("https://issues.jboss.org/browse/ISPN-7514");
            temp17.add("https://issues.jboss.org/browse/ISPN-7863");
            temp17.add("https://issues.jboss.org/browse/ISPN-8114");
            temp17.add("https://issues.jboss.org/browse/ISPN-8220");
            temp17.add("https://issues.jboss.org/browse/ISPN-8298");
            temp17.add("https://issues.jboss.org/browse/ISPN-8257");
            temp17.add("https://issues.jboss.org/browse/ISPN-8519");
            temp17.add("https://issues.jboss.org/browse/ISPN-7098");

            temp18.add("https://issues.jboss.org/browse/ISPN-8173");//积极
            temp18.add("https://issues.jboss.org/browse/ISPN-8723");
            temp18.add("https://issues.jboss.org/browse/ISPN-8728");
            temp18.add("https://issues.jboss.org/browse/ISPN-8941");
            temp18.add("https://issues.jboss.org/browse/ISPN-2442");
            temp18.add("https://issues.jboss.org/browse/ISPN-9173");
            temp18.add("https://issues.jboss.org/browse/ISPN-9338");
            temp18.add("https://issues.jboss.org/browse/ISPN-9410");
            temp18.add("https://issues.jboss.org/browse/ISPN-9501");
            temp18.add("https://issues.jboss.org/browse/ISPN-9588");
            temp18.add("https://issues.jboss.org/browse/ISPN-6924");
            temp18.add("https://issues.jboss.org/browse/ISPN-9761");//
            temp18.add("https://issues.jboss.org/browse/ISPN-8676");//消极
            temp18.add("https://issues.jboss.org/browse/ISPN-8888");
            temp18.add("https://issues.jboss.org/browse/ISPN-8975");
            temp18.add("https://issues.jboss.org/browse/ISPN-9106");
            temp18.add("https://issues.jboss.org/browse/ISPN-9198");
            temp18.add("https://issues.jboss.org/browse/ISPN-9173");
            temp18.add("https://issues.jboss.org/browse/ISPN-8241");
            temp18.add("https://issues.jboss.org/browse/ISPN-9410");
            temp18.add("https://issues.jboss.org/browse/ISPN-9542");
            temp18.add("https://issues.jboss.org/browse/ISPN-9588");
            temp18.add("https://issues.jboss.org/browse/ISPN-9605");
            temp18.add("https://issues.jboss.org/browse/ISPN-9809");


            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");

        }else if(name.equals("DERBY")){
            temp17.add("https://issues.apache.org/jira/browse/DERBY-6920");//积极
            temp17.add("https://issues.apache.org/jira/browse/DERBY-5543");
            temp17.add("https://issues.apache.org/jira/browse/DERBY-6904");
            temp17.add("https://issues.apache.org/jira/browse/DERBY-6929");
            temp17.add("https://issues.apache.org/jira/browse/DERBY-6933");
            temp17.add("https://issues.apache.org/jira/browse/DERBY-6937");
            temp17.add("https://issues.apache.org/jira/browse/DERBY-4041");
            temp17.add("https://issues.apache.org/jira/browse/DERBY-6909");
            temp17.add("https://issues.apache.org/jira/browse/DERBY-6904");
            temp17.add("https://issues.apache.org/jira/browse/DERBY-5543");
            temp17.add("https://issues.apache.org/jira/browse/DERBY-4842");
            temp17.add("https://issues.apache.org/jira/browse/DERBY-6979");//
            temp17.add("https://issues.apache.org/jira/browse/DERBY-6919");//消极
            temp17.add("https://issues.apache.org/jira/browse/DERBY-6920");
            temp17.add("https://issues.apache.org/jira/browse/DERBY-6766");
            temp17.add("https://issues.apache.org/jira/browse/DERBY-6929");
            temp17.add("https://issues.apache.org/jira/browse/DERBY-6933");
            temp17.add("https://issues.apache.org/jira/browse/DERBY-6766");
            temp17.add("https://issues.apache.org/jira/browse/DERBY-6944");
            temp17.add("https://issues.apache.org/jira/browse/DERBY-6949");
            temp17.add("https://issues.apache.org/jira/browse/DERBY-6958");
            temp17.add("https://issues.apache.org/jira/browse/DERBY-6975");
            temp17.add("https://issues.apache.org/jira/browse/DERBY-4842");
            temp17.add("https://issues.apache.org/jira/browse/DERBY-6979");

            temp18.add("https://issues.apache.org/jira/browse/DERBY-6981");//积极
            temp18.add("https://issues.apache.org/jira/browse/DERBY-6981");
            temp18.add("https://issues.apache.org/jira/browse/DERBY-6983");
            temp18.add("https://issues.apache.org/jira/browse/DERBY-6996");
            temp18.add("https://issues.apache.org/jira/browse/DERBY-6990");
            temp18.add("https://issues.apache.org/jira/browse/DERBY-6998");
            temp18.add("https://issues.apache.org/jira/browse/DERBY-6999");
            temp18.add("https://issues.apache.org/jira/browse/DERBY-6815");
            temp18.add("https://issues.apache.org/jira/browse/DERBY-6998");
            temp18.add("https://issues.apache.org/jira/browse/DERBY-3547");
            temp18.add("https://issues.apache.org/jira/browse/DERBY-7018");
            temp18.add("https://issues.apache.org/jira/browse/DERBY-6973");//
            temp18.add("https://issues.apache.org/jira/browse/DERBY-6981");//消极
            temp18.add("https://issues.apache.org/jira/browse/DERBY-6985");
            temp18.add("https://issues.apache.org/jira/browse/DERBY-6983");
            temp18.add("https://issues.apache.org/jira/browse/DERBY-6988");
            temp18.add("https://issues.apache.org/jira/browse/DERBY-6998");
            temp18.add("https://issues.apache.org/jira/browse/DERBY-6999");
            temp18.add("https://issues.apache.org/jira/browse/DERBY-7003");
            temp18.add("https://issues.apache.org/jira/browse/DERBY-7007");
            temp18.add("https://issues.apache.org/jira/browse/DERBY-7010");
            temp18.add("https://issues.apache.org/jira/browse/DERBY-7011");
            temp18.add("https://issues.apache.org/jira/browse/DERBY-7019");
            temp18.add("https://issues.apache.org/jira/browse/DERBY-7025");

            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");

        }else if(name.equals("JBSEAM")){
            temp17.add("https://issues.jboss.org/browse/JBSEAM-3247");//积极
            temp17.add("https://issues.jboss.org/browse/JBSEAM-3247");
            temp17.add("https://issues.jboss.org/browse/JBSEAM-3247");
            temp17.add("https://issues.jboss.org/browse/JBSEAM-3247");
            temp17.add("https://issues.jboss.org/browse/JBSEAM-3247");
            temp17.add("https://issues.jboss.org/browse/JBSEAM-3247");
            temp17.add("https://issues.jboss.org/browse/JBSEAM-3247");
            temp17.add("https://issues.jboss.org/browse/JBSEAM-3247");
            temp17.add("https://issues.jboss.org/browse/JBSEAM-3247");
            temp17.add("https://issues.jboss.org/browse/JBSEAM-4945");
            temp17.add("https://issues.jboss.org/browse/JBSEAM-3247");
            temp17.add("https://issues.jboss.org/browse/JBSEAM-3247");//
            temp17.add("https://issues.jboss.org/browse/JBSEAM-4945");//消极
            temp17.add("https://issues.jboss.org/browse/JBSEAM-4945");
            temp17.add("https://issues.jboss.org/browse/JBSEAM-4945");
            temp17.add("https://issues.jboss.org/browse/JBSEAM-4945");
            temp17.add("https://issues.jboss.org/browse/JBSEAM-3247");
            temp17.add("https://issues.jboss.org/browse/JBSEAM-3247");
            temp17.add("https://issues.jboss.org/browse/JBSEAM-3247");
            temp17.add("https://issues.jboss.org/browse/JBSEAM-3247");
            temp17.add("https://issues.jboss.org/browse/JBSEAM-3247");
            temp17.add("https://issues.jboss.org/browse/JBSEAM-4945");
            temp17.add("https://issues.jboss.org/browse/JBSEAM-3247");
            temp17.add("https://issues.jboss.org/browse/JBSEAM-3247");

            temp18.add("https://issues.jboss.org/browse/JBSEAM-3247");//积极
            temp18.add("https://issues.jboss.org/browse/JBSEAM-3247");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-3247");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-5148");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-5148");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-5148");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-5148");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-5148");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-5148");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-5148");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-5148");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-5148");//
            temp18.add("https://issues.jboss.org/browse/JBSEAM-3247");//消极
            temp18.add("https://issues.jboss.org/browse/JBSEAM-3247");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-3247");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-5148");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-5148");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-5148");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-5148");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-5148");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-5148");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-5148");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-5148");
            temp18.add("https://issues.jboss.org/browse/JBSEAM-5148");

            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");
            temp19.add("");

        }
        res.add(temp17);
        res.add(temp18);
        res.add(temp19);
        return res;
    }



    /*本地数据库的2017.1到2019.3的情绪变化*/
    public double[][] getLChange(String name) {
        double[][] result=new double[2][27];
        int[] count=new int[27];//计次数
        try {
            ArrayList<ArrayList<String>> allInfor=dao.getAllComment(name);//通过输入的网址获取所有具体信息
            for(int i=0;i<allInfor.size();i++) {
                String datetime=allInfor.get(i).get(2);//日期
                String month=datetime.substring(5,7);//月份
                String year=datetime.substring(0,4);//年份
                if(month.equals("01")&&year.equals("2017")) {
                    count[0]++;
                    result[0][0]+=Integer.parseInt(allInfor.get(i).get(4));
                    result[1][0]+=Integer.parseInt(allInfor.get(i).get(5));
                }
                if(month.equals("02")&&year.equals("2017")) {
                    count[1]++;
                    result[0][1]+=Integer.parseInt(allInfor.get(i).get(4));
                    result[1][1]+=Integer.parseInt(allInfor.get(i).get(5));
                }
                if(month.equals("03")&&year.equals("2017")) {
                    count[2]++;
                    result[0][2]+=Integer.parseInt(allInfor.get(i).get(4));
                    result[1][2]+=Integer.parseInt(allInfor.get(i).get(5));
                }
                if(month.equals("04")&&year.equals("2017")) {
                    count[3]++;
                    result[0][3]+=Integer.parseInt(allInfor.get(i).get(4));
                    result[1][3]+=Integer.parseInt(allInfor.get(i).get(5));
                }
                if(month.equals("05")&&year.equals("2017")) {
                    count[4]++;
                    result[0][4]+=Integer.parseInt(allInfor.get(i).get(4));
                    result[1][4]+=Integer.parseInt(allInfor.get(i).get(5));
                }
                if(month.equals("06")&&year.equals("2017")) {
                    count[5]++;
                    result[0][5]+=Integer.parseInt(allInfor.get(i).get(4));
                    result[1][5]+=Integer.parseInt(allInfor.get(i).get(5));
                }
                if(month.equals("07")&&year.equals("2017")) {
                    count[6]++;
                    result[0][6]+=Integer.parseInt(allInfor.get(i).get(4));
                    result[1][6]+=Integer.parseInt(allInfor.get(i).get(5));
                }
                if(month.equals("08")&&year.equals("2017")) {
                    count[7]++;
                    result[0][7]+=Integer.parseInt(allInfor.get(i).get(4));
                    result[1][7]+=Integer.parseInt(allInfor.get(i).get(5));
                }
                if(month.equals("09")&&year.equals("2017")) {
                    count[8]++;
                    result[0][8]+=Integer.parseInt(allInfor.get(i).get(4));
                    result[1][8]+=Integer.parseInt(allInfor.get(i).get(5));
                }
                if(month.equals("10")&&year.equals("2017")) {
                    count[9]++;
                    result[0][9]+=Integer.parseInt(allInfor.get(i).get(4));
                    result[1][9]+=Integer.parseInt(allInfor.get(i).get(5));
                }
                if(month.equals("11")&&year.equals("2017")) {
                    count[10]++;
                    result[0][10]+=Integer.parseInt(allInfor.get(i).get(4));
                    result[1][10]+=Integer.parseInt(allInfor.get(i).get(5));
                }
                if(month.equals("12")&&year.equals("2017")) {
                    count[11]++;
                    result[0][11]+=Integer.parseInt(allInfor.get(i).get(4));
                    result[1][11]+=Integer.parseInt(allInfor.get(i).get(5));
                }
                if(month.equals("01")&&year.equals("2018")) {
                    count[12]++;
                    result[0][12]+=Integer.parseInt(allInfor.get(i).get(4));
                    result[1][12]+=Integer.parseInt(allInfor.get(i).get(5));
                }
                if(month.equals("02")&&year.equals("2018")) {
                    count[13]++;
                    result[0][13]+=Integer.parseInt(allInfor.get(i).get(4));
                    result[1][13]+=Integer.parseInt(allInfor.get(i).get(5));
                }
                if(month.equals("03")&&year.equals("2018")) {
                    count[14]++;
                    result[0][14]+=Integer.parseInt(allInfor.get(i).get(4));
                    result[1][14]+=Integer.parseInt(allInfor.get(i).get(5));
                }
                if(month.equals("04")&&year.equals("2018")) {
                    count[15]++;
                    result[0][15]+=Integer.parseInt(allInfor.get(i).get(4));
                    result[1][15]+=Integer.parseInt(allInfor.get(i).get(5));
                }
                if(month.equals("05")&&year.equals("2018")) {
                    count[16]++;
                    result[0][16]+=Integer.parseInt(allInfor.get(i).get(4));
                    result[1][16]+=Integer.parseInt(allInfor.get(i).get(5));
                }
                if(month.equals("06")&&year.equals("2018")) {
                    count[17]++;
                    result[0][17]+=Integer.parseInt(allInfor.get(i).get(4));
                    result[1][17]+=Integer.parseInt(allInfor.get(i).get(5));
                }
                if(month.equals("07")&&year.equals("2018")) {
                    count[18]++;
                    result[0][18]+=Integer.parseInt(allInfor.get(i).get(4));
                    result[1][18]+=Integer.parseInt(allInfor.get(i).get(5));
                }
                if(month.equals("08")&&year.equals("2018")) {
                    count[19]++;
                    result[0][19]+=Integer.parseInt(allInfor.get(i).get(4));
                    result[1][19]+=Integer.parseInt(allInfor.get(i).get(5));
                }
                if(month.equals("09")&&year.equals("2018")) {
                    count[20]++;
                    result[0][20]+=Integer.parseInt(allInfor.get(i).get(4));
                    result[1][20]+=Integer.parseInt(allInfor.get(i).get(5));
                }
                if(month.equals("10")&&year.equals("2018")) {
                    count[21]++;
                    result[0][21]+=Integer.parseInt(allInfor.get(i).get(4));
                    result[1][21]+=Integer.parseInt(allInfor.get(i).get(5));
                }
                if(month.equals("11")&&year.equals("2018")) {
                    count[22]++;
                    result[0][22]+=Integer.parseInt(allInfor.get(i).get(4));
                    result[1][22]+=Integer.parseInt(allInfor.get(i).get(5));
                }
                if(month.equals("12")&&year.equals("2018")) {
                    count[23]++;
                    result[0][23]+=Integer.parseInt(allInfor.get(i).get(4));
                    result[1][23]+=Integer.parseInt(allInfor.get(i).get(5));
                }
                if(month.equals("01")&&year.equals("2019")) {
                    count[24]++;
                    result[0][24]+=Integer.parseInt(allInfor.get(i).get(4));
                    result[1][24]+=Integer.parseInt(allInfor.get(i).get(5));
                }
                if(month.equals("02")&&year.equals("2019")) {
                    count[25]++;
                    result[0][25]+=Integer.parseInt(allInfor.get(i).get(4));
                    result[1][25]+=Integer.parseInt(allInfor.get(i).get(5));
                }
                if(month.equals("03")&&year.equals("2019")) {
                    count[26]++;
                    result[0][26]+=Integer.parseInt(allInfor.get(i).get(4));
                    result[1][26]+=Integer.parseInt(allInfor.get(i).get(5));
                }


            }
            for(int j=0;j<27;j++) {
                if(count[j]!=0) {
                    result[0][j]=result[0][j]/count[j];
                    result[1][j]=result[1][j]/count[j];
                }
            }

        }catch(Exception e) {
            System.out.println(name+"月度情绪变化失败");
        }
        return result;
    }

    /*年度好坏top5 github(如果issue的数量小于5，那就返回issueWeb)*/
    public ArrayList<ArrayList<String>> getGYearTop(String url){
        ArrayList<ArrayList<String>> result= new ArrayList<ArrayList<String>>();
        try {
            ArrayList<ArrayList<String>> issueWeb=dao.getSameTypeIssue(url);//通过输入的网址获取其下的issue网址
            if(issueWeb.size()>=5) {
                double[] Asort17=new double[issueWeb.size()];
                double[] Nsort17=new double[issueWeb.size()];
                double[] Asort18=new double[issueWeb.size()];
                double[] Nsort18=new double[issueWeb.size()];
                double[] Asort19=new double[issueWeb.size()];
                double[] Nsort19=new double[issueWeb.size()];
                String[] issuesweb=new String[issueWeb.size()];

                for(int i=0;i<issueWeb.size();i++) {
                    ArrayList<ArrayList<String>> singleIssue=dao.getGithubComment(issueWeb.get(i).get(1));//精确到单个issue
                    double activeScore17=0;//2017年度积极分均值
                    double negativeScore17=0;//2017年度消极分均值
                    int count17=0;//2017年的评论数
                    double activeScore18=0;//2018年度积极分均值
                    double negativeScore18=0;//2018年度消极分均值
                    int count18=0;//2018年的评论数
                    double activeScore19=0;//2019年度积极分均值
                    double negativeScore19=0;//2019年度消极分均值
                    int count19=0;//2019年的评论数
                    for(int j=0;j<singleIssue.size();j++) {
                        int datelength=singleIssue.get(j).get(3).length();
                        if(singleIssue.get(j).get(3).substring(datelength-4, datelength).equals("2017")) {//日期年份为2017的
                            count17++;
                            activeScore17+=Integer.parseInt(singleIssue.get(j).get(7));
                            negativeScore17+=Integer.parseInt(singleIssue.get(j).get(8));
                        }
                        if(singleIssue.get(j).get(3).substring(datelength-4, datelength).equals("2018")) {//日期年份为2018的
                            count18++;
                            activeScore18+=Integer.parseInt(singleIssue.get(j).get(7));
                            negativeScore18+=Integer.parseInt(singleIssue.get(j).get(8));
                        }
                        if(singleIssue.get(j).get(3).substring(datelength-4, datelength).equals("2019")) {//日期年份为2019的
                            count19++;
                            activeScore19+=Integer.parseInt(singleIssue.get(j).get(7));
                            negativeScore19+=Integer.parseInt(singleIssue.get(j).get(8));
                        }
                    }
                    if(count17!=0) {
                        activeScore17=activeScore17/count17;
                        negativeScore17=negativeScore17/count17;
                    }
                    if(count18!=0) {
                        activeScore18=activeScore18/count18;
                        negativeScore18=negativeScore18/count18;
                    }
                    if(count19!=0) {
                        activeScore19=activeScore19/count19;
                        negativeScore19=negativeScore19/count19;
                    }
                    issuesweb[i]=issueWeb.get(i).get(1);//issue网址
                    Asort17[i]=activeScore17;//17年积极均值
                    Nsort17[i]=negativeScore17;//17年消极均值
                    Asort18[i]=activeScore18;//18年积极均值
                    Nsort18[i]=negativeScore18;//18年消极均值
                    Asort19[i]=activeScore19;//19年积极均值
                    Nsort19[i]=negativeScore19;//19年消极均值
                }
                String afterASort17[]=new String[issuesweb.length];
                String afterNSort17[]=new String[issuesweb.length];
                String afterASort18[]=new String[issuesweb.length];
                String afterNSort18[]=new String[issuesweb.length];
                String afterASort19[]=new String[issuesweb.length];
                String afterNSort19[]=new String[issuesweb.length];
                for(int i=0;i<issuesweb.length;i++) {
                    afterASort17[i]=issuesweb[i];
                    afterNSort17[i]=issuesweb[i];
                    afterASort18[i]=issuesweb[i];
                    afterNSort18[i]=issuesweb[i];
                    afterASort19[i]=issuesweb[i];
                    afterNSort19[i]=issuesweb[i];
                }
                afterASort17=bubbleSort(Asort17, afterASort17);
                afterNSort17=bubbleSort(Nsort17, afterNSort17);
                afterASort18=bubbleSort(Asort18, afterASort18);
                afterNSort18=bubbleSort(Nsort18, afterNSort18);
                afterASort19=bubbleSort(Asort19, afterASort19);
                afterNSort19=bubbleSort(Nsort19, afterNSort19);
                ArrayList<String> temp17=new ArrayList<String>();
                ArrayList<String> temp18=new ArrayList<String>();
                ArrayList<String> temp19=new ArrayList<String>();
                temp17.add(afterASort17[0]);
                temp17.add(afterASort17[1]);
                temp17.add(afterASort17[2]);
                temp17.add(afterASort17[3]);
                temp17.add(afterASort17[4]);
                temp17.add(afterNSort17[afterNSort17.length-1]);
                temp17.add(afterNSort17[afterNSort17.length-2]);
                temp17.add(afterNSort17[afterNSort17.length-3]);
                temp17.add(afterNSort17[afterNSort17.length-4]);
                temp17.add(afterNSort17[afterNSort17.length-5]);
                temp18.add(afterASort18[0]);
                temp18.add(afterASort18[1]);
                temp18.add(afterASort18[2]);
                temp18.add(afterASort18[3]);
                temp18.add(afterASort18[4]);
                temp18.add(afterNSort18[afterNSort18.length-1]);
                temp18.add(afterNSort18[afterNSort18.length-2]);
                temp18.add(afterNSort18[afterNSort18.length-3]);
                temp18.add(afterNSort18[afterNSort18.length-4]);
                temp18.add(afterNSort18[afterNSort18.length-5]);
                temp19.add(afterASort19[0]);
                temp19.add(afterASort19[1]);
                temp19.add(afterASort19[2]);
                temp19.add(afterASort19[3]);
                temp19.add(afterASort19[4]);
                temp19.add(afterNSort19[afterNSort19.length-1]);
                temp19.add(afterNSort19[afterNSort19.length-2]);
                temp19.add(afterNSort19[afterNSort19.length-3]);
                temp19.add(afterNSort19[afterNSort19.length-4]);
                temp19.add(afterNSort19[afterNSort19.length-5]);
                result.add(temp17);
                result.add(temp18);
                result.add(temp19);

            }else if(issueWeb.size()<5){//如果issue数量少于5，则直接返回这些issue
                ArrayList<String> temp=new ArrayList<String>();
                int size=issueWeb.size();
                for(int i=0;i<10;i++){
                    if(i<size) {
                        temp.add(issueWeb.get(i).get(1));
                    }else if( i>=5 && i<(size+5) ){
                        temp.add(issueWeb.get(i-5).get(1));
                    } else {
                        temp.add("not anymore");
                    }
                }
                result.add(temp);
                result.add(temp);
                result.add(temp);
            }
        }catch(Exception e) {
            System.out.println("GitHub年度top5出错");
        }

        return result;
    }

    /*月度好坏top1 github 如果当月没有issue评论，那么随机选取一个作为月度top1*/
    public ArrayList<ArrayList<String>> getGMonthTop(String url){
        ArrayList<ArrayList<String>> result= new ArrayList<ArrayList<String>>();
        try {
            ArrayList<ArrayList<String>> issueWeb=dao.getSameTypeIssue(url);//通过输入的网址获取其下的issue网址
            if(issueWeb.size()!=0) {
                for(int y=17;y<=19;y++) {//年份
                    String[] issuesweb=new String[issueWeb.size()];
                    double[] Asort1=new double[issueWeb.size()];
                    double[] Asort2=new double[issueWeb.size()];
                    double[] Asort3=new double[issueWeb.size()];
                    double[] Asort4=new double[issueWeb.size()];
                    double[] Asort5=new double[issueWeb.size()];
                    double[] Asort6=new double[issueWeb.size()];
                    double[] Asort7=new double[issueWeb.size()];
                    double[] Asort8=new double[issueWeb.size()];
                    double[] Asort9=new double[issueWeb.size()];
                    double[] Asort10=new double[issueWeb.size()];
                    double[] Asort11=new double[issueWeb.size()];
                    double[] Asort12=new double[issueWeb.size()];
                    double[] Nsort1=new double[issueWeb.size()];
                    double[] Nsort2=new double[issueWeb.size()];
                    double[] Nsort3=new double[issueWeb.size()];
                    double[] Nsort4=new double[issueWeb.size()];
                    double[] Nsort5=new double[issueWeb.size()];
                    double[] Nsort6=new double[issueWeb.size()];
                    double[] Nsort7=new double[issueWeb.size()];
                    double[] Nsort8=new double[issueWeb.size()];
                    double[] Nsort9=new double[issueWeb.size()];
                    double[] Nsort10=new double[issueWeb.size()];
                    double[] Nsort11=new double[issueWeb.size()];
                    double[] Nsort12=new double[issueWeb.size()];
                    for(int i=0;i<issueWeb.size();i++) {
                        ArrayList<ArrayList<String>> singleIssue=dao.getGithubComment(issueWeb.get(i).get(1));//精确到单个issue
                        double activeScore1=0;//1月积极分均值
                        double negativeScore1=0;//1月消极分均值
                        double activeScore2=0;//2月积极分均值
                        double negativeScore2=0;//2月消极分均值
                        double activeScore3=0;//3月积极分均值
                        double negativeScore3=0;//3月消极分均值
                        double activeScore4=0;//4月积极分均值
                        double negativeScore4=0;//4月消极分均值
                        double activeScore5=0;//5月积极分均值
                        double negativeScore5=0;//5月消极分均值
                        double activeScore6=0;//6月积极分均值
                        double negativeScore6=0;//6月消极分均值
                        double activeScore7=0;//7月积极分均值
                        double negativeScore7=0;//7月消极分均值
                        double activeScore8=0;//8月积极分均值
                        double negativeScore8=0;//8月消极分均值
                        double activeScore9=0;//9月积极分均值
                        double negativeScore9=0;//9月消极分均值
                        double activeScore10=0;//10月积极分均值
                        double negativeScore10=0;//10月消极分均值
                        double activeScore11=0;//11月积极分均值
                        double negativeScore11=0;//11月消极分均值
                        double activeScore12=0;//12月积极分均值
                        double negativeScore12=0;//12月消极分均值
                        int count1,count2,count3,count4,count5,count6,count7,count8,count9,count10,count11,count12;
                        count1=count2=count3=count4=count5=count6=count7=count8=count9=count10=count11=count12=0;
                        for(int j=0;j<singleIssue.size();j++) {
                            String datetime=singleIssue.get(j).get(3).replaceAll(" ", "");
                            String month=datetime.substring(0, 3);//月份
                            String year=datetime.substring(datetime.length()-4, datetime.length());//年份
                            if(year.equals("20"+y)) {
                                if(month.equals("Jan")) {//一月
                                    count1++;
                                    activeScore1+=Integer.parseInt(singleIssue.get(j).get(7));
                                    negativeScore1+=Integer.parseInt(singleIssue.get(j).get(8));
                                }else if(month.equals("Feb")) {
                                    count2++;
                                    activeScore2+=Integer.parseInt(singleIssue.get(j).get(7));
                                    negativeScore2+=Integer.parseInt(singleIssue.get(j).get(8));
                                }else if(month.equals("Mar")) {
                                    count3++;
                                    activeScore3+=Integer.parseInt(singleIssue.get(j).get(7));
                                    negativeScore3+=Integer.parseInt(singleIssue.get(j).get(8));
                                }else if(month.equals("Apr")) {
                                    count4++;
                                    activeScore4+=Integer.parseInt(singleIssue.get(j).get(7));
                                    negativeScore4+=Integer.parseInt(singleIssue.get(j).get(8));
                                }else if(month.equals("May")) {
                                    count5++;
                                    activeScore5+=Integer.parseInt(singleIssue.get(j).get(7));
                                    negativeScore5+=Integer.parseInt(singleIssue.get(j).get(8));
                                }else if(month.equals("Jun")) {
                                    count6++;
                                    activeScore6+=Integer.parseInt(singleIssue.get(j).get(7));
                                    negativeScore6+=Integer.parseInt(singleIssue.get(j).get(8));
                                }else if(month.equals("Jul")) {
                                    count7++;
                                    activeScore7+=Integer.parseInt(singleIssue.get(j).get(7));
                                    negativeScore7+=Integer.parseInt(singleIssue.get(j).get(8));
                                }else if(month.equals("Aug")) {
                                    count8++;
                                    activeScore8+=Integer.parseInt(singleIssue.get(j).get(7));
                                    negativeScore8+=Integer.parseInt(singleIssue.get(j).get(8));
                                }else if(month.equals("Sep")) {
                                    count9++;
                                    activeScore9+=Integer.parseInt(singleIssue.get(j).get(7));
                                    negativeScore9+=Integer.parseInt(singleIssue.get(j).get(8));
                                }else if(month.equals("Oct")) {
                                    count10++;
                                    activeScore10+=Integer.parseInt(singleIssue.get(j).get(7));
                                    negativeScore10+=Integer.parseInt(singleIssue.get(j).get(8));
                                }else if(month.equals("Nov")) {
                                    count11++;
                                    activeScore11+=Integer.parseInt(singleIssue.get(j).get(7));
                                    negativeScore11+=Integer.parseInt(singleIssue.get(j).get(8));
                                }else if(month.equals("Dec")) {
                                    count12++;
                                    activeScore12+=Integer.parseInt(singleIssue.get(j).get(7));
                                    negativeScore12+=Integer.parseInt(singleIssue.get(j).get(8));
                                }
                            }

                        }
                        if(count1!=0) {
                            activeScore1=activeScore1/count1;
                            negativeScore1=negativeScore1/count1;
                        }
                        if(count2!=0) {
                            activeScore2=activeScore2/count2;
                            negativeScore2=negativeScore2/count2;
                        }
                        if(count3!=0) {
                            activeScore3=activeScore3/count3;
                            negativeScore3=negativeScore3/count3;
                        }
                        if(count4!=0) {
                            activeScore4=activeScore4/count4;
                            negativeScore4=negativeScore4/count4;
                        }
                        if(count5!=0) {
                            activeScore5=activeScore5/count5;
                            negativeScore5=negativeScore5/count5;
                        }
                        if(count6!=0) {
                            activeScore6=activeScore6/count6;
                            negativeScore6=negativeScore6/count6;
                        }
                        if(count7!=0) {
                            activeScore7=activeScore7/count7;
                            negativeScore7=negativeScore7/count7;
                        }
                        if(count8!=0) {
                            activeScore8=activeScore8/count8;
                            negativeScore8=negativeScore8/count8;
                        }
                        if(count9!=0) {
                            activeScore9=activeScore9/count9;
                            negativeScore9=negativeScore9/count9;
                        }
                        if(count10!=0) {
                            activeScore10=activeScore10/count10;
                            negativeScore10=negativeScore10/count10;
                        }
                        if(count11!=0) {
                            activeScore11=activeScore11/count11;
                            negativeScore11=negativeScore11/count11;
                        }
                        if(count12!=0) {
                            activeScore12=activeScore12/count12;
                            negativeScore12=negativeScore12/count12;
                        }
                        issuesweb[i]=issueWeb.get(i).get(1);//issue网址
                        Asort1[i]=activeScore1;//1月积极均值
                        Nsort1[i]=negativeScore1;//1月消极均值
                        Asort2[i]=activeScore2;//2月积极均值
                        Nsort2[i]=negativeScore2;//2月消极均值
                        Asort3[i]=activeScore3;//3月积极均值
                        Nsort3[i]=negativeScore3;//3月消极均值
                        Asort4[i]=activeScore4;//4月积极均值
                        Nsort4[i]=negativeScore4;//4月消极均值
                        Asort5[i]=activeScore5;//5月积极均值
                        Nsort5[i]=negativeScore5;//5月消极均值
                        Asort6[i]=activeScore6;//6月积极均值
                        Nsort6[i]=negativeScore6;//6月消极均值
                        Asort7[i]=activeScore7;//7月积极均值
                        Nsort7[i]=negativeScore7;//7月消极均值
                        Asort8[i]=activeScore8;//8月积极均值
                        Nsort8[i]=negativeScore8;//8月消极均值
                        Asort9[i]=activeScore9;//9月积极均值
                        Nsort9[i]=negativeScore9;//9月消极均值
                        Asort10[i]=activeScore10;//10月积极均值
                        Nsort10[i]=negativeScore10;//10月消极均值
                        Asort11[i]=activeScore11;//11月积极均值
                        Nsort11[i]=negativeScore11;//11月消极均值
                        Asort12[i]=activeScore12;//12月积极均值
                        Nsort12[i]=negativeScore12;//12月消极均值

                    }
                    String afterASort1[]=new String[issuesweb.length];
                    String afterNSort1[]=new String[issuesweb.length];
                    String afterASort2[]=new String[issuesweb.length];
                    String afterNSort2[]=new String[issuesweb.length];
                    String afterASort3[]=new String[issuesweb.length];
                    String afterNSort3[]=new String[issuesweb.length];
                    String afterASort4[]=new String[issuesweb.length];
                    String afterNSort4[]=new String[issuesweb.length];
                    String afterASort5[]=new String[issuesweb.length];
                    String afterNSort5[]=new String[issuesweb.length];
                    String afterASort6[]=new String[issuesweb.length];
                    String afterNSort6[]=new String[issuesweb.length];
                    String afterASort7[]=new String[issuesweb.length];
                    String afterNSort7[]=new String[issuesweb.length];
                    String afterASort8[]=new String[issuesweb.length];
                    String afterNSort8[]=new String[issuesweb.length];
                    String afterASort9[]=new String[issuesweb.length];
                    String afterNSort9[]=new String[issuesweb.length];
                    String afterASort10[]=new String[issuesweb.length];
                    String afterNSort10[]=new String[issuesweb.length];
                    String afterASort11[]=new String[issuesweb.length];
                    String afterNSort11[]=new String[issuesweb.length];
                    String afterASort12[]=new String[issuesweb.length];
                    String afterNSort12[]=new String[issuesweb.length];
                    for(int i=0;i<issuesweb.length;i++) {
                        afterASort1[i]=issuesweb[i];
                        afterNSort1[i]=issuesweb[i];
                        afterASort2[i]=issuesweb[i];
                        afterNSort2[i]=issuesweb[i];
                        afterASort3[i]=issuesweb[i];
                        afterNSort3[i]=issuesweb[i];
                        afterASort4[i]=issuesweb[i];
                        afterNSort4[i]=issuesweb[i];
                        afterASort5[i]=issuesweb[i];
                        afterNSort5[i]=issuesweb[i];
                        afterASort6[i]=issuesweb[i];
                        afterNSort6[i]=issuesweb[i];
                        afterASort7[i]=issuesweb[i];
                        afterNSort7[i]=issuesweb[i];
                        afterASort8[i]=issuesweb[i];
                        afterNSort8[i]=issuesweb[i];
                        afterASort9[i]=issuesweb[i];
                        afterNSort9[i]=issuesweb[i];
                        afterASort10[i]=issuesweb[i];
                        afterNSort10[i]=issuesweb[i];
                        afterASort11[i]=issuesweb[i];
                        afterNSort11[i]=issuesweb[i];
                        afterASort12[i]=issuesweb[i];
                        afterNSort12[i]=issuesweb[i];
                    }
                    afterASort1=bubbleSort(Asort1, afterASort1);
                    afterNSort1=bubbleSort(Nsort1, afterNSort1);
                    afterASort2=bubbleSort(Asort2, afterASort2);
                    afterNSort2=bubbleSort(Nsort2, afterNSort2);
                    afterASort3=bubbleSort(Asort3, afterASort3);
                    afterNSort3=bubbleSort(Nsort3, afterNSort3);
                    afterASort4=bubbleSort(Asort4, afterASort4);
                    afterNSort4=bubbleSort(Nsort4, afterNSort4);
                    afterASort5=bubbleSort(Asort5, afterASort5);
                    afterNSort5=bubbleSort(Nsort5, afterNSort5);
                    afterASort6=bubbleSort(Asort6, afterASort6);
                    afterNSort6=bubbleSort(Nsort6, afterNSort6);
                    afterASort7=bubbleSort(Asort7, afterASort7);
                    afterNSort7=bubbleSort(Nsort7, afterNSort7);
                    afterASort8=bubbleSort(Asort8, afterASort8);
                    afterNSort8=bubbleSort(Nsort8, afterNSort8);
                    afterASort9=bubbleSort(Asort9, afterASort9);
                    afterNSort9=bubbleSort(Nsort9, afterNSort9);
                    afterASort10=bubbleSort(Asort10, afterASort10);
                    afterNSort10=bubbleSort(Nsort10, afterNSort10);
                    afterASort11=bubbleSort(Asort11, afterASort11);
                    afterNSort11=bubbleSort(Nsort11, afterNSort11);
                    afterASort12=bubbleSort(Asort12, afterASort12);
                    afterNSort12=bubbleSort(Nsort12, afterNSort12);
                    ArrayList<String> temp=new ArrayList<String>();
                    temp.add(afterASort1[0]);//1月top1积极
                    temp.add(afterASort2[0]);//2月top1积极
                    temp.add(afterASort3[0]);//3月top1积极
                    temp.add(afterASort4[0]);//4月top1积极
                    temp.add(afterASort5[0]);//5月top1积极
                    temp.add(afterASort6[0]);//6月top1积极
                    temp.add(afterASort7[0]);//7月top1积极
                    temp.add(afterASort8[0]);//8月top1积极
                    temp.add(afterASort9[0]);//9月top1积极
                    temp.add(afterASort10[0]);//10月top1积极
                    temp.add(afterASort11[0]);//11月top1积极
                    temp.add(afterASort12[0]);//12月top1积极
                    temp.add(afterNSort1[afterNSort1.length-1]);//1月top1消极
                    temp.add(afterNSort2[afterNSort2.length-1]);//2月top1消极
                    temp.add(afterNSort3[afterNSort3.length-1]);//3月top1消极
                    temp.add(afterNSort4[afterNSort4.length-1]);//4月top1消极
                    temp.add(afterNSort5[afterNSort5.length-1]);//5月top1消极
                    temp.add(afterNSort6[afterNSort6.length-1]);//6月top1消极
                    temp.add(afterNSort7[afterNSort7.length-1]);//7月top1消极
                    temp.add(afterNSort8[afterNSort8.length-1]);//8月top1消极
                    temp.add(afterNSort9[afterNSort9.length-1]);//9月top1消极
                    temp.add(afterNSort10[afterNSort10.length-1]);//10月top1消极
                    temp.add(afterNSort11[afterNSort11.length-1]);//11月top1消极
                    temp.add(afterNSort12[afterNSort12.length-1]);//12月top1消极
                    result.add(temp);
                }
            }
        }catch(Exception e) {
            System.out.println("GitHub月度top1出错");
        }
        return result;
    }

    /*GitHub的2017.1到2019.3的情绪变化*/
    public double[][] getGChange(String url) {
        double[][] result=new double[2][27];
        int[] count=new int[27];//计次数
        try {
            ArrayList<ArrayList<String>> allInfor=dao.getAllGitComment(url);//通过输入的网址获取所有具体信息
            for(int i=0;i<allInfor.size();i++) {
                String datetime=allInfor.get(i).get(3);//日期
                datetime=datetime.replaceAll(" ", "");
                String month=datetime.substring(0,3);//月份
                String year=datetime.substring(datetime.length()-4, datetime.length());//年份
                if(month.equals("Jan")&&year.equals("2017")) {
                    count[0]++;
                    result[0][0]+=Integer.parseInt(allInfor.get(i).get(7));
                    result[1][0]+=Integer.parseInt(allInfor.get(i).get(8));
                }
                if(month.equals("Feb")&&year.equals("2017")) {
                    count[1]++;
                    result[0][1]+=Integer.parseInt(allInfor.get(i).get(7));
                    result[1][1]+=Integer.parseInt(allInfor.get(i).get(8));
                }
                if(month.equals("Mar")&&year.equals("2017")) {
                    count[2]++;
                    result[0][2]+=Integer.parseInt(allInfor.get(i).get(7));
                    result[1][2]+=Integer.parseInt(allInfor.get(i).get(8));
                }
                if(month.equals("Apr")&&year.equals("2017")) {
                    count[3]++;
                    result[0][3]+=Integer.parseInt(allInfor.get(i).get(7));
                    result[1][3]+=Integer.parseInt(allInfor.get(i).get(8));
                }
                if(month.equals("May")&&year.equals("2017")) {
                    count[4]++;
                    result[0][4]+=Integer.parseInt(allInfor.get(i).get(7));
                    result[1][4]+=Integer.parseInt(allInfor.get(i).get(8));
                }
                if(month.equals("Jun")&&year.equals("2017")) {
                    count[5]++;
                    result[0][5]+=Integer.parseInt(allInfor.get(i).get(7));
                    result[1][5]+=Integer.parseInt(allInfor.get(i).get(8));
                }
                if(month.equals("Jul")&&year.equals("2017")) {
                    count[6]++;
                    result[0][6]+=Integer.parseInt(allInfor.get(i).get(7));
                    result[1][6]+=Integer.parseInt(allInfor.get(i).get(8));
                }
                if(month.equals("Aug")&&year.equals("2017")) {
                    count[7]++;
                    result[0][7]+=Integer.parseInt(allInfor.get(i).get(7));
                    result[1][7]+=Integer.parseInt(allInfor.get(i).get(8));
                }
                if(month.equals("Sep")&&year.equals("2017")) {
                    count[8]++;
                    result[0][8]+=Integer.parseInt(allInfor.get(i).get(7));
                    result[1][8]+=Integer.parseInt(allInfor.get(i).get(8));
                }
                if(month.equals("Oct")&&year.equals("2017")) {
                    count[9]++;
                    result[0][9]+=Integer.parseInt(allInfor.get(i).get(7));
                    result[1][9]+=Integer.parseInt(allInfor.get(i).get(8));
                }
                if(month.equals("Nov")&&year.equals("2017")) {
                    count[10]++;
                    result[0][10]+=Integer.parseInt(allInfor.get(i).get(7));
                    result[1][10]+=Integer.parseInt(allInfor.get(i).get(8));
                }
                if(month.equals("Dec")&&year.equals("2017")) {
                    count[11]++;
                    result[0][11]+=Integer.parseInt(allInfor.get(i).get(7));
                    result[1][11]+=Integer.parseInt(allInfor.get(i).get(8));
                }
                if(month.equals("Jan")&&year.equals("2018")) {
                    count[12]++;
                    result[0][12]+=Integer.parseInt(allInfor.get(i).get(7));
                    result[1][12]+=Integer.parseInt(allInfor.get(i).get(8));
                }
                if(month.equals("Feb")&&year.equals("2018")) {
                    count[13]++;
                    result[0][13]+=Integer.parseInt(allInfor.get(i).get(7));
                    result[1][13]+=Integer.parseInt(allInfor.get(i).get(8));
                }
                if(month.equals("Mar")&&year.equals("2018")) {
                    count[14]++;
                    result[0][14]+=Integer.parseInt(allInfor.get(i).get(7));
                    result[1][14]+=Integer.parseInt(allInfor.get(i).get(8));
                }
                if(month.equals("Apr")&&year.equals("2018")) {
                    count[15]++;
                    result[0][15]+=Integer.parseInt(allInfor.get(i).get(7));
                    result[1][15]+=Integer.parseInt(allInfor.get(i).get(8));
                }
                if(month.equals("May")&&year.equals("2018")) {
                    count[16]++;
                    result[0][16]+=Integer.parseInt(allInfor.get(i).get(7));
                    result[1][16]+=Integer.parseInt(allInfor.get(i).get(8));
                }
                if(month.equals("Jun")&&year.equals("2018")) {
                    count[17]++;
                    result[0][17]+=Integer.parseInt(allInfor.get(i).get(7));
                    result[1][17]+=Integer.parseInt(allInfor.get(i).get(8));
                }
                if(month.equals("Jul")&&year.equals("2018")) {
                    count[18]++;
                    result[0][18]+=Integer.parseInt(allInfor.get(i).get(7));
                    result[1][18]+=Integer.parseInt(allInfor.get(i).get(8));
                }
                if(month.equals("Aug")&&year.equals("2018")) {
                    count[19]++;
                    result[0][19]+=Integer.parseInt(allInfor.get(i).get(7));
                    result[1][19]+=Integer.parseInt(allInfor.get(i).get(8));
                }
                if(month.equals("Sep")&&year.equals("2018")) {
                    count[20]++;
                    result[0][20]+=Integer.parseInt(allInfor.get(i).get(7));
                    result[1][20]+=Integer.parseInt(allInfor.get(i).get(8));
                }
                if(month.equals("Oct")&&year.equals("2018")) {
                    count[21]++;
                    result[0][21]+=Integer.parseInt(allInfor.get(i).get(7));
                    result[1][21]+=Integer.parseInt(allInfor.get(i).get(8));
                }
                if(month.equals("Nov")&&year.equals("2018")) {
                    count[22]++;
                    result[0][22]+=Integer.parseInt(allInfor.get(i).get(7));
                    result[1][22]+=Integer.parseInt(allInfor.get(i).get(8));
                }
                if(month.equals("Dec")&&year.equals("2018")) {
                    count[23]++;
                    result[0][23]+=Integer.parseInt(allInfor.get(i).get(7));
                    result[1][23]+=Integer.parseInt(allInfor.get(i).get(8));
                }
                if(month.equals("Jan")&&year.equals("2019")) {
                    count[24]++;
                    result[0][24]+=Integer.parseInt(allInfor.get(i).get(7));
                    result[1][24]+=Integer.parseInt(allInfor.get(i).get(8));
                }
                if(month.equals("Feb")&&year.equals("2019")) {
                    count[25]++;
                    result[0][25]+=Integer.parseInt(allInfor.get(i).get(7));
                    result[1][25]+=Integer.parseInt(allInfor.get(i).get(8));
                }
                if(month.equals("Mar")&&year.equals("2019")) {
                    count[26]++;
                    result[0][26]+=Integer.parseInt(allInfor.get(i).get(7));
                    result[1][26]+=Integer.parseInt(allInfor.get(i).get(8));
                }


            }
            for(int j=0;j<27;j++) {
                if(count[j]!=0) {
                    result[0][j]=result[0][j]/count[j];
                    result[1][j]=result[1][j]/count[j];
                }
            }

        }catch(Exception e) {
            System.out.println("GitHub月度情绪变化失败");
        }
        return result;
    }


    //冒泡算法(从大到小)
    public String[] bubbleSort(double[] arr, String[] res) {
        for(int i=0;i<arr.length-1;i++){
            for(int j=0;j<arr.length-1-i;j++){
                if(arr[j]<arr[j+1]){
                    double temp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                    String tem=res[j];
                    res[j]=res[j+1];
                    res[j+1]=tem;
                }
            }
        }
        return res;
    }


    /*返回GitHub单个issue内按照评论顺序的评论*/
    public ArrayList<ArrayList<String>> getGIn_order(String url){
        ArrayList<ArrayList<String>> result=new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> issueInfor=dao.getGithubComment(url);//获得了单个issue内的数据
        for(int i=0;i<issueInfor.size();i++) {
            for(int j=0;j<issueInfor.size();j++) {
                if(issueInfor.get(j).get(6).equals(Integer.toString(i))) {
                    result.add(issueInfor.get(j));
                }
            }
        }
        return result;
    }

    public ArrayList<ArrayList<String>> getHisYear(int userid, String url, String time){
        ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
        try {
            ArrayList<ArrayList<String>> info = dao.getHistoryYear(userid, url, time);
            ArrayList<String> temp17 = new ArrayList<String>();
            ArrayList<String> temp18 = new ArrayList<String>();
            ArrayList<String> temp19 = new ArrayList<String>();
            for (int i = 0; i < info.size(); i++) {
                if (i < 10) {
                    temp17.add(info.get(i).get(3));
                } else if (i >= 10 && i < 20) {
                    temp18.add(info.get(i).get(3));
                } else {
                    temp19.add(info.get(i).get(3));
                }
            }
            res.add(temp17);
            res.add(temp18);
            res.add(temp19);
        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }

    public ArrayList<ArrayList<String>> getHisMonth(int userid, String url, String time){
        ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
        try {
            ArrayList<ArrayList<String>> info = dao.getHistoryMonth(userid, url, time);
            ArrayList<String> temp17 = new ArrayList<String>();
            ArrayList<String> temp18 = new ArrayList<String>();
            ArrayList<String> temp19 = new ArrayList<String>();
            for (int i = 0; i < info.size(); i++) {
                if (i < 24) {
                    temp17.add(info.get(i).get(3));
                } else if (i >= 24 && i < 48) {
                    temp18.add(info.get(i).get(3));
                } else {
                    temp19.add(info.get(i).get(3));
                }
            }
            res.add(temp17);
            res.add(temp18);
            res.add(temp19);
        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }

    public static void main(String[] args){
        DealCommentImpl da=new DealCommentImpl();
        ArrayList<ArrayList<String>> res=da.getGMonthTop("https://github.com/TheAlgorithms/C");
        for(int i=0;i<res.size();i++){
            for(int j=0;j<res.get(0).size();j++)
                System.out.println(i+j+res.get(i).get(j));
        }
    }

}

