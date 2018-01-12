import { Component, OnInit } from '@angular/core';
import { CalendarService } from '../calendar.service';
import { MessageService } from 'primeng/components/common/messageservice';

@Component({
  selector: 'app-calendar-update',
  templateUrl: './calendar-update.component.html',
  styleUrls: ['./calendar-update.component.css']
})
export class CalendarUpdateComponent implements OnInit {

  constructor(private calendarService: CalendarService, private messageService: MessageService) { }

  ngOnInit() {
  }
  
  update() {
    this.calendarService.updateWeatherCalendar().subscribe(data => {
        this.messageService.add({severity:'success', summary:'Weather calendar update success !'});
    })
  }

}
