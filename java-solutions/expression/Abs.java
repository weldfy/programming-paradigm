package expression;

import expression.exceptions.IntegerOverflowException;
import expression.operations.Operation;

public class Abs<T> extends Unary<T>{
    public Abs (GenericTripleExpression<T> expression, Operation<T> operation) {
        super(expression, operation);
    }
    public T operation(T a) throws IntegerOverflowException {
        return operation.abs(a);
    }
    public String getSign() {
        return "abs";
    }
}
