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


    /*年度好坏top5(本地已下好的数据库)统一以2015起步到2018*/
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

    /*月度好坏top1(本地已下好的数据库15年到19年*/
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

    /*年度好坏top5 github(如果issue的数量太少，则返回空数组)*/
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

            }else {
                result=issueWeb;
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

    public static void main(String[] args){
        DealCommentImpl da=new DealCommentImpl();
        ArrayList<ArrayList<String>> res=da.getGMonthTop("https://github.com/TheAlgorithms/C");
        for(int i=0;i<res.size();i++){
            for(int j=0;j<res.get(0).size();j++)
            System.out.println(i+j+res.get(i).get(j));
        }
    }

}

