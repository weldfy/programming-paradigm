package expression.exceptions;

public class IntegerOverflowException extends EvaluatingException {
    public IntegerOverflowException (String message){
        super(message);
    }
}
