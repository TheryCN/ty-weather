import { Component, OnInit } from '@angular/core';

import { Observable } from 'rxjs/Observable';
import {Message} from 'primeng/components/common/api';

import { WeatherService } from '../weather.service';
import { CurrentWeather } from '../entity/currentWeather';

@Component({
  selector: 'app-current-weather',
  templateUrl: './current-weather.component.html',
  styleUrls: ['./current-weather.component.css']
})
export class CurrentWeatherComponent implements OnInit {

  currentWeather: CurrentWeather;

  msgs: Message[] = [];

  constructor(private weatherService: WeatherService) { }

  ngOnInit() {
    this.setCurrentWeather();
  }

  setCurrentWeather(): void {
    this.weatherService.getCurrentWeather().subscribe(currentWeather => this.currentWeatherCallback(currentWeather));
  }

  currentWeatherCallback(currentWeather): void {
    this.currentWeather = currentWeather;
    this.msgs.push({severity:'info', summary:'Loading succed!', detail:'Current weather available'});
  }

}
