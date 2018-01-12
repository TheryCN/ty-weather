import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { GrowlModule } from 'primeng/primeng';
import { ButtonModule } from 'primeng/primeng';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MessageService } from 'primeng/components/common/messageservice';

import { AppComponent } from './app.component';
import { ForecastComponent } from './forecast/forecast.component';
import { ForecastsComponent } from './forecasts/forecasts.component';
import { ForecastService } from './forecast.service';
import { CalendarUpdateComponent } from './calendar-update/calendar-update.component';
import { CalendarService } from './calendar.service';

@NgModule({
  declarations: [
    AppComponent,
    ForecastComponent,
    ForecastsComponent,
    CalendarUpdateComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    GrowlModule,
    ButtonModule,
    BrowserAnimationsModule
  ],
  providers: [ForecastService, CalendarService, MessageService],
  bootstrap: [AppComponent]
})
export class AppModule { }
