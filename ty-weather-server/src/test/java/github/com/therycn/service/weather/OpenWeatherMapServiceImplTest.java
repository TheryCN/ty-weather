package github.com.therycn.service.weather;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import github.com.therycn.entity.WeatherForecastView;
import github.com.therycn.entity.openweather.Forecast;
import github.com.therycn.entity.openweather.Main;
import github.com.therycn.entity.openweather.WeatherForecastResponse;

/**
 * Test class {@link OpenWeatherMapServiceImpl}.
 * 
 * @author TheryLeopard
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class OpenWeatherMapServiceImplTest {

    @Mock
    private OpenWeatherMapClient client;

    @InjectMocks
    private OpenWeatherMapServiceImpl service;

    /**
     * Test {@link OpenWeatherMapServiceImpl#getForecast(String, String)}.
     * Checks conversions between date & degree unit.
     */
    @Test
    public void testGetForecast() {
        // Given
        Date expectedForecastDate = Date.from(LocalDateTime.of(2017, 12, 27, 18, 40).toInstant(ZoneOffset.UTC));
        WeatherForecastView expectedForecastView = new WeatherForecastView(5f, 0f, 10f, 20f, expectedForecastDate);

        Main main = mock(Main.class);
        when(main.getHumidity()).thenReturn(20f);
        // Kelvin
        when(main.getTemp()).thenReturn(278.15f);
        when(main.getTempMin()).thenReturn(273.15f);
        when(main.getTempMax()).thenReturn(283.15f);

        Forecast forecast = mock(Forecast.class);
        // UTC Wed Dec 27 2017 18:40:00
        when(forecast.getTime()).thenReturn(1514400000l);
        when(forecast.getMain()).thenReturn(main);

        WeatherForecastResponse clientResponse = mock(WeatherForecastResponse.class);
        when(clientResponse.getForecastList()).thenReturn(Arrays.asList(forecast));

        when(client.getForecast(Mockito.anyString(), Mockito.anyString())).thenReturn(clientResponse);

        // When
        List<WeatherForecastView> forecastViewList = service.getForecast("Grenoble", "FR");

        // Then
        assertThat(forecastViewList.size(), IsEqual.equalTo(1));
        assertThat(forecastViewList.get(0), IsEqual.equalTo(expectedForecastView));
    }
}
