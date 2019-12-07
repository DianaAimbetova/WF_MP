package utils;

import java.math.BigInteger;
import java.text.Normalizer;
import java.util.List;

public class Utils {

    public static String concatenateWords(List<String> words) {
        String result = "";
        for (String word : words) {
            result = result.concat(word);
        }
        return result;
    }

    public static String computeFactorial(int n) {
        if (n < 0)
            throw new IllegalArgumentException();
        BigInteger fact = new BigInteger("1");
        for (int i = 1; i <= n; i++) {
            fact = fact.multiply(new BigInteger(i + ""));
        }
        return fact.toString();
    }

    public static String normalizeWord(String word){
        String result = Normalizer.normalize(word, Normalizer.Form.NFD);
        return result;
    }

}
