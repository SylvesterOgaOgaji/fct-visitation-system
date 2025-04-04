import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SecurityAlertsPage } from './security-alerts.page';

describe('SecurityAlertsPage', () => {
  let component: SecurityAlertsPage;
  let fixture: ComponentFixture<SecurityAlertsPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(SecurityAlertsPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
