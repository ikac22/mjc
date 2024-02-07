package mjc.exception;

public class ParseException extends Exception {
    public ParseException() {
        super("Input program had syntax errors.");
    }
}