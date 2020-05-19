import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewBrandTypeComponent } from './new-brand-type.component';

describe('NewBrandTypeComponent', () => {
  let component: NewBrandTypeComponent;
  let fixture: ComponentFixture<NewBrandTypeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewBrandTypeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewBrandTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
