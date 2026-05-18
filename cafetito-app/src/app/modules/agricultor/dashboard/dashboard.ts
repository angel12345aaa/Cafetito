import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard-agricultor',
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css']
})
export class DashboardAgricultorComponent {

  constructor(private router: Router) {}

  irPesajes(): void {
    this.router.navigate(['/agricultor/pesajes']);
  }
}