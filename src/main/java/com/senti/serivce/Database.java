package com.senti.serivce;

import java.util.ArrayList;


public interface Database {

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

}
