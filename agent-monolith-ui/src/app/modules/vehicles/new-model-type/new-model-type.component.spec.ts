import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewModelTypeComponent } from './new-model-type.component';

describe('NewModelTypeComponent', () => {
  let component: NewModelTypeComponent;
  let fixture: ComponentFixture<NewModelTypeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewModelTypeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewModelTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
