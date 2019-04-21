package com.senti.model;

import java.io.Serializable;

public class GithubUser implements Serializable {
    private static final long serialVersionUID = -6181988876631804508L;
    private String name;
    private String username;
    private String avatar_url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public GithubUser(String name, String username, String avatar_url) {
        this.name = name;
        this.username = username;
        this.avatar_url = avatar_url;
    }
}
