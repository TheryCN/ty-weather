package github.com.therycn.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.api.services.calendar.model.Event;

import github.com.therycn.exception.ClientFailureException;

/**
 * Calendar integration tests.
 * 
 * @author TheryLeopard
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CalendarControllerIT {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Rule
	public WireMockRule wireMockRule = new WireMockRule();

	/**
	 * Test retrieving upcoming events.
	 * 
	 * @throws IOException
	 * @throws ClientFailureException
	 */
	@Ignore
	@Test
	public void testGetUpcomingEvents() {
		// Given

		// Mock OAuth2 !
		// Mock /principal from http://localhost:8080/principal (WireMock runs on port
		// 8080 by default)
		stubFor(get(urlPathMatching("/principal")).willReturn(okJson("{\"username\":\"trusted-user\"}]}")));

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer xyz123");

		// When
		ResponseEntity<Event[]> response = testRestTemplate.exchange("/calendar/events", HttpMethod.GET,
				new HttpEntity<>(headers), Event[].class);

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}
