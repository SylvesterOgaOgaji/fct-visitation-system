import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'home',
    loadComponent: () => import('./home/home.page').then((m) => m.HomePage),
  },
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full',
  },
  {
    path: 'login',
    loadComponent: () => import('./pages/login/login.page').then( m => m.LoginPage)
  },
  {
    path: 'home',
    loadComponent: () => import('./pages/home/home.page').then( m => m.HomePage)
  },
  {
    path: 'visitor-profile',
    loadComponent: () => import('./pages/visitor-profile/visitor-profile.page').then( m => m.VisitorProfilePage)
  },
  {
    path: 'qr-scanner',
    loadComponent: () => import('./pages/qr-scanner/qr-scanner.page').then( m => m.QrScannerPage)
  },
  {
    path: 'security-alerts',
    loadComponent: () => import('./pages/security-alerts/security-alerts.page').then( m => m.SecurityAlertsPage)
  },
];
