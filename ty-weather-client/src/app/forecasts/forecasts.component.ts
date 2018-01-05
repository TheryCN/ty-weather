import { Component, OnInit } from '@angular/core';

import { ForecastService } from '../forecast.service';
import { Forecast } from '../forecast';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'app-forecasts',
  templateUrl: './forecasts.component.html',
  styleUrls: ['./forecasts.component.css']
})
export class ForecastsComponent implements OnInit {

  forecastList: Forecast[] = [];

  constructor(private forecastService: ForecastService) { }

  ngOnInit() {
    this.getForecastList();
  }

  getForecastList(): void {
    this.forecastService.getForecastList().subscribe(forecastList => this.forecastList = forecastList);
  }

}
