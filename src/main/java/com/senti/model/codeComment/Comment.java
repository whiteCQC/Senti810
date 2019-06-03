package com.senti.model.codeComment;

import java.sql.Timestamp;

/**
 *  代码模块
 *  代码注释
 */
public class Comment {
    private String comment;//注释
    private String className;//类路径
    private Timestamp date;//修改日期
    private String type;//增或删

    public Comment(String comment, String className, Timestamp date, String type) {
        this.comment=comment;
        this.className=className;
        this.date=date;
        this.type=type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
