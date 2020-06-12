import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VehicleOccupancyListComponent } from './vehicle-occupancy-list.component';

describe('VehicleOccupancyListComponent', () => {
  let component: VehicleOccupancyListComponent;
  let fixture: ComponentFixture<VehicleOccupancyListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VehicleOccupancyListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VehicleOccupancyListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
