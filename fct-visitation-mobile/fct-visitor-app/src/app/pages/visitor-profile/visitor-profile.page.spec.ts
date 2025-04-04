import { ComponentFixture, TestBed } from '@angular/core/testing';
import { VisitorProfilePage } from './visitor-profile.page';

describe('VisitorProfilePage', () => {
  let component: VisitorProfilePage;
  let fixture: ComponentFixture<VisitorProfilePage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(VisitorProfilePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
