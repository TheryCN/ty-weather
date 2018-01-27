package github.com.therycn.service.calendar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import github.com.therycn.entity.WeatherForecastView;
import github.com.therycn.service.weather.WeatherService;

/**
 * Calendar weather update service.
 * 
 * @author TheryLeopard
 *
 */
@Service
public class WeatherUpdateService {

	private CalendarService calendarService;

	private WeatherService weatherService;

	@Value("${weather.calendar.summary}")
	private String weatherCalendarSummary;

	public WeatherUpdateService(CalendarService calendarService, WeatherService weatherService) {
		this.calendarService = calendarService;
		this.weatherService = weatherService;
	}

	/**
	 * Update calendar weather.
	 * 
	 * @param city
	 *            the city
	 * @param countryCode
	 *            the country code
	 * @return {@link Event} list
	 */
	public List<Event> update(String city, String countryCode) {
		List<WeatherForecastView> forecastList = weatherService.getForecast(city, countryCode);

		String weatherCalendarId = calendarService.findCalendarIdBySummary(weatherCalendarSummary);

		// If not found, create it
		if (weatherCalendarId == null) {
			weatherCalendarId = calendarService.addCalendar(weatherCalendarSummary);
		}

		// Add events
		List<Event> upcomingEventList = calendarService.getUpcomingEvents(weatherCalendarId);

		List<Event> eventList = new ArrayList<>();
		for (WeatherForecastView forecast : forecastList) {
			eventList.add(createOrUpdateEvent(forecast, upcomingEventList, weatherCalendarId));
		}

		return eventList;
	}

	private Event createOrUpdateEvent(WeatherForecastView forecast, List<Event> upcomingEventList,
			String weatherCalendarId) {
		// Create event
		Event event = new Event().setSummary(Math.round(forecast.getTemp() * 100) / 100 + "°C");

		EventDateTime start = new EventDateTime().setDateTime(new DateTime(forecast.getDate()));
		event.setStart(start);

		EventDateTime end = new EventDateTime().setDateTime(new DateTime(forecast.getDate()));
		event.setEnd(end);

		List<Event> existingEventsAtSameTime = upcomingEventList.stream().filter(
				upcomingEvent -> upcomingEvent.getStart().getDateTime().getValue() == forecast.getDate().getTime())
				.collect(Collectors.toList());

		if (!CollectionUtils.isEmpty(existingEventsAtSameTime)) {
			// Update the first
			Event existingEvent = existingEventsAtSameTime.get(0);
			event = calendarService.updateEvent(weatherCalendarId, existingEvent.getId(), event);

		} else {
			// Add a new one
			event = calendarService.addEvent(weatherCalendarId, event);
		}

		return event;
	}

}
