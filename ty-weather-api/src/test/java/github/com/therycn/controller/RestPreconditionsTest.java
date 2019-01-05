package github.com.therycn.controller;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import github.com.therycn.exception.NoAuthException;

/**
 * Test class {@link RestPreconditions}.
 * 
 * @author THERY
 *
 */
public class RestPreconditionsTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	/**
	 * Test check active google authentication with token.
	 */
	@Test
	public void testCheckActiveGoogleAuthWithToken() {
		// Given
		OAuth2ClientContext context = Mockito.mock(OAuth2ClientContext.class);

		OAuth2AccessToken token = Mockito.mock(OAuth2AccessToken.class);
		Mockito.when(token.getValue()).thenReturn("TOKEN_EXAMPLE");

		Mockito.when(context.getAccessToken()).thenReturn(token);

		// When
		RestPreconditions.checkActiveGoogleAuth(context);

		// Then - No Exception
	}

	/**
	 * Test check active google authentication without token.
	 */
	@Test
	public void testCheckActiveGoogleAuthWithoutToken() {
		// Given
		OAuth2ClientContext context = Mockito.mock(OAuth2ClientContext.class);
		expectedEx.expect(NoAuthException.class);

		// When
		RestPreconditions.checkActiveGoogleAuth(context);

		// Then - Exception
	}

	/**
	 * Test check active google authentication without context.
	 */
	@Test
	public void testCheckActiveGoogleAuthWithoutContext() {
		// Given
		expectedEx.expect(NoAuthException.class);
		// When
		RestPreconditions.checkActiveGoogleAuth(null);

		// Then - Exception
	}

}
