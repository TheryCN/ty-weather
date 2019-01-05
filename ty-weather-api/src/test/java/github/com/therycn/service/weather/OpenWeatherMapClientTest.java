package github.com.therycn.service.weather;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import com.fasterxml.jackson.databind.ObjectMapper;

import github.com.therycn.entity.openweather.City;
import github.com.therycn.entity.openweather.WeatherForecasts;
import github.com.therycn.exception.ClientFailureException;

/**
 * Open weather map client test.
 * 
 * @author TheryLeopard
 *
 */
@RunWith(SpringRunner.class)
@RestClientTest(OpenWeatherMapClient.class)
public class OpenWeatherMapClientTest {

	@Autowired
	private OpenWeatherMapClient client;

	@Autowired
	private MockRestServiceServer server;

	@Value("${api.forecast.query}")
	private String apiForecastQuery;

	private ObjectMapper mapper = new ObjectMapper();

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	/**
	 * Test get forecast when the server answer is OK.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetFiveDaysPerThreeHoursForecastServerOK() throws Exception {
		// Given
		WeatherForecasts expectedResponse = new WeatherForecasts();
		expectedResponse.setCity(new City());
		expectedResponse.getCity().setId(1l);
		expectedResponse.getCity().setName("Grenoble");
		server.expect(requestTo(Matchers.startsWith(apiForecastQuery)))
				.andRespond(withSuccess(mapper.writeValueAsString(expectedResponse), MediaType.APPLICATION_JSON));

		// When
		WeatherForecasts response = client.getFiveDaysPerThreeHoursForecast("Grenoble", "FR");

		// Then
		assertThat(response).isEqualTo(expectedResponse);
	}

	/**
	 * Test client when the server answer is KO.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetFiveDaysPerThreeHoursForecastServerError() throws Exception {
		// Given
		expectedEx.expect(ClientFailureException.class);

		server.expect(requestTo(Matchers.startsWith(apiForecastQuery))).andRespond(withServerError());

		// When
		client.getFiveDaysPerThreeHoursForecast("Grenoble", "FR");

		// Then - expectedEx
	}
}
