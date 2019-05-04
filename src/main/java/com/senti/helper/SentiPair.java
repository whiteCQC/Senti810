package com.senti.helper;

public class SentiPair {
    private int score;
    private String word;
    private String sentence;

    public SentiPair(int score, String word, String sentence) {
        this.score = score;
        this.word = word;
        this.sentence = sentence;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public SentiPair(int score, String word) {
        this.score = score;
        this.word = word;
    }

    public int getScore() {
        return score;
    }

    public String getWord() {
        return word;
    }

    @Override
    public String toString() {
        return word+"["+score+"], ";

    }
}
