package repl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ast.And;
import ast.Constant;
import ast.Equals;
import ast.Imply;
import ast.Not;
import ast.Or;
import ast.Proposition;
import ast.Variable;
import ast.Xor;
import exceptions.ParsingException;
import imperatives.Forget;
import imperatives.Imperative;
import imperatives.Let;
import imperatives.NegationNormalForm;
import imperatives.Print;
import imperatives.Solve;

public class Parser {

    private Scanner scan;

    /**
     * Construct the parse tree of an input string.
     * 
     * @param string:
     *            the string to interpret.
     * @return a Program.
     * @throws ParsingException:
     *             if the string has malformed syntax.
     */
    public Imperative interpret(String string) throws ParsingException {
        try {
            scan = new Scanner(string);
            scan.useDelimiter("\\s+|(?=[{}(),;\\$])|(?<=[{}(),;\\$])");
            return parseImperative();
        } finally {
            if (scan != null)
                scan.close();
            scan = null;
        }
    }

    public Imperative parseImperative() throws ParsingException {
        if (scan.hasNext("forget")) return parseForget();
        else if (scan.hasNext("let")) return parseLet();
        else if (scan.hasNext("nnf")) return parseNNF();
        else if (scan.hasNext("print")) return parsePrint();
        else if (scan.hasNext("solve")) return parseSolve();
        else return new Print(parseProposition());
    }

    private Imperative parseNNF() throws ParsingException {
    	if (!gobble("nnf"))
    		fail("NegationNormalForm imperative must start with \"nnf\".");
    	Proposition prop = parseProposition();
    	return new NegationNormalForm(prop);
	}

	private Forget parseForget() throws ParsingException {
    	if (!gobble("forget"))
    		fail("Forget imperative must start with \"forget\".");
    	String name = parseIdentifier();
    	return new Forget(name);
    }
    
    private Solve parseSolve() throws ParsingException {
        if (!gobble("solve"))
            fail("Solve imperative must start with \"solve\".");
        Proposition prop = parseProposition();
        return new Solve(prop);
    }

    private Print parsePrint() throws ParsingException {
        if (!gobble("print"))
            fail("Print imperative must start with \"print\".");
        Proposition prop = parseProposition();
        return new Print(prop);
    }
    
    private Let parseLet() throws ParsingException {
    	if (!gobble("let"))
    		fail("Let imperative must start with \"let\".");
    	String name = parseIdentifier();
    	if (!gobble("="))
    		fail("Let imperative must have `=` separating `let` and the definition.");
    	Proposition prop = parseProposition();
    	return new Let(name, prop);
    }

    private String parseIdentifier() throws ParsingException {
    	String next = scan.next();
    	for (char ch: next.toCharArray()) {
    		if (!Character.isLetter(ch)) {
    			throw new ParsingException("Identifiers can only have letters.");
    		}
    	}
    	return next;
    }
    
    private Proposition parseProposition() throws ParsingException {
        String string = scan.nextLine();
        ShuntingYard shuntingYard = new ShuntingYard(Utils.TokenRegex(), Utils.PrecedenceMap());
        List<String> tokens = shuntingYard.infixToPostfix(string);

        Map<String, Integer> arity = Utils.ArityMap();
        Stack<Proposition> stack = new Stack<>();

        for (String token : tokens) {
            if (Utils.IsOperator(token)) {
                int opArity = arity.get(token);
                List<Proposition> args = new ArrayList<Proposition>(arity.get(token));
                for (int i = 0; i < opArity; i++) {
                	args.add(stack.pop());
                }
                Collections.reverse(args); // we're in postfix, so reverse args to get in the correct order.
                Proposition prop = makeProposition(token, args);
                stack.push(prop);
            } else if (token.equals("true")) {
                stack.push(Constant.True());
            } else if (token.equals("false")) {
                stack.push(Constant.False());
            } else {
                stack.push(new Variable(token));
            }
        }

        Proposition prop = stack.pop();
        if (stack.size() > 0) {
            throw new ParsingException("Bad number of operators when parsing proposition.");
        }
        return prop;
    }

    private Proposition makeProposition(String operator, List<Proposition> args) throws ParsingException {
        switch (operator) {
        case "and":
            return new And(args.get(0), args.get(1));
        case "or":
            return new Or(args.get(0), args.get(1));
        case "not":
            return new Not(args.get(0));
        case "xor":
            return new Xor(args.get(0), args.get(1));
        case "eq":
            return new Equals(args.get(0), args.get(1));
        case "implies":
            return new Imply(args.get(0), args.get(1));
        default:
            throw new ParsingException("Unknown operator \"" + operator + "\"");
        }
    }

    protected void fail(String msg) throws ParsingException {
        throw new ParsingException(msg);
    }

    protected boolean gobble(String p) {
        if (scan.hasNext(p)) {
            scan.next();
            return true;
        } else {
            return false;
        }
    }

}
