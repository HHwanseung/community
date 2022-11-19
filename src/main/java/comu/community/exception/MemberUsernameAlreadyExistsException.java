package comu.community.exception;

public class MemberUsernameAlreadyExistsException extends RuntimeException {
    public MemberUsernameAlreadyExistsException(String message) {
        super(message);
    }
}
