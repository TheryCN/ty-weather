import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { CurrentWeather } from './entity/currentWeather';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class WeatherService {

  private currentWeatherUrl = '/api/weather/current';

  constructor(private http: HttpClient) { }

  getCurrentWeather(): Observable<CurrentWeather> {
    return this.http.get<CurrentWeather>(this.currentWeatherUrl);
  }

}
