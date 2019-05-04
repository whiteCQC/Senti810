package com.senti.model.codeComment;

public class CaltopHigh implements Comparable<CaltopHigh>{
    private int countHigh;
    private int totalHigh;
    private String name;

    public CaltopHigh(String name,int high){
        this.name=name;
        countHigh=1;
        totalHigh=high;

    }

    public void add(int high){
        countHigh++;
        totalHigh+=high;

    }

    public double getScoreHigh() {
        if(this.countHigh!=0)
            return this.totalHigh/(double)this.countHigh;
        else
            return 0;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(CaltopHigh o) {
        return Double.compare(o.getScoreHigh(),this.getScoreHigh());
    }
}
