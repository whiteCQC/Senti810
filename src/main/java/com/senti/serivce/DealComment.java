package com.senti.serivce;

import java.util.ArrayList;

public interface DealComment {

    /*年度好坏top5(本地已下好的数据库) 仅用于计算出数据，不调用*/
    public ArrayList<ArrayList<String>> getLYearTop(String name);

    /*17,18,19年的年度top5*/
    public ArrayList<ArrayList<String>> getLYdata(String name);

    /*月度好坏top1(本地已下好的数据库*/
    public ArrayList<ArrayList<String>> getLMonthTop(String name);

    /*17,18,19月度top1*/
    public ArrayList<ArrayList<String>> getLMdata(String name);

    /*本地数据库的2017.1到2019.3的情绪变化*/
    public ArrayList<Object> getLChange(String name);

    /*年度好坏top5 github*/
    public ArrayList<Object> getGYearTop(String url);

    /*月度好坏top1 github*/
    public ArrayList<Object> getGMonthTop(String url);

    /*Github 2017.1到2019.3的情绪变化*/
    public ArrayList<Object> getGChange(String url);

    /*返回GITHUB单个issue内按照评论顺序的评论*/
    public ArrayList<ArrayList<String>> getGIn_order(String url);

    public ArrayList<ArrayList<String>> getHisYear(int userid, String url, String time);

    public ArrayList<ArrayList<String>> getHisMonth(int userid, String url, String time);

}

