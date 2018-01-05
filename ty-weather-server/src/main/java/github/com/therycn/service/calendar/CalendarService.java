package github.com.therycn.service.calendar;

import java.util.List;

import com.google.api.services.calendar.model.Event;

/**
 * Calendar Service.
 * 
 * @author TheryLeopard
 *
 */
public interface CalendarService {

    /**
     * Gets calendar id from his summary.
     * 
     * @param calendarSummary
     *            the summary
     * @return the id
     */
    String findCalendarIdBySummary(String calendarSummary);

    /**
     * Creates a new calendar from the given summary.
     * 
     * @param summary
     *            the summary
     * @return the calendar id
     */
    String addCalendar(String summary);

    /**
     * Update existing event.
     * 
     * @param calendarId
     *            the calendar id
     * @param eventId
     *            the event id
     * @param event
     *            {@link Event}
     * @return {@link Event}
     */
    Event updateEvent(String calendarId, String eventId, Event event);

    /**
     * Add new event.
     * 
     * @param calendarId
     *            the calendar id
     * @param event
     *            {@link Event}
     * @return {@link Event}
     */
    Event addEvent(String calendarId, Event event);

    /**
     * Gets upcoming events.
     * 
     * @return {@link Event} list
     */
    List<Event> getUpcomingEvents();

    /**
     * Gets upcoming events from the given calendar id.
     * 
     * @param calendarId
     *            the calendar id (where primary is the default)
     * @return {@link Event} list
     */
    List<Event> getUpcomingEvents(String calendarId);

}
