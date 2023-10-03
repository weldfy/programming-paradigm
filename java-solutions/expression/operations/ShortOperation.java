package expression.operations;

import expression.exceptions.*;

public class ShortOperation implements Operation<Short> {
    @Override
    public Short add(Short a, Short b) throws IntegerOverflowException {
        return (short) (a + b);
    }

    @Override
    public Short divine(Short a, Short b) throws DivisionByZeroException {
        if (b == 0) {
            throw new DivisionByZeroException("division by zero");
        }
        return (short) (a / b);
    }
    @Override
    public Short multiply(Short a, Short b) {
        return (short) (a * b);
    }

    @Override
    public Short negate(Short a) {
        return (short) -a;
    }

    @Override
    public Short subtract(Short a, Short b) {
        return (short) (a - b);
    }

    @Override
    public Short parse(String s) {
        return Short.parseShort(s);
    }
    public Short parse(int x) {
        return (short) x;
    }

    @Override
    public Short abs(Short a) throws IntegerOverflowException {
        return (short) Math.abs(a);
    }

    @Override
    public Short square(Short a) throws IntegerOverflowException {
        return multiply(a, a);
    }

    @Override
    public Short mod(Short a, Short b) throws DivisionByZeroException, ModuleException {
        if (b == 0) {
            throw new DivisionByZeroException("division by zero");
        }
        return (short) (a%b);
    }
}
