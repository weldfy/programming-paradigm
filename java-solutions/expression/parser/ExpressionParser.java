package expression.parser;

import expression.*;
import expression.exceptions.*;
import expression.operations.Operation;

import java.util.*;

public class ExpressionParser<T> implements TripleParser<T> {
    private final Operation<T> operation;
    public ExpressionParser(final Operation<T> operation) {
        this.operation = operation;
    }
    private ParsedExp<T> expression;
    private static final Map<String, Integer> LEVELS = new HashMap<>() {
        {
            put("*", 3);
            put("/", 3);
            put("-", 2);
            put("+", 2);
            put("set", 1);
            put("clear", 1);
            put("mod", 3);
        }
    };
    @Override
    public GenericTripleExpression<T> parse(String stringExpression) throws ExpressionParsingException {
        expression = new ParsedExp<T>(stringExpression + ")");
        GenericTripleExpression<T> parsedExpression = parseLevel();
        if (!expression.isEmpty()) {
            throw new ExpressionParsingException("invalid bracket sequence");
        }
        return parsedExpression;
    }
    private GenericTripleExpression<T> parseLevel() throws ExpressionParsingException {
        ArrayList<GenericTripleExpression<T>> parsed = new ArrayList<>();
        ArrayList<String> sign = new ArrayList<>();
        while (true) {
            ArrayList<String> unary = new ArrayList<>();
            while (expression.isUnary()) {
                unary.add(expression.getUnary());
            }
            boolean whitespace = expression.isWhitespace();
            int flag = expression.checkNext();
            GenericTripleExpression<T> newExp;
            if (flag == 0) {
                T x;
                if (!whitespace && unary.size() > 0 && "-".equals(unary.get(unary.size() - 1))) {
                    x = (T) expression.getConst("-", operation);
                    unary.remove(unary.size() - 1);
                } else {
                    x = (T) expression.getConst("+", operation);
                }
                newExp = new Const<>(x, operation);
            } else {
                if (flag == 1) {
                    newExp = new Variable<T>(expression.getVariable(), operation);
                } else {
                    if (flag == 2) {
                        expression.next(1);
                        newExp = parseLevel();
                    } else {
                        throw new ExpressionParsingException("Incorrect expression, expected \"(\", or Variable, or Const");
                    }
                }
            }
            for (int i = unary.size() - 1; i >= 0; i--) {
                if ("-".equals(unary.get(i))) {
                    newExp = new Negate<T>(newExp, operation);
                }
                if ("square".equals(unary.get(i))) {
                    newExp = new Square<T>(newExp, operation);
                }
                if ("abs".equals(unary.get(i))) {
                    newExp = new Abs<>(newExp, operation);
                }
            }
            parsed.add(newExp);
            flag = expression.checkNext();
            if (flag == 3) {
                sign.add(expression.getSign());
            } else {
                if (flag == 4) {
                    expression.next(1);
                    break;
                } else {
                    throw new ExpressionParsingException("Incorrect expression, expected \")\", or Sign");
                }
            }
        }
        for (int i = 3; i >= 1; i--) {
            ArrayList<GenericTripleExpression<T>> newParsed = new ArrayList<>();
            ArrayList<String> newSign = new ArrayList<>();
            newParsed.add(parsed.get(0));
            for (int j = 1; j < parsed.size(); j++) {

                if (LEVELS.get(sign.get(j - 1)) == i) {
                    GenericTripleExpression<T> left;
                    left = get(newParsed.get(newParsed.size() - 1), parsed.get(j), sign.get(j - 1));
                    newParsed.remove(newParsed.size() - 1);
                    newParsed.add(left);
                } else {
                    newParsed.add(parsed.get(j));
                    newSign.add(sign.get(j - 1));
                }
            }
            parsed = newParsed;
            sign = newSign;
        }
        return parsed.get(0);
    }
    private GenericTripleExpression<T> get (GenericTripleExpression<T> left, GenericTripleExpression<T> right, String sign) throws ExpressionParsingException {
        if (sign.equals("+")) {
            return new Add<T>(left, right, operation);
        }
        if (sign.equals("-")) {
            return new Subtract<T>(left, right, operation);
        }
        if (sign.equals("/")) {
            return new Divide<T>(left, right, operation);
        }
        if (sign.equals("*")) {
            return new Multiply<T>(left, right, operation);
        }
        if (sign.equals("mod")) {
            return new Mod<T>(left, right, operation);
        }
        throw new ExpressionParsingException("Incorrect expression, expected Sign");
    }
}
