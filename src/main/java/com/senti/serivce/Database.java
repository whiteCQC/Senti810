package com.senti.serivce;

import java.util.ArrayList;


public interface Database {

    public void connSQL();
    public void deconnSQL();
    //通过输入的网址来获取derby数据库中数据(限制用户输入)
    public ArrayList<ArrayList<String>> getDerbyComment(int index);

    public ArrayList<ArrayList<String>> getDroolsComment(int index);

    public ArrayList<ArrayList<String>> getGroovyComment(int index);

    public ArrayList<ArrayList<String>> getIspnComment(int index);

    public ArrayList<ArrayList<String>> getMngComment(int index);

    public ArrayList<ArrayList<String>> getPigComment(int index);

    public ArrayList<ArrayList<String>> getJbseamComment(int index);

    public ArrayList<ArrayList<String>> getGithubComment(String url);

    public ArrayList<ArrayList<String>> getSameTypeIssue(String type);

    public ArrayList<ArrayList<String>> getAllComment(String name);//name为DERBY等等

    public boolean update(String sql);

    public ArrayList<ArrayList<String>> getAllGitComment(String type);//输入网址的所有issues

    /*返回Derby等数据库单个issue内按照评论顺序的评论*/
    public ArrayList<ArrayList<String>> getLIn_order(String name, int issueNo);

    //用户信息
    public ArrayList<String> getUser(String name);

    //更新历史搜索表
    public boolean updatehistory(int userid, String url, String time);

    //更新历史yeartop表
    public boolean updateYear(int userid, String url, String time, String yeartop, int number, String description);

    //更新历史monthtop表
    public boolean updateMonth(int userid, String url, String time, String monthtop, int number, String description);

    //查找历史记录
    public ArrayList<ArrayList<String>> getHistory(int userid);

    //查找历史年度
    public ArrayList<ArrayList<String>> getHistoryYear(int userid, String url, String time);

    //查找历史月度
    public ArrayList<ArrayList<String>> getHistoryMonth(int userid, String url, String time);

    //查找月度图评论
    public ArrayList<String> getHistoryComment(int userid, String url, String time);

    //插入monthchart的评论表
    public boolean insertHistoryComment(int userid, String url, String time, String comment, String commenttime);

    //更新monthchart的评论表
    public boolean updateHistoryComment(int userid, String url, String time, String comment, String commenttime);

    //查找近期记录
    public ArrayList<ArrayList<String>> getRecent(int userid);

}
