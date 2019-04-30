package com.senti.serivce;

import java.util.ArrayList;

public interface DealComment {

    /*输入的网址为本地已下载的，则直接从数据库调取数据，无需重新爬取
     * 情绪值低于-3的issue*/
    public ArrayList<String> getLow(String name);

    /*相应的数据库和issueNo对应的情绪值随时间变化需要的数据 */
    public ArrayList<ArrayList<String>> sentiChange(String name, int issueNo);

    /*处理GitHub爬取的数据（情绪值低于-2.5的issue）*/
    public ArrayList<String> getGitLow(String url);

    /*github单个issue内的情绪变化(此处url为具体到某个页面，格式如https://github.com/TheAlgorithms/Java/issues/1*/
    public ArrayList<ArrayList<String>> sentiChangeGit(String url);

    /*年度好坏top5(本地已下好的数据库) 仅用于计算出数据，不调用*/
    public ArrayList<ArrayList<String>> getLYearTop(String name);

    /*17,18,19年的年度top5*/
    public ArrayList<ArrayList<String>> getLYdata(String name);

    /*月度好坏top1(本地已下好的数据库*/
    public ArrayList<ArrayList<String>> getLMonthTop(String name);

    /*本地数据库的2017.1到2019.3的情绪变化*/
    public double[][] getLChange(String name);

    /*年度好坏top5 github*/
    public ArrayList<ArrayList<String>> getGYearTop(String url);

    /*月度好坏top1 github*/
    public ArrayList<ArrayList<String>> getGMonthTop(String url);

    /*Github 2017.1到2019.3的情绪变化*/
    public double[][] getGChange(String url);

    /*返回GITHUB单个issue内按照评论顺序的评论*/
    public ArrayList<ArrayList<String>> getGIn_order(String url);

    public ArrayList<ArrayList<String>> getHisYear(int userid, String url, String time);

    public ArrayList<ArrayList<String>> getHisMonth(int userid, String url, String time);

}

