package expression;

import expression.exceptions.*;
import expression.operations.Operation;

public abstract class Unary<T> implements GenericTripleExpression<T> {
    protected abstract T operation(T x) throws IntegerOverflowException, IncorrectValueException;
    protected abstract String getSign();
    GenericTripleExpression<T> expression;
    Operation<T> operation;
    public Unary(GenericTripleExpression<T> expression, Operation<T> operation) {
        this.expression = expression;
        this.operation = operation;
    }
    @Override
    public T evaluate(T x, T y, T z) throws IntegerOverflowException, DivisionByZeroException, IncorrectValueException, IncorrectOperation, ModuleException {
        return operation(expression.evaluate(x, y, z));
    }
    @Override
    public String toString() {
        return getSign() + '(' + expression.toString() + ')';
    }
    @Override
    public String toMiniString() {
        if (expression.getClass() == Const.class || expression.getClass() == Variable.class || expression instanceof Unary) {
            return getSign() + " " + expression.toMiniString();
        } else {
            return getSign() + '(' + expression.toMiniString() + ')';
        }

    }
}
