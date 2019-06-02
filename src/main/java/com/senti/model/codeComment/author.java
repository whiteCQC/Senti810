package com.senti.model.codeComment;

import java.io.Serializable;
import java.util.Objects;

public class author implements Serializable {
    private static final long serialVersionUID = 2808303938976807952L;
    private String name;
    private String description;
    private  int star;
    private  int activity;




    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        author author = (author) o;
        return Objects.equals(name, author.name);
    }

    public author() {
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "{" +
                "\"name\":\"" + name + '\"' +
                ", \"description\":\"" + description + '\"' +
                ", \"star\":" + star +
                ", \"activity\":" + activity +
                '}';
    }
}
