package expression.operations;

import expression.exceptions.*;

public class DoubleOperation implements Operation<Double>{
    @Override
    public Double add(Double a, Double b) throws IntegerOverflowException {
        return a + b;
    }

    @Override
    public Double divine(Double a, Double b) {
        return a / b;
    }

    @Override
    public Double multiply(Double a, Double b) {
        return a * b;
    }

    @Override
    public Double negate(Double a) {
        return -a;
    }

    @Override
    public Double parse(String s) {
        return Double.parseDouble(s);
    }
    public Double parse(int x) {
        return (double) x;
    }

    @Override
    public Double abs(Double a) throws IntegerOverflowException {
        return Math.abs(a);
    }

    @Override
    public Double square(Double a) throws IntegerOverflowException {
        return multiply(a, a);
    }

    @Override
    public Double mod(Double a, Double b) throws DivisionByZeroException, ModuleException {
        return a%b;
    }

    @Override
    public Double subtract(Double a, Double b) {
        return a - b;
    }

}
