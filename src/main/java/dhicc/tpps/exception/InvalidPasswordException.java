package dhicc.tpps.exception;

public class InvalidPasswordException extends DhiccException {
    private static final String MESSAGE = "비밀번호가 올바르지 않습니다.";

    public InvalidPasswordException() {
        super(MESSAGE);
    }

    public InvalidPasswordException(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
