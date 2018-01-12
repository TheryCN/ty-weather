import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CalendarUpdateComponent } from './calendar-update.component';

describe('CalendarUpdateComponent', () => {
  let component: CalendarUpdateComponent;
  let fixture: ComponentFixture<CalendarUpdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CalendarUpdateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CalendarUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
