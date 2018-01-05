package github.com.therycn.service.calendar;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import github.com.therycn.entity.WeatherForecastView;
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

    /**
     * Test update with a new weather calendar.
     */
    @Test
    public void testUpdateNewCalendar() {
        // Given
        WeatherForecastView forecast = mock(WeatherForecastView.class);
        when(forecast.getDate()).thenReturn(Date.from(LocalDateTime.of(2018, 1, 4, 18, 0).toInstant(ZoneOffset.UTC)));
        when(forecast.getTemp()).thenReturn(5d);

        when(weatherService.getForecast(CITY, COUNTRYCODE)).thenReturn(Arrays.asList(forecast));

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
        WeatherForecastView forecast = mock(WeatherForecastView.class);
        when(forecast.getDate()).thenReturn(Date.from(LocalDateTime.of(2018, 1, 4, 18, 0).toInstant(ZoneOffset.UTC)));
        when(forecast.getTemp()).thenReturn(5d);

        WeatherForecastView nextForecast = mock(WeatherForecastView.class);
        when(nextForecast.getDate())
                .thenReturn(Date.from(LocalDateTime.of(2018, 1, 4, 19, 0).toInstant(ZoneOffset.UTC)));
        when(nextForecast.getTemp()).thenReturn(5d);

        when(weatherService.getForecast(CITY, COUNTRYCODE)).thenReturn(Arrays.asList(forecast, nextForecast));

        when(calendarService.findCalendarIdBySummary(anyString())).thenReturn(WEATHER_CALENDAR_ID);

        Event event = new Event();
        event.setStart(new EventDateTime());
        event.getStart().setDateTime(new DateTime(forecast.getDate()));
        when(calendarService.getUpcomingEvents(anyString())).thenReturn(Arrays.asList(event));

        // Returns the second parameter
        when(calendarService.addEvent(anyString(), any())).thenAnswer(i -> i.getArguments()[1]);
        when(calendarService.updateEvent(anyString(), anyString(), any())).thenAnswer(i -> i.getArguments()[1]);

        // When
        List<Event> eventList = updateServie.update(CITY, COUNTRYCODE);

        // Then
        assertThat(eventList.size(), IsEqual.equalTo(2));
        verify(calendarService, times(1)).updateEvent(anyString(), anyString(), any());
        verify(calendarService, times(1)).addEvent(anyString(), any());
    }
}
