package expression.operations;

import expression.exceptions.*;

import java.math.BigInteger;

public class BigIntegerOperation implements Operation<BigInteger>{

    @Override
    public BigInteger add(BigInteger a, BigInteger b) throws IntegerOverflowException {
        return a.add(b);
    }

    @Override
    public BigInteger divine(BigInteger a, BigInteger b) throws DivisionByZeroException {
        if (b.equals(BigInteger.ZERO)) {
            throw new DivisionByZeroException("Division by zero");
        }
        return a.divide(b);
    }

    @Override
    public BigInteger multiply(BigInteger a, BigInteger b) {
        return a.multiply(b);
    }

    @Override
    public BigInteger negate(BigInteger a) {
        return a.negate();
    }

    @Override
    public BigInteger subtract(BigInteger a, BigInteger b) {
        return a.subtract(b);
    }

    @Override
    public BigInteger parse(String s) {
        return new BigInteger(s);
    }
    public BigInteger parse(int x) {
        return BigInteger.valueOf(x);
    }

    @Override
    public BigInteger abs(BigInteger a) throws IntegerOverflowException {
        return a.abs();
    }

    @Override
    public BigInteger square(BigInteger a) throws IntegerOverflowException {
        return a.multiply(a);
    }

    @Override
    public BigInteger mod(BigInteger a, BigInteger b) throws DivisionByZeroException, ModuleException {
        if (b.equals(BigInteger.ZERO)) {
            throw new DivisionByZeroException("division by zero");
        }
        if (b.compareTo(BigInteger.ZERO) < 0) {
            throw new ModuleException("modulus not positive");
        }
        return a.mod(b);
    }

}
