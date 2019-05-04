package com.senti.helper;

import uk.ac.wlv.sentistrength.SentiStrength;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SentiStrengthUtilhht {
    private static SentiStrength sentiStrength1=new SentiStrength();
    private static SentiStrength sentiStrength2=new SentiStrength();
    private static final String SSInitialisation2[]={"sentidata","SentStrength_Data//"};
    private static final String SSInitialisation1[]={"sentidata","SentStrength_Data/","explain"};
    private SentiStrengthUtilhht(){

    }



    static {
        sentiStrength2.initialise(SSInitialisation2);
        sentiStrength1.initialise(SSInitialisation1);

    }

    public static List<Integer> newway(String str){
        List<SentiPair> lists=analyzeKeywords(str);
        int a=0,b=0;
        Set<String> words=new HashSet<>();
        for (SentiPair s:lists){
            if (words.contains(s.getWord())) continue;
            words.add(s.getWord());
            if (s.getScore()>0) a++;
            else b++;


        }
        List<Integer> re=new ArrayList();
        re.add(a);
        re.add(b);

        return re;


    }


    public static List<SentiPair> analyzeKeywords(String str){
        String ana_result=analyzeWithExplain(str);
        String word="";

        int keyword=0;
        int minus=1;
        int score=0;
        List<SentiPair> result=new ArrayList<>();

        for (char c: ana_result.toCharArray()){

           if((c<='z' && c>='a')  ||   (c<='Z'  && c>='A')) {word+=c; continue;}




                if (c=='[') {keyword=1; continue;}
                if (keyword==1 && c=='-') {minus=-1; continue;}
                if (keyword==1 && c<='5' && c>='1') { score=c-'0';continue;}

                if (c==']' && keyword==1 && score!=0) {

                    SentiPair sentiPair=new SentiPair(score*minus,word);
                    result.add(sentiPair);
                    minus=1;
                    score=0;
                    keyword=0; continue;}
            if (keyword==1 ) { keyword=0; continue;}

            word="";


        }







        return result;
    }




    public static List<Integer> analyzeWithoutExplain(String str) {

        String[] strings=sentiStrength2.computeSentimentScores(str).split(" ");
        Integer i1=Integer.parseInt(strings[0]);
        Integer i2=Integer.parseInt(strings[1]);
        List<Integer> result=new ArrayList<>();
        result.add(i1);
        result.add(i2);
        result.add(i2+i1);
        return result;
    }
    public static int analyzeSum(String str) {

        String[] strings=sentiStrength2.computeSentimentScores(str).split(" ");
        Integer i1=Integer.parseInt(strings[0]);
        Integer i2=Integer.parseInt(strings[1]);
        return i1+i2;
    }

    public static String analyzeWithExplain(String str) {

        return sentiStrength1.computeSentimentScores(str);

    }


}
