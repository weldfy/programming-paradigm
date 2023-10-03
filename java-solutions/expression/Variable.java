package expression;

import expression.operations.Operation;

public class Variable<T> implements GenericTripleExpression<T> {
    String arg;
    Operation<T> operation;
    public Variable (String arg, Operation<T> operation) {
        this.arg = arg;
        this.operation = operation;
    }
    @Override
    public String toString() {
        return arg;
    }
    @Override
    public T evaluate (T x, T y, T z) {
        if (arg.equals("x")) {
            return x;
        }
        if (arg.equals("y")) {
            return y;
        }
        return z;
    }
    public boolean equals (Object x) {
        return x != null && x.getClass() == this.getClass() && arg.equals(x.toString());
    }
    public int hashCode () {
        return arg.hashCode();
    }
    @Override
    public String toMiniString () {
        return arg;
    }
}
