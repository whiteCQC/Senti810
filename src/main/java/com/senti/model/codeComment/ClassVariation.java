package com.senti.model.codeComment;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 *  代码模块
 *  版本变更的Commit中类的信息
 */
public class ClassVariation{
    private String ClassName;//类的具体路径
    private Timestamp date;//变更时间
    private List<String> commentAdd;//添加的注释
    private List<String> commentDelete;//删除的注释
    private String commit;//该次Commit的message
    private String sha;//commit的SHA

    public ClassVariation(String className, Timestamp date, List<String> commentAdd, List<String> commentDelete, String commit,String sha) {
        ClassName = className;
        this.date = date;
        this.commentAdd = commentAdd;
        this.commentDelete = commentDelete;
        this.commit = commit;
        this.sha=sha;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
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

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }
}
