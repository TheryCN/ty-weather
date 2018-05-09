import { Component, OnInit } from '@angular/core';

import { Observable } from 'rxjs/Observable';
import {Message} from 'primeng/components/common/api';

import { WeatherService } from '../weather.service';
import { CurrentWeather } from '../entity/currentWeather';

import OlMap from 'ol/map';
import OlView from 'ol/view';
import OlProj from 'ol/proj';
import OlTileLayer from 'ol/layer/tile';
import OlOSMSource from 'ol/source/OSM';

@Component({
  selector: 'app-current-weather',
  templateUrl: './current-weather.component.html',
  styleUrls: ['./current-weather.component.css']
})
export class CurrentWeatherComponent implements OnInit {

  currentWeather: CurrentWeather;

  weatherClass:string;

  msgs: Message[] = [];

  map: OlMap;

  view: OlView;

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
    this.initWeatherIcon();
    this.initMap();
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

  initMap(): void {
    this.view = new OlView({
      center: OlProj.fromLonLat([this.currentWeather.coord.lon, this.currentWeather.coord.lat]),
      zoom: 12,
    });

    this.map = new OlMap({
      target: 'map',
      layers: [
        new OlTileLayer({
          source: new OlOSMSource()
        })
      ],
      view: this.view
    });
  }

}
