package github.com.therycn.service.calendar;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import github.com.therycn.exception.ClientFailureException;
import lombok.extern.slf4j.Slf4j;

/**
 * Google Calendar Service Impl.
 * 
 * @author TheryLeopard
 *
 */
@Service
@Slf4j
public class GoogleCalendarServiceImpl implements CalendarService {

    /**
     * The client service depends on the token, use factory to get a new one in
     * this case.
     */
    private final GoogleServiceFactory googleFactory;

    public GoogleCalendarServiceImpl(GoogleServiceFactory googleFactory) {
        this.googleFactory = googleFactory;
    }

    /*
     * (non-Javadoc)
     * 
     * @see github.com.therycn.service.calendar.CalendarService#
     * findCalendarIdBySummary(java.lang.String)
     */
    @Override
    public String findCalendarIdBySummary(String calendarSummary) {
        try {
            String calendarId = null;
            CalendarList calendarList = googleFactory.getCalendarService().calendarList().list().execute();
            for (CalendarListEntry existingCalendar : calendarList.getItems()) {
                // Find it by summary
                if (existingCalendar.getSummary().equals(calendarSummary)) {
                    calendarId = existingCalendar.getId();
                }
            }

            return calendarId;
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
            throw new ClientFailureException(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * github.com.therycn.service.calendar.CalendarService#addCalendar(java.lang
     * .String)
     */
    @Override
    public String addCalendar(String summary) {
        try {
            com.google.api.services.calendar.model.Calendar weatherCalendar = new com.google.api.services.calendar.model.Calendar();
            weatherCalendar.setSummary(summary);

            return googleFactory.getCalendarService().calendars().insert(weatherCalendar).execute().getId();
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
            throw new ClientFailureException(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * github.com.therycn.service.calendar.CalendarService#updateEvent(java.lang
     * .String, java.lang.String, com.google.api.services.calendar.model.Event)
     */
    @Override
    public Event updateEvent(String calendarId, String eventId, Event event) {
        try {
            return googleFactory.getCalendarService().events().update(calendarId, eventId, event).execute();
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
            throw new ClientFailureException(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * github.com.therycn.service.calendar.CalendarService#addEvent(java.lang.
     * String, com.google.api.services.calendar.model.Event)
     */
    @Override
    public Event addEvent(String calendarId, Event event) {
        try {
            return googleFactory.getCalendarService().events().insert(calendarId, event).execute();
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
            throw new ClientFailureException(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * github.com.therycn.service.calendar.CalendarService#getUpcomingEvents()
     */
    @Override
    public List<Event> getUpcomingEvents() {
        try {
            // Retrieve an event
            Events events = googleFactory.getCalendarService().events().list("primary")
                    .setTimeMin(new DateTime(new Date())).execute();
            return events.getItems();
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
            throw new ClientFailureException(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * github.com.therycn.service.calendar.CalendarService#getUpcomingEvents(
     * java.lang.String)
     */
    @Override
    public List<Event> getUpcomingEvents(String calendarId) {
        try {
            // Retrieve an event
            Events events = googleFactory.getCalendarService().events().list(calendarId)
                    .setTimeMin(new DateTime(new Date())).execute();
            return events.getItems();
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
            throw new ClientFailureException(e.getMessage(), e);
        }
    }

}
