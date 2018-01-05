import { Component, OnInit, Input } from '@angular/core';
import { Forecast } from '../forecast';

@Component({
  selector: 'app-forecast',
  templateUrl: './forecast.component.html',
  styleUrls: ['./forecast.component.css']
})
export class ForecastComponent implements OnInit {
  
  @Input() forecast: Forecast;

  constructor() { }

  ngOnInit() {
  }

}
