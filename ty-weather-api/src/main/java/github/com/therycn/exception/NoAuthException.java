package github.com.therycn.exception;

/**
 * Not authenticated exception.
 * 
 * @author TheryLeopard
 *
 */
public class NoAuthException extends FailureException {

    /** Serial version. */
    private static final long serialVersionUID = -8325094736680964862L;

    public NoAuthException(String message) {
        super(message);
    }

}
