package com.epam.example;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Diana Aimbetova on 4/3/2020
 **/

public class Regexr {
    private List doRegex(String text, String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        List results = new ArrayList<String>();
        while (matcher.find()) {
            results.add(matcher.group());
        }
        return results;
    }
    public List splitWord(String text){
        return doRegex(text,"\\w+");
    }
    public List splitSentence(String text){
        return doRegex(text,".*?\\.|.*?\\!|.*?\\?(?= [A-Z]|$)");
    }
    public List getEmail(String text){
        return doRegex(text,"\\S+@\\S+\\.\\w+");
    }
    public List getDate(String text){
        return doRegex(text,"((Jan(uary)?|Feb(ruary)?|Mar(ch)?|Apr(il)?|May|Jun(e)?|Jul(y)?|Aug(ust)?|Sep(tember)?|Oct(ober)?|Nov(ember)?|Dec(ember)?)|\\d{2,4})(\\/|-|\\s*)?((Jan(uary)?|Feb(ruary)?|Mar(ch)?|Apr(il)?|May|Jun(e)?|Jul(y)?|Aug(ust)?|Sep(tember)?|Oct(ober)?|Nov(ember)?|Dec(ember)?)|\\d{2,4})(\\/|-|\\s*|,\\s?)?(\\d{2,4})");
    }

}
