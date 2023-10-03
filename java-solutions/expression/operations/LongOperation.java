package expression.operations;

import expression.exceptions.*;

public class LongOperation implements Operation<Long>{
    @Override
    public Long add(Long a, Long b) throws IntegerOverflowException {
        return a + b;
    }

    @Override
    public Long divine(Long a, Long b) throws DivisionByZeroException, IntegerOverflowException {
        if (b == 0) {
            throw new DivisionByZeroException("division by zero");
        }
        return a / b;
    }

    @Override
    public Long multiply(Long a, Long b) throws IntegerOverflowException {
        return a * b;
    }

    @Override
    public Long negate(Long a) throws IntegerOverflowException {
        return -a;
    }

    @Override
    public Long subtract(Long a, Long b) throws IntegerOverflowException {
        return a - b;
    }

    @Override
    public Long parse(String s) {
        return Long.parseLong(s);
    }
    public Long parse(int x) {
        return (long) x;
    }

    @Override
    public Long abs(Long a) throws IntegerOverflowException {
        return Math.abs(a);
    }

    @Override
    public Long square(Long a) throws IntegerOverflowException {
        return multiply(a, a);
    }

    @Override
    public Long mod(Long a, Long b) throws DivisionByZeroException, ModuleException {
        if (b == 0) {
            throw new DivisionByZeroException("division by zero");
        }
        return a%b;
    }
}
