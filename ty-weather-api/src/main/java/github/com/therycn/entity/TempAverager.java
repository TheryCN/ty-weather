package github.com.therycn.entity;

import github.com.therycn.entity.openweather.Main;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Temperature Averager.
 * 
 * @author THERY
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TempAverager {

	private int count;

	private float tempSum;

	private float pressureSum;

	private float humiditySum;

	/**
	 * Accumulator of {@link Main}.
	 * 
	 * @param main
	 *            {@link Main}
	 * @return {@link TempAverager}
	 */
	public TempAverager accept(Main main) {
		count++;
		tempSum += main.getTemp();
		pressureSum += main.getPressure();
		humiditySum += main.getHumidity();

		return this;
	}

	/**
	 * Combiner of {@link TempAverager}.
	 * 
	 * @param other
	 *            {@link TempAverager}
	 * @return {@link TempAverager}
	 */
	public TempAverager combine(TempAverager other) {
		count += other.getCount();
		tempSum += other.getTempSum();
		pressureSum += other.getPressureSum();
		humiditySum += other.getHumiditySum();

		return this;
	}

	public double getTempAvg() {
		return tempSum / count;
	}

	public double getPressureAvg() {
		return pressureSum / count;
	}

	public double getHumidityAvg() {
		return humiditySum / count;
	}

}
