package repl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.ParsingException;

public class ShuntingYard {
    
    private Pattern tokenPattern;
    private Map<String, Integer> precedence;
    
    /**
     * Construct a new ShuntingYard object.
     * @param operatorRegex: operators and brackets should match against this regex.
     * @param arity: a map from operators to how many arguments they take.
     * @param precedence: a map from operators to their precedence.
     */
    public ShuntingYard(String tokenRegex, Map<String, Integer> precedence)
    throws ParsingException {
        this.tokenPattern = Pattern.compile(tokenRegex);
        this.precedence = precedence;
    }
    
    private boolean isOperator(String operator) {
        return this.precedence.containsKey(operator);
    }
    
    private int precedence(String operator) {
        return this.precedence.get(operator);
    }
    
    public List<String> infixToPostfix(String infix) {
        
        Stack<String> stack = new Stack<>();
        List<String> output = new ArrayList<>();
        infix = infix + ")";
        stack.push("(");
        Matcher matcher = this.tokenPattern.matcher(infix);
        
        while (matcher.find()) {
            String token = matcher.group();
            if (this.isOperator(token)) {
                int precedence = this.precedence(token);
                while (isOperator(stack.peek()) && precedence(stack.peek()) >= precedence) {
                    output.add(stack.pop());
                }
                stack.push(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
               while (!stack.peek().equals("(")) {
                   output.add(stack.pop());
               }
               stack.pop();
            } else {
                output.add(token);
            }
        }
        return output;
    }
    
    public List<String> infixToPrefix(String infix) {
        
        Stack<String> stack = new Stack<>();
        List<String> output = new ArrayList<>();
        infix = infix + "(";
        stack.push(")");
        Matcher matcher = this.tokenPattern.matcher(infix);
        
        while (matcher.find()) {
            String token = matcher.group();
            if (this.isOperator(token)) {
                int precedence = this.precedence(token);
                while (isOperator(stack.peek()) && precedence(stack.peek()) > precedence) {
                    output.add(stack.pop());
                }
                stack.push(token);
            } else if (token.equals(")")) {
                stack.push(token);
            } else if (token.equals("(")) {
               while (!stack.peek().equals(")")) {
                   output.add(stack.pop());
               }
               stack.pop();
            } else {
                output.add(token);
            }
        }
        Collections.reverse(output);
        return output;
    }
    
}
