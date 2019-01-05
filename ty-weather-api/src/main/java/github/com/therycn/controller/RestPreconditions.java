package github.com.therycn.controller;

import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.util.StringUtils;

import github.com.therycn.exception.NoAuthException;

/**
 * Rest preconditions for controllers.
 * 
 * @author TheryLeopard
 *
 */
public final class RestPreconditions {

	/**
	 * Private constructor.
	 */
	private RestPreconditions() {
		throw new AssertionError();
	}

	/**
	 * Check if there is an active google authentication.
	 * 
	 * @param context
	 *            {@link OAuth2ClientContext}
	 */
	public static void checkActiveGoogleAuth(OAuth2ClientContext context) {
		if (context == null || context.getAccessToken() == null
				|| StringUtils.isEmpty(context.getAccessToken().getValue())) {
			throw new NoAuthException("There is no Google active token");
		}
	}
}
