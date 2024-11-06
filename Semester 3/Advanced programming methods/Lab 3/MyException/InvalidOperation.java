package MyException;

public class InvalidOperation extends Exception {

    public InvalidOperation(String errorMessage) {
        super(errorMessage);
    }
}