package com.senti.model.codeComment;

import java.io.Serializable;

public class ClassSenti implements Serializable {
    private String name;
    private double high;
    private double low;
    private String date;

    public ClassSenti(String name, double high, double low, String date) {
        this.name = name;
        this.high = high;
        this.low = low;
        this.date = date;
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
