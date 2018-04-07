package github.com.therycn.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import github.com.therycn.entity.WeatherForecastView;
import github.com.therycn.entity.openweather.CurrentWeather;
import github.com.therycn.entity.openweather.WeatherForecasts;
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
	 * @return {@link CurrentWeather}
	 */
	@RequestMapping(value = "/current")
	public CurrentWeather getCurrentWeather(@RequestParam(value = "city", defaultValue = "Grenoble") String city,
			@RequestParam(value = "countryCode", defaultValue = "FR") String countryCode) {
		return weatherService.getCurrentWeather(city, countryCode);
	}

	/**
	 * Gets the forecast by hours.
	 * 
	 * @param city
	 *            the city
	 * @param countryCode
	 *            the country code
	 * @return {@link WeatherForecasts}
	 */
	@RequestMapping(value = "/forecastByHours")
	public WeatherForecasts getForecastByHours(@RequestParam(value = "city", defaultValue = "Grenoble") String city,
			@RequestParam(value = "countryCode", defaultValue = "FR") String countryCode) {
		return weatherService.getForecastByHours(city, countryCode);
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
	public WeatherForecastView getWeatherForecastView(
			@RequestParam(value = "city", defaultValue = "Grenoble") String city,
			@RequestParam(value = "countryCode", defaultValue = "FR") String countryCode) {
		return weatherService.getWeatherForecastView(city, countryCode);
	}

}
