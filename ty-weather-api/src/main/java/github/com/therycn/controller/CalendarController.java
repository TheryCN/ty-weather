package github.com.therycn.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.calendar.model.Event;

import github.com.therycn.service.calendar.CalendarService;
import github.com.therycn.service.calendar.WeatherUpdateService;

/**
 * Calendar Controller.
 * 
 * @author TheryLeopard
 *
 */
@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

	private CalendarService calendarService;

	private WeatherUpdateService weatherCalendarUpdateService;

	private OAuth2ClientContext context;

	public CalendarController(CalendarService calendarService, WeatherUpdateService weatherCalendarUpdateService,
			OAuth2ClientContext context) {
		this.calendarService = calendarService;
		this.weatherCalendarUpdateService = weatherCalendarUpdateService;
		this.context = context;
	}

	/**
	 * Gets upcoming events.
	 * 
	 * @return {@link Event} list
	 * @throws IOException
	 */
	@RequestMapping(value = "/events")
	public List<Event> getUpcomingEvents() {
		RestPreconditions.checkActiveGoogleAuth(context);
		return calendarService.getUpcomingEvents();
	}

	/**
	 * Updates weather calendar.
	 * 
	 * @param city
	 *            the city
	 * @param countryCode
	 *            the country code
	 * @return {@link Event} list
	 * @throws IOException
	 */
	@RequestMapping(value = "/update-weather")
	public List<Event> updateCalendarWeather(@RequestParam(value = "city", defaultValue = "Grenoble") String city,
			@RequestParam(value = "countryCode", defaultValue = "FR") String countryCode) {
		RestPreconditions.checkActiveGoogleAuth(context);
		return weatherCalendarUpdateService.update(city, countryCode);
	}

}
