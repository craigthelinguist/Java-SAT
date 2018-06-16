package repl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.ParsingException;

public class Utils {
    
    /**
     * Return a Matcher object wrapped around the given string which spits out the tokens as they appear.
     * @param input: string to read.
     * @return Matcher object.
     */
    public static Matcher OperatorMatcher (String input) {
        Pattern pat = Pattern.compile("[A-Za-z]+|->|[&\\*\\+\\^\\~\\!\\|\\=]{1}|[\\(\\)]{1}");
        return pat.matcher(input);
    }
    
    public static String TokenRegex() {
        return "[A-Za-z]+|->|[&\\*\\+\\^\\~\\!\\|\\=]{1}|[\\(\\)]{1}";
    }
    
    public static Map<String, Integer> PrecedenceMap() {
        return precedence;
    }
    
    public static Map<String, Integer> ArityMap() {
        return arity;
    }
    
    public static boolean IsOperator(String operator) {
        return PrecedenceMap().containsKey(operator);
    }
    
    private static Map<String, Integer> arity, precedence;
    
    static {
        arity = new HashMap<>();
        arity.put("not", 1);
        arity.put("and", 2);
        arity.put("xor", 2);
        arity.put("or", 2);
        arity.put("implies", 2);
        arity.put("eq", 2);
    }
    
    static {
        precedence = new HashMap<>();
        precedence.put("not", 100);
        precedence.put("and", 40);
        precedence.put("xor", 40);
        precedence.put("or", 30);
        precedence.put("implies", 20);
        precedence.put("eq", 0);
    }
    
    
    
}
