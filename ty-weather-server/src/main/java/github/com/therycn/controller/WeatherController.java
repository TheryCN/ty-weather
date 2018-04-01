package github.com.therycn.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import github.com.therycn.entity.WeatherForecastView;
import github.com.therycn.entity.openweather.CurrentWeatherResponse;
import github.com.therycn.service.weather.WeatherService;

/**
 * Weather Forecast Controller.
 * 
 * @author TheryLeopard
 *
 */
@RestController
@RequestMapping("/weather")
public class WeatherController {

	private WeatherService weatherService;

	/**
	 * Constructor.
	 * 
	 * @param weatherService
	 *            {@link WeatherService}
	 */
	public WeatherController(WeatherService weatherService) {
		this.weatherService = weatherService;
	}

	/**
	 * Gets the current weather for the given city & country.
	 * 
	 * @param city
	 *            the city
	 * @param countryCode
	 *            the country code
	 * @return {@link CurrentWeatherResponse}
	 */
	@RequestMapping(value = "/current")
	public CurrentWeatherResponse getCurrentWeather(
			@RequestParam(value = "city", defaultValue = "Grenoble") String city,
			@RequestParam(value = "countryCode", defaultValue = "FR") String countryCode) {
		return weatherService.getCurrentWeather(city, countryCode);
	}

	/**
	 * Gets the weather view (Weather data formatted).
	 * 
	 * @param city
	 *            the city
	 * @param countryCode
	 *            the country code (i.e. FR, EN, ...)
	 * @return {@link WeatherForecastView} list
	 */
	@RequestMapping(value = "/forecast")
	public List<WeatherForecastView> getWeatherForecastView(
			@RequestParam(value = "city", defaultValue = "Grenoble") String city,
			@RequestParam(value = "countryCode", defaultValue = "FR") String countryCode) {
		return weatherService.getForecast(city, countryCode);
	}

	/**
	 * Gets the average temperature calculated from the 3 days forecast.
	 * 
	 * @param city
	 *            the city
	 * @param countryCode
	 *            the country code
	 * @return the temperature
	 */
	@RequestMapping(value = "/averageForecast")
	public double getWeatherAverageForecast(@RequestParam(value = "city", defaultValue = "Grenoble") String city,
			@RequestParam(value = "countryCode", defaultValue = "FR") String countryCode) {
		return getWeatherForecastView(city, countryCode).stream()
				.collect(Collectors.averagingDouble(WeatherForecastView::getTemp));
	}

}
