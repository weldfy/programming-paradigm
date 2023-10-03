package expression;

import expression.operations.Operation;

public class Const<T> implements GenericTripleExpression<T> {
    T x;
    Operation<T> operation;
    public Const (T x, Operation<T> operation) {
        this.x = x;
        this.operation = operation;
    }
    @Override
    public String toString () {
        return String.valueOf(x);
    }
    @Override
    public T evaluate (T x, T y, T z) {
        return this.x;
    }
    public boolean equals (Object x) {
        return x != null && x.getClass() == this.getClass() && this.x == ((Const) x).x;
    }
    @Override
    public String toMiniString () {
        return String.valueOf(x);
    }
}
