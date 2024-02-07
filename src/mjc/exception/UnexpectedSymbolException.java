package mjc.exception;

public class UnexpectedSymbolException extends Exception {

    public UnexpectedSymbolException() {
        super("Unexpected symbol");
    }

    public UnexpectedSymbolException(String message) {
        super(message);
    }
}
