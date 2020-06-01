import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VehicleOccupancyComponent } from './vehicle-occupancy.component';

describe('VehicleOccupancyComponent', () => {
  let component: VehicleOccupancyComponent;
  let fixture: ComponentFixture<VehicleOccupancyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VehicleOccupancyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VehicleOccupancyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
