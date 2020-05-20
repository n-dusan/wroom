import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SignupRequestSentComponent } from './signup-request-sent.component';

describe('SignupRequestSentComponent', () => {
  let component: SignupRequestSentComponent;
  let fixture: ComponentFixture<SignupRequestSentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SignupRequestSentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SignupRequestSentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
