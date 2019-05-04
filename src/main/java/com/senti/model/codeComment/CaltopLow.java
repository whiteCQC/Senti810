package com.senti.model.codeComment;

public class CaltopLow implements Comparable<CaltopLow>{
    private int countLow;
    private int totalLow;
    private String name;

    public CaltopLow(String name,int low){
        this.name=name;
        countLow=1;
        totalLow=low;
    }

    public void add(int low){
        countLow++;
        totalLow+=low;
    }

    public double getScoreLow() {
        if(this.countLow!=0)
            return this.totalLow/(double)this.countLow;
        else
            return 0;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(CaltopLow o) {
        return Double.compare(this.getScoreLow(),o.getScoreLow());
    }
}
