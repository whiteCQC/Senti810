package com.senti.model.codeComment;

public class MessageSenti {
    private double high;
    private double low;
    private int count;
    private String date;
    private String comment;

    public MessageSenti(double high, double low, int count, String date, String comment) {
        this.high = high;
        this.low = low;
        this.count = count;
        this.date = date;
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
