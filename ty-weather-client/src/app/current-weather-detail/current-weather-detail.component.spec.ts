import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CurrentWeatherDetailComponent } from './current-weather-detail.component';

describe('CurrentWeatherDetailComponent', () => {
  let component: CurrentWeatherDetailComponent;
  let fixture: ComponentFixture<CurrentWeatherDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CurrentWeatherDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CurrentWeatherDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
