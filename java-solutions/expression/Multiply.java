package expression;

import expression.exceptions.IntegerOverflowException;
import expression.operations.Operation;

public class Multiply<T> extends Sign<T> {
    public Multiply (GenericTripleExpression<T> left, GenericTripleExpression<T> right, Operation<T> operation) {
        super(left, right, operation);
    }
    @Override
    public char getSign() {
        return '*';
    }
    public String sign() {
        return "*";
    }
    @Override
    public T operation(T left, T right) throws IntegerOverflowException {
        return operation.multiply(left, right);
    }
    @Override
    public String getL() {
        return "+-cs";
    }
    @Override
    public String getR() {
        return "+-/cs";
    }
}
