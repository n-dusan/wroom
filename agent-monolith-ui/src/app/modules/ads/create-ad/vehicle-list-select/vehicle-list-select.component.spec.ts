import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VehicleListSelectComponent } from './vehicle-list-select.component';

describe('VehicleListSelectComponent', () => {
  let component: VehicleListSelectComponent;
  let fixture: ComponentFixture<VehicleListSelectComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VehicleListSelectComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VehicleListSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
