package expression;

import expression.exceptions.*;

public interface GenericTripleExpression<T> extends ToMiniString{
    T evaluate(T a, T b, T c) throws DivisionByZeroException, IntegerOverflowException, IncorrectValueException, IncorrectOperation, ModuleException;
    String toString();
}
