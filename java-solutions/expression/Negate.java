package expression;

import expression.exceptions.IntegerOverflowException;
import expression.operations.Operation;

public class Negate<T> extends Unary<T>{
    public Negate (GenericTripleExpression<T> expression, Operation<T> operation) {
        super(expression, operation);
    }
    public T operation(T x) throws IntegerOverflowException {
        return operation.negate(x);
    }
    public String getSign() {
        return "-";
    }
}
