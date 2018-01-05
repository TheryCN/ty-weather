package github.com.therycn.service.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;

/**
 * Google client service factory.
 * 
 * @author TheryLeopard
 *
 */
@Service
public class GoogleServiceFactory {

    @Autowired
    private final OAuth2ClientContext oauth2ClientContext;

    private Calendar calendarService;

    private String currentToken;

    public GoogleServiceFactory(OAuth2ClientContext oauth2ClientContext) {
        this.oauth2ClientContext = oauth2ClientContext;
    }

    /**
     * Gets the calendar service depending on the token.
     * 
     * @return {@link Calendar}
     */
    public Calendar getCalendarService() {
        String newToken = oauth2ClientContext.getAccessToken().getValue();
        if (!newToken.equals(currentToken)) {
            GoogleCredential credential = new GoogleCredential().setAccessToken(newToken);
            calendarService = new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(),
                    credential).setApplicationName("applicationName").build();
        }
        return calendarService;
    }

}
