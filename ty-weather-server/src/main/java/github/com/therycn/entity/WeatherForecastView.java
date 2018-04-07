package github.com.therycn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * WeatherForecastView;
 * 
 * @author TheryLeopard
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class WeatherForecastView {

	/** Temperature �C. */
	private double tempAvg;

	/** Temperature min �C. */
	private double tempMin;

	/** Temperature max �C. */
	private double tempMax;

	/** Humidity (%). */
	private double humidityAvg;

	private double pressureAvg;

}
