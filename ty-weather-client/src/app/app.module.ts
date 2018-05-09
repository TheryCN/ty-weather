import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { GrowlModule } from 'primeng/primeng';
import { ButtonModule } from 'primeng/primeng';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MessageService } from 'primeng/components/common/messageservice';

import { AppComponent } from './app.component';
import { CalendarUpdateComponent } from './calendar-update/calendar-update.component';
import { CalendarService } from './calendar.service';
import { CurrentWeatherComponent } from './current-weather/current-weather.component';
import { WeatherService } from './weather.service';

@NgModule({
  declarations: [
    AppComponent,
    CalendarUpdateComponent,
    CurrentWeatherComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    GrowlModule,
    ButtonModule,
    BrowserAnimationsModule
  ],
  providers: [CalendarService, MessageService, WeatherService],
  bootstrap: [AppComponent]
})
export class AppModule { }
