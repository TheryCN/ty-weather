package github.com.therycn.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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

    /** Temperature °C. */
    private double temp;

    /** Temperature min °C. */
    private double tempMin;

    /** Temperature max °C. */
    private double tempMax;

    /** Humidity (%). */
    private double humidity;

    /** Formatted date in UTC. */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date date;
}
