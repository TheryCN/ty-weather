package github.com.therycn.service.weather;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github.com.therycn.entity.WeatherForecastView;
import github.com.therycn.entity.openweather.CurrentWeatherResponse;
import github.com.therycn.entity.openweather.Forecast;
import github.com.therycn.entity.openweather.WeatherForecastResponse;

/**
 * Weather Service Impl.
 * 
 * @author TheryLeopard
 *
 */
@Service
public class OpenWeatherMapServiceImpl implements WeatherService {

	@Autowired
	private OpenWeatherMapClient client;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * github.com.therycn.service.weather.WeatherService#getCurrentWeather(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public CurrentWeatherResponse getCurrentWeather(String city, String countryCode) {
		return client.getCurrentWeather(city, countryCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see github.com.therycn.service.WeatherService#getForecast(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<WeatherForecastView> getForecast(String city, String countryCode) {
		return toWeatherForecastViewList(client.getFiveDaysPerThreeHoursForecast(city, countryCode));
	}

	public List<WeatherForecastView> toWeatherForecastViewList(WeatherForecastResponse response) {
		return response.getForecastList().stream().map(w -> toWeatherForecastView(w)).collect(Collectors.toList());
	}

	private WeatherForecastView toWeatherForecastView(Forecast forecast) {
		// Format date
		LocalDateTime dateUtc = LocalDateTime.ofEpochSecond(forecast.getTime(), 0, ZoneOffset.UTC);

		return new WeatherForecastView(forecast.getMain().getTemp(), forecast.getMain().getTempMin(),
				forecast.getMain().getTempMax(), forecast.getMain().getHumidity(),
				Date.from(dateUtc.toInstant(ZoneOffset.UTC)));
	}

}
