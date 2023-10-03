package expression;

import expression.exceptions.*;
import expression.operations.Operation;

public abstract class Sign<T> implements GenericTripleExpression<T> {
    GenericTripleExpression<T> left, right;
    Operation<T> operation;
    protected abstract String sign();
    protected abstract char getSign();
    protected abstract T operation(T left, T right) throws IntegerOverflowException, DivisionByZeroException, IncorrectOperation, ModuleException;
    protected abstract String getL();
    protected abstract String getR();
    public Sign (GenericTripleExpression<T> left, GenericTripleExpression<T> right, Operation<T> operation) {
        this.left = left;
        this.right = right;
        this.operation = operation;
    }
    public String toString () {
        return "(" + left.toString() + " " + sign() + " " + right.toString() + ')';
    }
    @Override
    public T evaluate(T x, T y, T z) throws DivisionByZeroException, IntegerOverflowException, IncorrectValueException, IncorrectOperation, ModuleException {
        return operation(this.left.evaluate(x, y, z), this.right.evaluate(x, y, z));
    }
    @Override
    public boolean equals(Object x) {
        return x != null && x.getClass() == this.getClass() && toString().equals(x.toString());
    }
    public int hashCode () {
        return toString().hashCode();
    }
    @Override
    public String toMiniString() {
        char l = left instanceof Sign ? ((Sign) left).getSign() : 'C';
        char r = right instanceof Sign ? ((Sign) right).getSign() : 'C';
        return (checkBrackets(l, false) ? '(' + left.toMiniString() + ')' : left.toMiniString())
                + " " + sign() + " " + (checkBrackets(r, true) ? '(' + right.toMiniString() + ')' : right.toMiniString());
    }
    public boolean checkBrackets(char c, boolean nm) {
        final String parentheses = nm ? getR() : getL();
        for (int i = 0; i < parentheses.length(); i++) {
            if (parentheses.charAt(i) == c) {
                return true;
            }
        }
        return false;
    }
}
