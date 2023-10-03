package expression;

import expression.exceptions.IntegerOverflowException;
import expression.operations.Operation;

public class Subtract<T> extends Sign<T> {
    public Subtract (GenericTripleExpression<T> left, GenericTripleExpression<T> right, Operation<T> operation) {
        super(left, right, operation);
    }
    @Override
    public char getSign() {
        return '-';
    }
    public String sign() {
        return "-";
    }

    public T operation(T left, T right) throws IntegerOverflowException {
        return operation.subtract(left, right);
    }
    @Override
    public String getL() {
        return "sc";
    }
    @Override
    public String getR() {
        return "-+sc";
    }
}
