package github.com.therycn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * Weather Application.
 * 
 * @author TheryLeopard
 *
 */
@SpringBootApplication
@PropertySource("classpath:weather-api.properties")
public class WeatherApp {

    public static void main(String[] args) {
        SpringApplication.run(WeatherApp.class, args);
    }

}
