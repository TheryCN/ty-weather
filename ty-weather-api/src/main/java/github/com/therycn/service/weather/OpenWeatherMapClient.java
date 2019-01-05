package github.com.therycn.service.weather;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import github.com.therycn.entity.openweather.CurrentWeather;
import github.com.therycn.entity.openweather.WeatherForecasts;
import github.com.therycn.exception.ClientFailureException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * OpenWeatherMap client.
 * 
 * @author TheryLeopard
 *
 */
@Service
@Slf4j
public class OpenWeatherMapClient {

	@Getter
	@Value("${app.id}")
	private String appId;

	@Value("${api.forecast.query}")
	private String apiForecastQuery;

	@Value("${api.currentWeather.query}")
	private String apiCurrentWeatherQuery;

	@Value("${api.units}")
	private String units;

	private RestTemplate restTemplate;

	public OpenWeatherMapClient(RestTemplateBuilder restTemplateBuilder) {
		restTemplate = restTemplateBuilder.build();
	}

	/**
	 * Retrieve current weather data.
	 * 
	 * @param city
	 *            the city name
	 * @param countryCode
	 *            the country code
	 * @return {@link CurrentWeather}
	 */
	public CurrentWeather getCurrentWeather(String city, String countryCode) {
		URI targetUrl = getTargetUrl(apiCurrentWeatherQuery, city, countryCode);

		try {
			return restTemplate.getForObject(targetUrl, CurrentWeather.class);
		} catch (RestClientResponseException e) {
			log.warn(e.getMessage(), e);
			throw new ClientFailureException(e.getMessage(), e);
		}
	}

	/**
	 * Retrieve forecast data (5 day / 3 hour).
	 * 
	 * @param city
	 *            the city name
	 * @param countryCode
	 *            the country code
	 * @return {@link WeatherForecasts}
	 */
	public WeatherForecasts getFiveDaysPerThreeHoursForecast(String city, String countryCode) {
		URI targetUrl = getTargetUrl(apiForecastQuery, city, countryCode);

		try {
			return restTemplate.getForObject(targetUrl, WeatherForecasts.class);
		} catch (RestClientResponseException e) {
			log.warn(e.getMessage(), e);
			throw new ClientFailureException(e.getMessage(), e);
		}

	}

	private URI getTargetUrl(String query, String city, String countryCode) {
		return UriComponentsBuilder.fromUriString(query).queryParam("units", units)
				.queryParam("q", city + "," + countryCode).queryParam("APPID", appId).build().toUri();
	}
}
