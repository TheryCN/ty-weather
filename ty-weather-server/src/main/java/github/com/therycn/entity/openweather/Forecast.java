package github.com.therycn.entity.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * OpenWeatherMap Forecast.
 * 
 * @author TheryLeopard
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Forecast {

	/** Time of data forecasted, unix, UTC. */
	@JsonProperty("dt")
	private long time;

	private Main main;

}
