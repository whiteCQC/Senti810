package com.senti.model.codeComment;

/**
 * 代码模块
 * 将Commit的Message和代码注释拼接计算的情绪
 */
public class CombineSenti {
    private int gid;//项目ID
    private int high;//正面情绪
    private int low;//负面情绪
    private String date;//修改日期
    private String name;//类的路径

    public CombineSenti(){}

    public CombineSenti(int gid, int high, int low, String date, String name) {
        this.gid = gid;
        this.high = high;
        this.low = low;
        this.date = date;
        this.name = name;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
