package comu.community.exception;

public class FileUploadFailureException extends RuntimeException{
    public FileUploadFailureException(Throwable causer) {
        super(causer);
    }
}
