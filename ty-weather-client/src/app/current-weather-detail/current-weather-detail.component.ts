import { Component, OnInit, Input } from '@angular/core';

import { CurrentWeather } from '../entity/currentWeather';

@Component({
  selector: 'app-current-weather-detail',
  templateUrl: './current-weather-detail.component.html',
  styleUrls: ['./current-weather-detail.component.css']
})
export class CurrentWeatherDetailComponent implements OnInit {

  @Input() currentWeather: CurrentWeather;

  weatherClass:string;

  constructor() { }

  ngOnInit() {

  }

  ngOnChanges(changeRecord) {
    if(this.currentWeather != undefined){
      this.initWeatherIcon();
    }
  }

  initWeatherIcon(): void {
    switch(this.currentWeather.weather[0].main) {
      case "Clouds":
        this.weatherClass = "icon-cloudy";
        break;
      default:
        this.weatherClass = "icon-sun";
        break;
    }

  }
}
