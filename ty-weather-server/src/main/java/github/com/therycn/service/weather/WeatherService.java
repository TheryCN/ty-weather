package github.com.therycn.service.weather;

import java.util.List;

import org.springframework.stereotype.Service;

import github.com.therycn.entity.WeatherForecastView;
import github.com.therycn.entity.openweather.CurrentWeatherResponse;

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
	 * @return {@link CurrentWeatherResponse}
	 */
	CurrentWeatherResponse getCurrentWeather(String city, String countryCode);

	/**
	 * Gets the forecast for the given city & country.
	 * 
	 * @param city
	 *            the city
	 * @param countryCode
	 *            the country code
	 * @return {@link WeatherForecastView} list
	 */
	List<WeatherForecastView> getForecast(String city, String countryCode);
}
