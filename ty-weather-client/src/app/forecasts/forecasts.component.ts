import { Component, OnInit } from '@angular/core';

import { Observable } from 'rxjs/Observable';
import {Message} from 'primeng/components/common/api';

import { ForecastService } from '../forecast.service';
import { Forecast } from '../forecast';


@Component({
  selector: 'app-forecasts',
  templateUrl: './forecasts.component.html',
  styleUrls: ['./forecasts.component.css']
})
export class ForecastsComponent implements OnInit {

  forecastList: Forecast[] = [];
  
  msgs: Message[] = [];

  constructor(private forecastService: ForecastService) { }

  ngOnInit() {
    this.getForecastList();
  }

  getForecastList(): void {
    this.forecastService.getForecastList().subscribe(forecastList => this.forecastListCallback(forecastList));
  },
  
  forecastListCallback(forecastList): void {
    this.forecastList = forecastList;
    this.msgs.push({severity:'info', summary:'Loading succed!', detail:'Forecasts available'});
  }

}
