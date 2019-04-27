package com.senti.serivce.Impl;


import java.util.ArrayList;

import org.springframework.stereotype.Service;
import uk.ac.wlv.sentistrength.SentiStrength;;
@Service
public class SentiStrengthAnalyse {
    public int[] getScore(String text){
        SentiStrength sentiStrength = new SentiStrength();
        String ssthInitialisation[] = {"sentidata", "D:/SentStrength_Data/", "explain"};
        sentiStrength.initialise(ssthInitialisation); //Initialise
        String result=sentiStrength.computeSentimentScores(text);
        int posScore=Integer.parseInt(result.substring(0,1));
        int negScore=Integer.parseInt(result.substring(2,4));
        int[] score= new int[2];
        score[0]=posScore;
        score[1]=negScore;
        return score;

    }
}




