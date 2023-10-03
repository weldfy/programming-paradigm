package expression.operations;

import expression.exceptions.*;

public interface Operation<T> {
    T add(T a, T b) throws IntegerOverflowException;
    T divine(T a, T b) throws DivisionByZeroException, IntegerOverflowException;
    T multiply(T a, T b) throws IntegerOverflowException;
    T negate(T a) throws IntegerOverflowException;
    T subtract(T a, T b) throws IntegerOverflowException;
    T parse(String s);
    T parse(int x);
    T abs(T a) throws IntegerOverflowException;
    T square(T a) throws IntegerOverflowException;
    T mod(T a, T b) throws DivisionByZeroException, ModuleException;
}
