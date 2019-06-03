package com.senti.model.codeComment;

import java.sql.Timestamp;

/**
 * 代码模块
 * Commit信息
 */
public class Commits {
    private String sha;//Commit的SHA
    private String author;//作者
    private Timestamp date;//修改日期
    private String message;//提交记录message

    public Commits(String sha, String author, String date, String message) {
        this.sha = sha;
        this.author = author;
        this.date = Timestamp.valueOf(date);
        this.message = message;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
