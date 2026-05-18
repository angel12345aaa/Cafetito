import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';

@Component({
  standalone: false,
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrls: ['./app.css']
})
export class AppComponent {

  title = 'cafetito-app';

  mostrarNavbar = true;

  constructor(private router: Router) {

    this.router.events.subscribe(event => {

      if (event instanceof NavigationEnd) {

        this.mostrarNavbar = event.url !== '/login';

      }

    });

  }
}