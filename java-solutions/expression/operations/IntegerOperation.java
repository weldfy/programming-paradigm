package expression.operations;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.IntegerOverflowException;
import expression.exceptions.ModuleException;

public class IntegerOperation implements Operation<Integer>{
    boolean check;
    public IntegerOperation (boolean check) {
        this.check = check;
    }
    @Override
    public Integer add(Integer a, Integer b) throws IntegerOverflowException {
        if (check) {
            int min, max;
            if (a >= 0) {
                min = java.lang.Integer.MIN_VALUE;
            } else {
                min = java.lang.Integer.MIN_VALUE - a;
            }
            if (a <= 0) {
                max = java.lang.Integer.MAX_VALUE;
            } else {
                max = java.lang.Integer.MAX_VALUE - a;
            }
            if (b < min || b > max) {
                throw new IntegerOverflowException("add overflow");
            }
        }
        return a + b;
    }

    @Override
    public Integer divine(Integer a, Integer b) throws DivisionByZeroException, IntegerOverflowException {
        if (b == -1 && a == Integer.MIN_VALUE && check) {
            throw new IntegerOverflowException("divide overflow");
        }
        if (b == 0) {
            throw new DivisionByZeroException("division by zero");
        }
        return a / b;
    }

    @Override
    public Integer multiply(Integer a, Integer b) throws IntegerOverflowException {
        if (check) {
            if (a != 0) {
                int max = Integer.MAX_VALUE / a;
                int min;
                if (a == -1) {
                    min = Integer.MAX_VALUE;
                } else {
                    min = Integer.MIN_VALUE / a;
                }
                if (max < min) {
                    int c = max;
                    max = min;
                    min = c;
                }
                if (min > b || max < b) {
                    throw new IntegerOverflowException("multiple overflow");
                }
            }
        }
        return a * b;
    }

    @Override
    public Integer negate(Integer a) throws IntegerOverflowException {
        if (a == Integer.MIN_VALUE && check) {
            throw new IntegerOverflowException ("negate overflow");
        }
        return -a;
    }

    @Override
    public Integer parse(String s) {
        return Integer.parseInt(s);
    }
    public Integer parse(int x) {
        return x;
    }
    @Override
    public Integer subtract(Integer a, Integer b) throws IntegerOverflowException {
        if (check) {
            int min;
            if (a <= -1) {
                min = Integer.MIN_VALUE;
            } else {
                min = a - Integer.MAX_VALUE;
            }
            int max;
            if (a >= -1) {
                max = Integer.MAX_VALUE;
            } else {
                max = a - Integer.MIN_VALUE;
            }
            if (b < min || b > max) {
                throw new IntegerOverflowException("subtract overflow");
            }
        }
        return a - b;
    }
    @Override
    public Integer abs(Integer a) throws IntegerOverflowException {
        if (a == Integer.MIN_VALUE && check) {
            throw new IntegerOverflowException ("abs overflow");
        }
        return Math.abs(a);
    }
    @Override
    public Integer square(Integer a) throws IntegerOverflowException {
        return multiply(a, a);
    }
    @Override
    public Integer mod(Integer a, Integer b) throws DivisionByZeroException, ModuleException {
        if (b == 0) {
            throw new DivisionByZeroException("division by zero");
        }
        return a%b;
    }
}
