import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { ForecastComponent } from './forecast/forecast.component';
import { ForecastsComponent } from './forecasts/forecasts.component';
import { ForecastService } from './forecast.service';


@NgModule({
  declarations: [
    AppComponent,
    ForecastComponent,
    ForecastsComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [ForecastService],
  bootstrap: [AppComponent]
})
export class AppModule { }
