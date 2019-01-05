package github.com.therycn.service.calendar;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import github.com.therycn.entity.openweather.Forecast;
import github.com.therycn.entity.openweather.Main;
import github.com.therycn.entity.openweather.WeatherForecasts;
import github.com.therycn.service.weather.WeatherService;

/**
 * Test class {@link WeatherUpdateService}.
 * 
 * @author TheryLeopard
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class WeatherUpdateServiceTest {

	private static final String WEATHER_CALENDAR_ID = "weatherCalendarId";

	private static final String COUNTRYCODE = "FR";

	private static final String CITY = "Grenoble";

	@Mock
	private CalendarService calendarService;

	@Mock
	private WeatherService weatherService;

	@InjectMocks
	private WeatherUpdateService updateServie;

	@Before
	public void init() {
		ReflectionTestUtils.setField(updateServie, "weatherCalendarSummary", "");
	}

	/**
	 * Test update with a new weather calendar.
	 */
	@Test
	public void testUpdateNewCalendar() {
		// Given
		Instant instant = LocalDateTime.of(2018, 1, 4, 18, 0).toInstant(ZoneOffset.UTC);
		WeatherForecasts forecasts = getForecasts(instant);
		when(weatherService.getForecastByHours(CITY, COUNTRYCODE)).thenReturn(forecasts);

		when(calendarService.findCalendarIdBySummary(anyString())).thenReturn(null);
		when(calendarService.addCalendar(anyString())).thenReturn(WEATHER_CALENDAR_ID);
		when(calendarService.getUpcomingEvents(anyString())).thenReturn(new ArrayList<>());

		// Returns the second parameter
		when(calendarService.addEvent(anyString(), any())).thenAnswer(i -> i.getArguments()[1]);

		// When
		List<Event> eventList = updateServie.update(CITY, COUNTRYCODE);

		// Then
		assertThat(eventList.size(), IsEqual.equalTo(1));
		verify(calendarService, times(1)).addCalendar(anyString());
		verify(calendarService, times(1)).addEvent(anyString(), any());
	}

	/**
	 * Test update with an existing weather calendar.
	 */
	@Test
	public void testUpdateExistingCalendar() {
		// Given
		Instant instant = LocalDateTime.of(2018, 1, 4, 18, 0).toInstant(ZoneOffset.UTC);
		WeatherForecasts forecasts = getForecasts(instant);
		when(weatherService.getForecastByHours(CITY, COUNTRYCODE)).thenReturn(forecasts);

		when(calendarService.findCalendarIdBySummary(anyString())).thenReturn(WEATHER_CALENDAR_ID);

		Event event = new Event();
		event.setId("");
		event.setStart(new EventDateTime());
		event.getStart().setDateTime(new DateTime(Date.from(instant)));
		when(calendarService.getUpcomingEvents(anyString())).thenReturn(Arrays.asList(event));

		// Returns the second third
		when(calendarService.updateEvent(anyString(), anyString(), any())).thenAnswer(i -> i.getArguments()[2]);

		// When
		List<Event> eventList = updateServie.update(CITY, COUNTRYCODE);

		// Then
		assertThat(eventList.size(), IsEqual.equalTo(1));
		verify(calendarService, times(1)).updateEvent(anyString(), anyString(), any());
	}

	private WeatherForecasts getForecasts(Instant instant) {
		WeatherForecasts forecasts = mock(WeatherForecasts.class);

		Forecast forecast = Mockito.mock(Forecast.class);
		// Time should be in seconds
		when(forecast.getTime()).thenReturn(instant.toEpochMilli() / 1000);
		Main main = Mockito.mock(Main.class);
		Mockito.when(main.getTemp()).thenReturn(5f);

		when(forecast.getMain()).thenReturn(main);
		when(forecasts.getForecastList()).thenReturn(Arrays.asList(forecast));

		return forecasts;
	}
}
