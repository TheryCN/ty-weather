import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';

@Injectable()
export class CalendarService {

  private updateWeatherCalendarUrl = '/api/calendar/update-weather';

  constructor(private http: HttpClient) { }
  
  updateWeatherCalendar(): Observable<Object> {
    return this.http.get(this.updateWeatherCalendarUrl);
  }

}
