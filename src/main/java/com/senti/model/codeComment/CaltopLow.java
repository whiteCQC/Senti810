package com.senti.model.codeComment;

/**
 * 代码模块
 * 用于计算负面情绪平均值（绝对值）最高的类
 */
public class CaltopLow implements Comparable<CaltopLow>{
    private int countLow;//总数
    private int totalLow;//总值
    private String name;//类的文件路径

    public CaltopLow(String name,int low){
        this.name=name;
        countLow=1;
        totalLow=low;
    }

    public void add(int low){//初始化后增加数据
        countLow++;
        totalLow+=low;
    }

    public double getScoreLow() {//获得平均值，留两位小数
        if(this.countLow!=0)
            return f(this.totalLow/(double)this.countLow);
        else
            return 0;
    }

    public String getName() {
        return name;
    }

    private double f(double i) {
        return Double.parseDouble(String.format("%.2f", i));
    }

    @Override
    public int compareTo(CaltopLow o) {//从小到大排序，获得负面情绪最高的
        return Double.compare(this.getScoreLow(),o.getScoreLow());
    }
}
