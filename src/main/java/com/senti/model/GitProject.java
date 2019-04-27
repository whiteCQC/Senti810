package com.senti.model;

public class GitProject {
    private int gid;
    private String owner;
    private String repo;

    public GitProject(){}

    public GitProject(int gid,String owner,String repo){
        this.gid=gid;
        this.owner=owner;
        this.repo=repo;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }
}
