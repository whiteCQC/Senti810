package com.senti.model.codeComment;

import java.io.Serializable;

public class ClassSenti implements Serializable {
    private int gid;
    private String name;
    private double high;
    private double low;
    private String date;
    private String comment;

    public ClassSenti(){}

    public ClassSenti(int gid,String name, double high, double low, String date,String comment) {
        this.name = name;
        this.high = high;
        this.low = low;
        this.date = date;
        this.comment=comment;
        this.gid=gid;
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
