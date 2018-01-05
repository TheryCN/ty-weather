package github.com.therycn.exception;

/**
 * Failure not checked exception.
 * 
 * @author TheryLeopard
 *
 */
public class FailureException extends RuntimeException {

    /** Serial version. */
    private static final long serialVersionUID = 2292297665693656256L;

    public FailureException(String message) {
        super(message);
    }

    public FailureException(Throwable cause) {
        super(cause);
    }

    public FailureException(String message, Throwable cause) {
        super(message, cause);
    }

}
