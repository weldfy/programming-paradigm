package expression.generic;

import expression.GenericTripleExpression;
import expression.exceptions.IncorrectModeException;
import expression.exceptions.MyException;
import expression.parser.ExpressionParser;
import expression.operations.*;

import java.util.HashMap;
import java.util.Map;

public class GenericTabulator implements Tabulator {
    private static final Map<String, Operation<?>> MODE_EXPRESSION = new HashMap<>() {
        {
            put("i", new IntegerOperation(true));
            put("u", new IntegerOperation(false));
            put("d", new DoubleOperation());
            put("bi", new BigIntegerOperation());
            put("l", new LongOperation());
            put("s", new ShortOperation());
        }
    };

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws MyException {
        if (!MODE_EXPRESSION.containsKey(mode)) {
            throw new IncorrectModeException(mode + " is incorrect");
        }
        return doGeneric(MODE_EXPRESSION.get(mode), expression, x1, x2, y1, y2, z1, z2);
    }

    private <T> Object[][][] doGeneric(Operation<T> operation, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws MyException {
        GenericTripleExpression<T> exception = new ExpressionParser<>(operation).parse(expression);
        Object[][][] result = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        for (int i = 0; i < x2 - x1 + 1; i++) {
            for (int j = 0; j < y2 - y1 + 1; j++) {
                for (int k = 0; k < z2 - z1 + 1; k++) {
                    try {
                        result[i][j][k] = exception.evaluate(operation.parse(x1 + i), operation.parse(y1 + j), operation.parse(z1 + k));
                    } catch (MyException e) {
                        result[i][j][k] = null;
                    }
                }
            }
        }
        return result;
    }
}