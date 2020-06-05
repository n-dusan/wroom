import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateBundleDialogComponent } from './create-bundle-dialog.component';

describe('CreateBundleDialogComponent', () => {
  let component: CreateBundleDialogComponent;
  let fixture: ComponentFixture<CreateBundleDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateBundleDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateBundleDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
