import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
	
import { Forecast } from './forecast';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class ForecastService {

  private forecastUrl = '/weather-forecast/forecast';

  constructor(private http: HttpClient) { }

  getForecastList(): Observable<Forecast[]> {
    return this.http.get<Forecast[]>(this.forecastUrl);
  }

}
