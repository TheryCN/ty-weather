package github.com.therycn.entity.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Main {

	/** Temperature in °K. */
	private float temp;

	@JsonProperty("temp_min")
	private float tempMin;

	@JsonProperty("temp_max")
	private float tempMax;

	private float pressure;

	/** Humidity in %. */
	private float humidity;

}