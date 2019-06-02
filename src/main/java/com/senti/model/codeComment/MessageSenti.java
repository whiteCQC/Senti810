package com.senti.model.codeComment;

/**
 * 代码模块
 * Commit的Message的情绪
 */
public class MessageSenti {
    private int gid;//项目ID
    private double high;//正面情绪
    private double low;//负面情绪
    private String date;//commit提交日期
    private String comment;//Commit的message
    private String sha;//Commit的SHA

    public MessageSenti(){}

    public MessageSenti(double high, double low,String date, String comment){
        this.high=high;
        this.low=low;
        this.date=date;
        this.comment=comment;
    }

    public MessageSenti(int gid,double high, double low, String date, String comment,String sha) {
        this.gid=gid;
        this.high = high;
        this.low = low;
        this.date = date;
        this.comment = comment;
        this.sha=sha;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public int getGid() {
        return gid;
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
