package github.com.therycn.entity.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

	/** weather */
	private Weather weather;

	/** main */
	private Main main;

	/** wind */
	private Wind wind;

}
