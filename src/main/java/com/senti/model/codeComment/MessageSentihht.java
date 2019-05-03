package com.senti.model.codeComment;

import java.io.Serializable;
import java.util.List;

public class MessageSentihht extends  MessageSenti implements Serializable {
    private static final long serialVersionUID = -5714175127848493795L;
    private String author;
    private List<String> filechanged;

    public MessageSentihht(double high, double low, String date, String comment) {
        super(high, low, date, comment);

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getFilechanged() {
        return filechanged;
    }

    public void setFilechanged(List<String> filechanged) {
        this.filechanged = filechanged;
    }
}
