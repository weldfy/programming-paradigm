package expression.parser;

import expression.GenericTripleExpression;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
@FunctionalInterface
public interface TripleParser<T> {
    GenericTripleExpression<T> parse(String expression) throws Exception;
}
