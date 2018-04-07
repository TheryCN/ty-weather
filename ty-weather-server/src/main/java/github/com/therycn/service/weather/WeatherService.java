package github.com.therycn.service.weather;

import org.springframework.stereotype.Service;

import github.com.therycn.entity.WeatherForecastView;
import github.com.therycn.entity.openweather.CurrentWeather;
import github.com.therycn.entity.openweather.WeatherForecasts;

/**
 * Weather Service.
 * 
 * @author TheryLeopard
 *
 */
@Service
public interface WeatherService {

	/**
	 * Gets the current weather for the given city & country.
	 * 
	 * @param city
	 *            the city
	 * @param countryCode
	 *            the country code
	 * @return {@link CurrentWeather}
	 */
	CurrentWeather getCurrentWeather(String city, String countryCode);

	/**
	 * Gets the forecast by hours.
	 * 
	 * @param city
	 *            the city
	 * @param countryCode
	 *            the country code
	 * @return {@link WeatherForecasts}
	 */
	WeatherForecasts getForecastByHours(String city, String countryCode);

	/**
	 * Gets the forecast for the given city & country.
	 * 
	 * @param city
	 *            the city
	 * @param countryCode
	 *            the country code
	 * @return {@link WeatherForecastView}
	 */
	WeatherForecastView getWeatherForecastView(String city, String countryCode);
}
