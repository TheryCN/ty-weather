package github.com.therycn.service.weather;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import github.com.therycn.entity.openweather.WeatherForecastResponse;
import github.com.therycn.exception.ClientFailureException;
import lombok.extern.slf4j.Slf4j;

/**
 * OpenWeatherMap client.
 * 
 * @author TheryLeopard
 *
 */
@Service
@Slf4j
public class OpenWeatherMapClient {

    @Value("${app.id}")
    private String appId;

    @Value("${api.forecast.query}")
    private String apiForecastQuery;

    private RestTemplate restTemplate;

    public OpenWeatherMapClient(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    /**
     * Retrieve forecast data (5 day / 3 hour).
     * 
     * @param city
     *            the city name
     * @param countryCode
     *            the country code
     * @return {@link WeatherForecastResponse}
     */
    public WeatherForecastResponse getForecast(String city, String countryCode) {
        URI targetUrl = UriComponentsBuilder.fromUriString(apiForecastQuery).queryParam("q", city + "," + countryCode)
                .queryParam("APPID", appId).build().toUri();

        try {
            return restTemplate.getForObject(targetUrl, WeatherForecastResponse.class);
        } catch (RestClientResponseException e) {
            log.warn(e.getMessage(), e);
            throw new ClientFailureException(e.getMessage(), e);
        }

    }
}
