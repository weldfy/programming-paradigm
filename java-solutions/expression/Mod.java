package expression;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.IntegerOverflowException;
import expression.exceptions.ModuleException;
import expression.operations.Operation;

public class Mod<T> extends Sign<T>{
    public Mod (GenericTripleExpression<T> left, GenericTripleExpression<T> right, Operation<T> operation) {
        super(left, right, operation);
    }
    @Override
    public char getSign() {
        return 'm';
    }
    public String sign() {
        return "mod";
    }
    @Override
    public T operation(T left, T right) throws IntegerOverflowException, DivisionByZeroException, ModuleException {
        return operation.mod(left, right);
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
