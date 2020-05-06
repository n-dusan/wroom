import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewFuelTypeComponent } from './new-fuel-type.component';

describe('NewFuelTypeComponent', () => {
  let component: NewFuelTypeComponent;
  let fixture: ComponentFixture<NewFuelTypeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewFuelTypeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewFuelTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
