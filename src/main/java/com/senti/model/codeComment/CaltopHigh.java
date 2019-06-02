package com.senti.model.codeComment;

/**
 * 代码模块
 * 用于计算正面情绪平均值最高的类
 */
public class CaltopHigh implements Comparable<CaltopHigh>{
    private int countHigh;//总数
    private int totalHigh;//总值
    private String name;//类的文件路径

    public CaltopHigh(String name,int high){
        this.name=name;
        countHigh=1;
        totalHigh=high;

    }

    public void add(int high){//用于在初始化之后继续添加
        countHigh++;
        totalHigh+=high;

    }

    public double getScoreHigh() {//获得对应总数和总值的平均值，将会取两位小数
        if(this.countHigh!=0)
            return f(this.totalHigh/(double)this.countHigh);
        else
            return 0;
    }
    private double f(double i) {
        return Double.parseDouble(String.format("%.2f", i));
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(CaltopHigh o) {//正面情绪，从大到小排序
        return Double.compare(o.getScoreHigh(),this.getScoreHigh());
    }
}
