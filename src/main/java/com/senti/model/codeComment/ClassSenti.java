package com.senti.model.codeComment;

import java.io.Serializable;

/**
 *  代码模块
 *  具体类的每次有注释变化的情绪
 */
public class ClassSenti implements Serializable {
    private int gid;//项目ID
    private String name;//类的路径
    private double high;//正面情绪值
    private double low;//负面
    private String date;//注释变更的版本日期
    private String comment;//变更的注释
    private String sha;//Commit的SHA

    public ClassSenti(){}

    public ClassSenti(int gid,String name, double high, double low, String date,String comment,String sha) {
        this.name = name;
        this.high = high;
        this.low = low;
        this.date = date;
        this.comment=comment;
        this.gid=gid;
        this.sha=sha;
    }

    public int getGid() {
        return gid;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
