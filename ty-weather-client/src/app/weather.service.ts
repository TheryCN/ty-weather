import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { CurrentWeather } from './entity/currentWeather';
import { WeatherApiConfig } from './entity/weatherApiConfig';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class WeatherService {

  private serviceUrl = '/api/weather/';

  constructor(private http: HttpClient) { }

  getApiConfig(): Observable<WeatherApiConfig> {
    return this.http.get<WeatherApiConfig>(this.serviceUrl + 'config');
  }

  getCurrentWeather(): Observable<CurrentWeather> {
    return this.http.get<CurrentWeather>(this.serviceUrl + 'current');
  }

}
