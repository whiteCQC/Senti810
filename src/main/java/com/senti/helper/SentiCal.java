package com.senti.helper;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import uk.ac.wlv.sentistrength.SentiStrength;

import java.util.Properties;

public class SentiCal {
    public int stanford_analyse(String line) {
        if(line.length()==0)
            return 2;

        //String senti;
        int sentiment = 2;
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        Annotation annotation = pipeline.process(line);
        //CoreMap sen = annotation.get(CoreAnnotations.SentencesAnnotation.class).get(0);
        //senti = sen.get(SentimentCoreAnnotations.SentimentClass.class);


        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {

            //得到parse tree
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);

            //得到运用rnn的entiment score
            sentiment = RNNCoreAnnotations.getPredictedClass(tree);

            //得到sentiment
//             senti = sentence.get(SentimentCoreAnnotations.SentimentClass.class);


//            SimpleMatrix sm = RNNCoreAnnotations.getPredictions(tree);


            //System.out.println(senti);

//            System.out.println(sentiment);



//            System.out.println("very negative " + sm.get(0));
//            System.out.println("negative " + sm.get(1));
//            System.out.println("neutral " + sm.get(2));
//            System.out.println("positive " + sm.get(3));
//            System.out.println("very positive " + sm.get(4));

        }
        return sentiment;
    }

    public int[] senti_strength(String line) {
        if(line.length()==0)
            return new int[] {0,0};

        SentiStrength sentiStrength = new SentiStrength();
        String ssthInitialisation[] = {"sentidata", "F:/F/Git/SentStrength_Data/", "explain"};
        sentiStrength.initialise(ssthInitialisation); //Initialise
        //can now calculate sentiment scores quickly without having to initialise again
        String[] s=sentiStrength.computeSentimentScores(line).split(" ");
        int high=Integer.parseInt(s[0]);
        int low=Integer.parseInt(s[1]);
        return new int[] {high,low};
    }

}
