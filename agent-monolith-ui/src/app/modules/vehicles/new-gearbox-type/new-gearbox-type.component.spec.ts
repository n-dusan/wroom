import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewGearboxTypeComponent } from './new-gearbox-type.component';

describe('NewGearboxTypeComponent', () => {
  let component: NewGearboxTypeComponent;
  let fixture: ComponentFixture<NewGearboxTypeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewGearboxTypeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewGearboxTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
