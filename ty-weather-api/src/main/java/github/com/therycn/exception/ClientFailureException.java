package github.com.therycn.exception;

/**
 * Client failure exception.
 * 
 * @author TheryLeopard
 *
 */
public class ClientFailureException extends FailureException {

	/** Serial version. */
	private static final long serialVersionUID = 7984919426325782196L;

	public ClientFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
