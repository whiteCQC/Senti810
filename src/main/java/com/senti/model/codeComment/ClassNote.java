package com.senti.model.codeComment;

import java.sql.Timestamp;

/**
 *  代码模块
 *  Commit代码模块中对具体类进行留言的实体
 */
public class ClassNote {
    private int userid;//用户ID
    private int gid;//项目的ID
    private String classname;//类的文件路径
    private String note;//留言内容
    private String time;//留言时间

    public ClassNote(){

    }
    public ClassNote(int userid,int gid,String className,String note,String time){
        this.userid=userid;
        this.gid=gid;
        this.classname=className;
        this.note=note;
        this.time=time;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }
}
