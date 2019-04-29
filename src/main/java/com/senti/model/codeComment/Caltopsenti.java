package com.senti.model.codeComment;

public class Caltopsenti {
    private int countHigh;
    private int countLow;

    private int totalHigh;
    private int totalLow;

    private String name;

    public Caltopsenti(String name,int score){
        this.name=name;
        if(score>0){
            countHigh=1;
            countLow=0;
            totalHigh=score;
            totalLow=0;

        }else{
            countHigh=0;
            countLow=1;
            totalHigh=0;
            totalLow=score;
        }

    }

    public void add(int score){
        if(score>0){
            countHigh++;
            totalHigh+=score;
        }else{
            countLow++;
            totalLow+=score;
        }
    }

    public double getScoreHigh() {
        if(this.countHigh!=0)
            return this.totalHigh/(double)this.countHigh;
        else
            return 0;
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
}
