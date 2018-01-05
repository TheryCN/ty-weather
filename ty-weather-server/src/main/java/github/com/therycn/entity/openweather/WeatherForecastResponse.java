package github.com.therycn.entity.openweather;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * OpenWeatherMap forecast response.
 * 
 * @author TheryLeopard
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherForecastResponse {

	private City city;

	@JsonProperty("list")
	private List<Forecast> forecastList;
}