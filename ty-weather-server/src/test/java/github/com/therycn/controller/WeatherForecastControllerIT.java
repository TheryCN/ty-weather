package github.com.therycn.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
 * Weather forecast integration tests.
 * 
 * @author TheryLeopard
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class WeatherForecastControllerIT {

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private OpenWeatherMapClient client;

	private ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Test retrieving forecast view from Grenoble.
	 * 
	 * @throws IOException
	 * @throws ClientFailureException
	 */
	@Test
	public void testGetForecastViewGrenoble() throws IOException, ClientFailureException {
		// Given
		String cityName = "Grenoble";
		String countryCode = "FR";

		mockForecastClient(cityName, countryCode);

		// When
		ResponseEntity<WeatherForecastView[]> response = restTemplate.getForEntity(
				"/api/weather-forecast/forecast?city={city}&countryCode={countryCode}", WeatherForecastView[].class,
				cityName, countryCode);

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		List<WeatherForecastView> forecastViewList = Arrays.asList(response.getBody());
		assertThat(forecastViewList.size()).isEqualTo(40);
	}

	/**
	 * Test retrieving average forecast from Grenoble.
	 * 
	 * @throws IOException
	 * @throws ClientFailureException
	 */
	@Test
	public void testGetWeatherAverageForecastGrenoble() throws ClientFailureException, IOException {
		// Given
		String cityName = "Grenoble";
		String countryCode = "FR";

		mockForecastClient(cityName, countryCode);

		double expected = -4.187541961669922d;

		// When
		ResponseEntity<Double> response = restTemplate.getForEntity(
				"/api/weather-forecast/averageForecast?city={city}&countryCode={countryCode}", Double.class, cityName,
				countryCode);

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(expected);
	}

	private void mockForecastClient(String cityName, String countryCode) throws IOException, ClientFailureException {
		WeatherForecasts forecastResponse = objectMapper.readValue(
				getClass().getClassLoader().getResourceAsStream("data/forecast_grenoble_fr.json"),
				WeatherForecasts.class);

		given(client.getFiveDaysPerThreeHoursForecast(cityName, countryCode)).willReturn(forecastResponse);
	}

}
