package github.com.therycn.entity.openweather;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Current Weather Response.
 * 
 * @author THERY
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeatherResponse {

	/** name City name */
	private String name;

	/** coord */
	private Coordinate coord;

	/** weather (more info Weather condition codes) */
	@JsonProperty("weather")
	private List<Weather> weatherList;

	/** main */
	private Main main;

	/** wind */
	private Wind wind;

}
