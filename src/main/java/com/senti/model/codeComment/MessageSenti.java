package com.senti.model.codeComment;

public class MessageSenti {
    private int gid;
    private double high;
    private double low;
    private int score;
    private String date;
    private String comment;

    public MessageSenti(){}

    public MessageSenti(double high, double low,String date, String comment){
        this.high=high;
        this.low=low;
        this.date=date;
        this.comment=comment;
    }

    public MessageSenti(int gid,double high, double low, int score, String date, String comment) {
        this.gid=gid;
        this.high = high;
        this.low = low;
        this.date = date;
        this.score=score;
        this.comment = comment;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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
