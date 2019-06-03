package com.senti.model.codeComment;

/**
 * 代码模块
 * Commit的中涉及到的类（现有的）
 */
public class relatedClasses {
    private int gid;//项目ID
    private String sha;//Commit的SHA
    private String name;//类的路径

    public relatedClasses(int gid, String sha, String name) {
        this.gid = gid;
        this.sha = sha;
        this.name = name;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
