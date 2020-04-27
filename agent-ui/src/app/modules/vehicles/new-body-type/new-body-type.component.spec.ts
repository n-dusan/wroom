import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewBodyTypeComponent } from './new-body-type.component';

describe('NewBodyTypeComponent', () => {
  let component: NewBodyTypeComponent;
  let fixture: ComponentFixture<NewBodyTypeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewBodyTypeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewBodyTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
