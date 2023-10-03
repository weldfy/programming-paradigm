package expression;

import expression.exceptions.IntegerOverflowException;
import expression.operations.Operation;

public class Square<T> extends Unary<T> {
    public Square (GenericTripleExpression<T> expression, Operation<T> operation) {
        super(expression, operation);
    }
    public T operation(T a) throws IntegerOverflowException {
        return operation.square(a);
    }
    public String getSign() {
        return "square";
    }
}
