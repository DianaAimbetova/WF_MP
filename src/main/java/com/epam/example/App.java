package com.epam.example;

import java.util.List;

/**
 * Created by Diana Aimbetova on 4/3/2020
 **/

public class App {
    public static void main(String[] args) {
        String TEXT1 = "it is a new day. Java Regexp text.";
        String TEXT2 = "it is a new day. 1991-12-01 epam@wf.com Java Regexp textepam@wf.com Java@Regexp";
        String TEXT3 = "it is a January 02, 2000 new day. Dec 01, 1991 epam@wf.com Java NovRegexp t January 05 2001 extepam@wf.com Java@Regexp";

        Regexr regexr = new Regexr();
        System.out.println("================ SPLIT BY WORDS: ======================");
        List results = regexr.splitWord(TEXT1);
        System.out.println(results);
        results = regexr.splitWord(TEXT2);
        System.out.println(results);
        results = regexr.splitWord(TEXT3);
        System.out.println(results);

        System.out.println("================ SPLIT BY SENTENCES: ======================");
        results = regexr.splitSentence(TEXT1);
        System.out.println(results);
        results = regexr.splitSentence(TEXT2);
        System.out.println(results);
        results = regexr.splitSentence(TEXT3);
        System.out.println(results);

        System.out.println("================ GET EMAILS: ======================");
        results = regexr.getEmail(TEXT1);
        System.out.println(results);
        results = regexr.getEmail(TEXT2);
        System.out.println(results);
        results = regexr.getEmail(TEXT3);
        System.out.println(results);

        System.out.println("================ GET DATES: ======================");
        results = regexr.getDate(TEXT1);
        System.out.println(results);
        results = regexr.getDate(TEXT2);
        System.out.println(results);
        results = regexr.getDate(TEXT3);
        System.out.println(results);
    }
}
