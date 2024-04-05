package com.example.nlrs_main.NLS;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import java.util.Properties;

public class NaturalLanguageAnalyzer {
    private static StanfordCoreNLP pipeline;

    static {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(props);
    }

    public  int analyzeSentiment(String reviewText) {
        if (reviewText == null || reviewText.trim().isEmpty()) {
            return 5;
        }

        Annotation annotation = pipeline.process(reviewText);
        int totalSentimentScore = 0;
        int totalSentences = 0;

        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
            int sentimentScore = getSentimentScore(sentiment); // Convert sentiment to score
            totalSentimentScore += sentimentScore;
            totalSentences++;
        }


        if (totalSentences == 0) {
            return totalSentimentScore;
        }else {
            return totalSentimentScore/totalSentences;
        }
    }
    private static int getSentimentScore(String sentiment) {
        switch (sentiment) {
            case "Very positive": return 9;
            case "Positive": return 7;
            case "Neutral": return 5;
            case "Negative": return 3;
            case "Very negative": return 1;
            default: return 5; // Considering 'Unknown' sentiments as neutral
        }
    }
}
