package expression.parser;

import expression.exceptions.ExpressionParsingException;
import expression.operations.Operation;

import java.util.HashMap;
import java.util.Map;

public class ParsedExp<T> {
    private static final Map<Character, String> SIGN = new HashMap<>() {
        {
            put('*', "*");
            put('/', "/");
            put('-', "-");
            put('+', "+");
            put('m', "mod");
        }
    };
    private static final Map<Character, String> UNARY = new HashMap<>() {
        {
            put('-', "-");
            put('a', "abs");
            put('s', "square");
        }
    };
    private final String expression;
    private int pos;
    public boolean isEmpty() {
        skip();
        return pos >= expression.length();
    }
    public ParsedExp(String stringExpression) {
        expression = stringExpression;
        pos = 0;
    }
    public boolean isUnary () {
        boolean fl = skip();
        if (UNARY.get(expression.charAt(pos)) == null) {
            if (fl) {
                pos--;
            }
        }
        return UNARY.get(expression.charAt(pos)) != null;
    }
    public String getUnary() throws ExpressionParsingException {
        skip();
        if (expression.charAt(pos) == '-') {
            next(1);
            return "-";
        }
        if (pos + 2 < expression.length() && expression.startsWith("abs", pos)) {
            next(3);
            if (expression.charAt(pos) == '(' || skip()) {
                if (expression.charAt(pos) != '(') {
                    pos--;
                }
                return "abs";
            }
            throw new ExpressionParsingException("Incorrect expression, " +
                    "there is no separation between abs and TripleExpression, expected \"(\", or Whitespace");
        }
        if (pos + 5 < expression.length() && expression.startsWith("square", pos)) {
            next(6);
            if (expression.charAt(pos) == '(' || skip()) {
                if (expression.charAt(pos) != '(') {
                    pos--;
                }
                return "square";
            }
            throw new ExpressionParsingException("Incorrect expression, " +
                    "there is no separation between square and TripleExpression, expected \"(\", or Whitespace");
        }
        throw new ExpressionParsingException("Incorrect expression, expected Unary");
    }
    public void next(int x) {
        pos += x;
    }
    private boolean skip() {
        boolean fl = false;
        while (pos < expression.length() && Character.isWhitespace(expression.charAt(pos))) {
            pos++;
            fl = true;
        }
        return fl;
    }
    public String getVariable() {
        skip();
        char c = expression.charAt(pos);
        next(1);
        return String.valueOf(c);
    }
    public T getConst(String sign, Operation<T> operation) throws ExpressionParsingException {
        skip();
        StringBuilder ans = new StringBuilder(sign);
        while (pos < expression.length() && Character.isDigit(expression.charAt(pos))) {
            ans.append(expression.charAt(pos));
            pos++;
        }
        try {
            return operation.parse(String.valueOf(ans));
        } catch (NumberFormatException e) {
            throw new ExpressionParsingException ("Incorrect expression, incorrect number format");
        }
    }
    public String getSign() throws ExpressionParsingException {
        boolean flag = skip();
        char c = expression.charAt(pos);
        if (c == '+') {
            next(1);
            return "+";
        }
        if (c == '-') {
            next(1);
            return "-";
        }
        if (c == '*') {
            next(1);
            return "*";
        }
        if (c == '/') {
            next(1);
            return "/";
        }
        if (pos + 2 < expression.length() && expression.startsWith("mod", pos) && (flag || expression.charAt(pos - 1) == ')')) {
            next(3);
            return "mod";
        }
        throw new ExpressionParsingException("Incorrect expression, expected Sign");
    }
    private boolean isVariable(char c) {
        return c == 'x' || c == 'y' || c == 'z';
    }
    private boolean isSign(char c) {
        return SIGN.get(c) != null;
    }
    public boolean isWhitespace() {
        return Character.isWhitespace(expression.charAt(pos));
    }
    public int checkNext() {
        boolean flag = skip();
        if (pos >= expression.length()) {
            return -1;
        }
        char c = expression.charAt(pos);

        if (c == '(') {
            return 2;
        }
        if (c == ')') {
            return 4;
        }
        if (flag) {
            pos--;
        }
        if (Character.isDigit(c)) {
            return 0;
        }
        if (isVariable(c)) {
            return 1;
        }
        if (isSign(c)) {

            return 3;
        }
        return -1;
    }
}
