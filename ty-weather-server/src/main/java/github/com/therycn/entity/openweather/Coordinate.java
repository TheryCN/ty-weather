package github.com.therycn.entity.openweather;

import lombok.Data;

/**
 * Coordinate.
 * 
 * @author THERY
 *
 */
@Data
public class Coordinate {

	/** coord.lat City geo location, latitude */
	private long lat;

	/** coord.lon City geo location, longitude */
	private long lon;

}
