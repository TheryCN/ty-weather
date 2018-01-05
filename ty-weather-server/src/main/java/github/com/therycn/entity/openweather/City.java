package github.com.therycn.entity.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * OpenWeatherMap City.
 * 
 * @author TheryLeopard
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class City {

	private Long id;

	private String name;

}
