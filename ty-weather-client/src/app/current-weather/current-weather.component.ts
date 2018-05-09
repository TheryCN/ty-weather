import { Component, OnInit } from '@angular/core';

import { Observable } from 'rxjs/Observable';
import { Message } from 'primeng/components/common/api';
import { WeatherService } from '../weather.service';
import { MessageService } from 'primeng/components/common/messageservice';

import { CurrentWeather } from '../entity/currentWeather';
import { WeatherApiConfig } from '../entity/weatherApiConfig';

import Map from 'ol/map';
import View from 'ol/view';
import Proj from 'ol/proj';
import TileLayer from 'ol/layer/tile';
import OSMSource from 'ol/source/OSM';
import XYZ from 'ol/source/XYZ';

@Component({
  selector: 'app-current-weather',
  templateUrl: './current-weather.component.html',
  styleUrls: ['./current-weather.component.css']
})
export class CurrentWeatherComponent implements OnInit {

  apiConfig: WeatherApiConfig;

  currentWeather: CurrentWeather;

  map: Map;

  view: View;

  constructor(private weatherService: WeatherService, private messageService: MessageService) { }

  ngOnInit() {
    this.initWeather();
  }

  initWeather(): void {
    var self = this;
    this.weatherService.getApiConfig().subscribe(apiConfig => {
      this.apiConfig = apiConfig;
      this.weatherService.getCurrentWeather().subscribe(currentWeather => this.currentWeatherCallback(currentWeather));
    });
  }

  currentWeatherCallback(currentWeather): void {
    this.currentWeather = currentWeather;
    this.initMap();
    this.messageService.add({severity:'success', summary:'Loading succed!', detail:'Current weather available'});
  }

  initMap(): void {
    this.view = new View({
      center: Proj.fromLonLat([this.currentWeather.coord.lon, this.currentWeather.coord.lat]),
      zoom: 8,
    });

    this.map = new Map({
      target: 'map',
      layers: [
        new TileLayer({
          source: new OSMSource()
        }),
        new TileLayer({
          source: new XYZ({url:"https://tile.openweathermap.org/map/temp_new/{z}/{x}/{y}.png?appid={apiKey}".replace("{apiKey}", this.apiConfig.key)})
        })
      ],
      view: this.view
    });
  }

}
