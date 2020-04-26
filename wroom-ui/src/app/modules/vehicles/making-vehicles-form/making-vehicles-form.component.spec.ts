import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MakingVehiclesFormComponent } from './making-vehicles-form.component';

describe('MakingVehiclesFormComponent', () => {
  let component: MakingVehiclesFormComponent;
  let fixture: ComponentFixture<MakingVehiclesFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MakingVehiclesFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MakingVehiclesFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
