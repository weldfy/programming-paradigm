package expression;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.IntegerOverflowException;
import expression.operations.Operation;

public class Divide<T> extends Sign<T> {
    public Divide (GenericTripleExpression<T> left, GenericTripleExpression<T> right, Operation<T> operation) {
        super(left, right, operation);
    }
    @Override
    public char getSign() {
        return '/';
    }
    public String sign() {
        return "/";
    }
    @Override
    public T operation(T left, T right) throws DivisionByZeroException, IntegerOverflowException {
        return operation.divine(left, right);
    }
    @Override
    public String getL() {
        return "+-cs";
    }
    @Override
    public String getR() {
        return "-+*/cs";
    }
}
