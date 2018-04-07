package github.com.therycn.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import github.com.therycn.entity.WeatherForecastView;
import github.com.therycn.entity.openweather.WeatherForecasts;
import github.com.therycn.exception.ClientFailureException;
import github.com.therycn.service.weather.OpenWeatherMapClient;

/**
 * Weather integration tests.
 * 
 * @author TheryLeopard
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class WeatherControllerIT {

	private static final String COUNTRY_CODE = "FR";

	private static final String CITY_NAME = "Grenoble";

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private OpenWeatherMapClient client;

	private ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Test retrieving forecast by hours from Grenoble.
	 * 
	 * @throws IOException
	 * @throws ClientFailureException
	 */
	@Test
	public void testGetForecastByHours() throws IOException, ClientFailureException {
		// Given
		mockForecastClient(CITY_NAME, COUNTRY_CODE);

		// When
		ResponseEntity<WeatherForecasts> response = restTemplate.getForEntity(
				"/weather/forecastByHours?city={city}&countryCode={countryCode}", WeatherForecasts.class, CITY_NAME, COUNTRY_CODE);

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		WeatherForecasts weatherForecasts = response.getBody();
		assertThat(weatherForecasts.getForecastList().size()).isEqualTo(40);
	}

	/**
	 * Test retrieving forecast from Grenoble.
	 * 
	 * @throws IOException
	 * @throws ClientFailureException
	 */
	@Test
	public void testGetWeatherForecastView() throws IOException, ClientFailureException {
		// Given
		mockForecastClient(CITY_NAME, COUNTRY_CODE);

		// When
		ResponseEntity<WeatherForecastView> response = restTemplate.getForEntity(
				"/weather/forecast?city={city}&countryCode={countryCode}", WeatherForecastView.class, CITY_NAME, COUNTRY_CODE);

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		WeatherForecastView weatherForecastView = response.getBody();
		assertThat(weatherForecastView.getTempAvg()).isEqualTo(268.7853698730469d);
	}

	private void mockForecastClient(String cityName, String countryCode) throws IOException, ClientFailureException {
		WeatherForecasts forecastResponse = objectMapper.readValue(
				getClass().getClassLoader().getResourceAsStream("data/forecast_grenoble_fr.json"),
				WeatherForecasts.class);

		given(client.getFiveDaysPerThreeHoursForecast(cityName, countryCode)).willReturn(forecastResponse);
	}

}
