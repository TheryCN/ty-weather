package github.com.therycn.service.weather;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github.com.therycn.entity.WeatherForecastView;
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
     * github.com.therycn.service.WeatherService#getForecast(java.lang.String,
     * java.lang.String)
     */
    @Override
    public List<WeatherForecastView> getForecast(String city, String countryCode) {
        return toWeatherForecastViewList(client.getForecast(city, countryCode));
    }

    public List<WeatherForecastView> toWeatherForecastViewList(WeatherForecastResponse response) {
        return response.getForecastList().stream().map(w -> toWeatherForecastView(w)).collect(Collectors.toList());
    }

    private WeatherForecastView toWeatherForecastView(Forecast forecast) {
        // Format date
        LocalDateTime dateUtc = LocalDateTime.ofEpochSecond(forecast.getTime(), 0, ZoneOffset.UTC);

        return new WeatherForecastView(getCelsius(forecast.getMain().getTemp()),
                getCelsius(forecast.getMain().getTempMin()), getCelsius(forecast.getMain().getTempMax()),
                forecast.getMain().getHumidity(), Date.from(dateUtc.toInstant(ZoneOffset.UTC)));
    }

    private float getCelsius(float kelvin) {
        return kelvin - 273.15f;
    }

}
