package com.senti.model.codeComment;

/**
 *  代码模块
 *  Commit的message和代码注释结合，正面情绪占比
 */
public class RatioHigh implements Comparable<RatioHigh>{
    private String name;//类路径
    private int total;//
    private int count;

    public RatioHigh(String name) {
        this.name = name;
        this.total=0;
        this.count=0;
    }

    public String getName() {
        return name;
    }

    public void add(int score){
        if(score>0)
            this.count++;
        this.total++;
    }

    public String getRatioString(){//百分比格式
        double res=100*((double)this.count)/this.total;

        return String.valueOf(f(res))+"%";
    }

    public double getRatio(){
        double res=((double)this.count)/this.total;

        return res;
    }

    @Override
    public int compareTo(RatioHigh o) {//由高到低
        return Double.compare(o.getRatio(),this.getRatio());
    }

    private double f(double i) {
        return Double.parseDouble(String.format("%.2f", i));
    }
}
