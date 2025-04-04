import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonContent, IonHeader, IonTitle, IonToolbar } from '@ionic/angular/standalone';

@Component({
  selector: 'app-visitor-profile',
  templateUrl: './visitor-profile.page.html',
  styleUrls: ['./visitor-profile.page.scss'],
  standalone: true,
  imports: [IonContent, IonHeader, IonTitle, IonToolbar, CommonModule, FormsModule]
})
export class VisitorProfilePage implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
