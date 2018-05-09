package github.com.therycn.service.weather;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github.com.therycn.entity.TempAverager;
import github.com.therycn.entity.WeatherApiConfig;
import github.com.therycn.entity.WeatherForecastView;
import github.com.therycn.entity.openweather.CurrentWeather;
import github.com.therycn.entity.openweather.Forecast;
import github.com.therycn.entity.openweather.WeatherForecasts;

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
	 * @see github.com.therycn.service.weather.WeatherService#getApiConfig()
	 */
	@Override
	public WeatherApiConfig getApiConfig() {
		return new WeatherApiConfig(client.getAppId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * github.com.therycn.service.weather.WeatherService#getCurrentWeather(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public CurrentWeather getCurrentWeather(String city, String countryCode) {
		return client.getCurrentWeather(city, countryCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * github.com.therycn.service.weather.WeatherService#getForecastByHours(java.
	 * lang.String, java.lang.String)
	 */
	@Override
	public WeatherForecasts getForecastByHours(String city, String countryCode) {
		return client.getFiveDaysPerThreeHoursForecast(city, countryCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * github.com.therycn.service.weather.WeatherService#getWeatherForecastView(java
	 * .lang.String, java.lang.String)
	 */
	@Override
	public WeatherForecastView getWeatherForecastView(String city, String countryCode) {
		WeatherForecasts forecasts = client.getFiveDaysPerThreeHoursForecast(city, countryCode);
		List<Forecast> forecastList = forecasts.getForecastList();

		// Retrieve min & max values
		double tempMin = forecastList.stream().mapToDouble(f -> f.getMain().getTempMin()).min().getAsDouble();
		double tempMax = forecastList.stream().mapToDouble(f -> f.getMain().getTempMax()).max().getAsDouble();

		// Reduce using TempAverager instead of doing 3 average on the same stream
		TempAverager averager = forecastList.stream().map(f -> f.getMain())
				.filter(main -> main.getTemp() > tempMin && main.getTemp() < tempMax)
				.reduce(new TempAverager(), TempAverager::accept, TempAverager::combine);

		return new WeatherForecastView(averager.getTempAvg(), tempMin, tempMax, averager.getHumidityAvg(),
				averager.getPressureAvg());
	}

}
