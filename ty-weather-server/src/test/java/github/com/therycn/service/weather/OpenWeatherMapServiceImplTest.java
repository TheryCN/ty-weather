package github.com.therycn.service.weather;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

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
import github.com.therycn.entity.openweather.WeatherForecasts;

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
	 * Test
	 * {@link OpenWeatherMapServiceImpl#getWeatherForecastView(String, String)}.
	 */
	@Test
	public void testGetWeatherForecastView() {
		// Given
		WeatherForecastView expectedForecastView = new WeatherForecastView(10d, 2d, 15d, 80d, 800d);

		Main main = mock(Main.class);
		when(main.getHumidity()).thenReturn(80f);
		when(main.getTemp()).thenReturn(10f);
		when(main.getTempMin()).thenReturn(2f);
		when(main.getTempMax()).thenReturn(15f);
		when(main.getPressure()).thenReturn(800f);

		Forecast forecast = mock(Forecast.class);
		// UTC Wed Dec 27 2017 18:40:00
		when(forecast.getTime()).thenReturn(1514400000l);
		when(forecast.getMain()).thenReturn(main);

		WeatherForecasts clientResponse = mock(WeatherForecasts.class);
		when(clientResponse.getForecastList()).thenReturn(Arrays.asList(forecast));

		when(client.getFiveDaysPerThreeHoursForecast(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(clientResponse);

		// When
		WeatherForecastView forecastView = service.getWeatherForecastView("Grenoble", "FR");

		// Then
		assertThat(forecastView, IsEqual.equalTo(expectedForecastView));
	}
}
