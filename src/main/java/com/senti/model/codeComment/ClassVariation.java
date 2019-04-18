package com.senti.model.codeComment;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class ClassVariation implements Serializable {
    private static final long serialVersionUID = 1L;
    private String ClassName;
    private Timestamp date;
    private List<String> commentAdd;
    private List<String> commentDelete;

    public ClassVariation(String ClassName, Timestamp date, List<String> ca, List<String> cd) {
        this.ClassName = ClassName;
        this.date = date;
        this.commentAdd = ca;
        this.commentDelete = cd;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public List<String> getCommentAdd() {
        return commentAdd;
    }

    public void setCommentAdd(List<String> commentAdd) {
        this.commentAdd = commentAdd;
    }

    public List<String> getCommentDelete() {
        return commentDelete;
    }

    public void setCommentDelete(List<String> commentDelete) {
        this.commentDelete = commentDelete;
    }
}
