import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth';
import { Rol } from '../../../core/models/models';
@Component({ standalone: false, selector: 'app-navbar', templateUrl: './navbar.html' })
export class NavbarComponent implements OnInit {
  tabs: { label: string; ruta: string }[] = [];
  nombreUsuario = ''; rolLabel = '';
  constructor(public authService: AuthService, private router: Router) {}
  ngOnInit() {
    const user = this.authService.currentUser;
    const rol = this.authService.rol;
    if (user) {
      this.nombreUsuario = user.usuario || '';
      this.rolLabel = String(rol ?? '');
      this.cargarTabs(rol);
    }
  }
  cargarTabs(rol: Rol | null) {

  if (rol === Rol.AGRICULTOR) {

    this.tabs = [
      { label: 'Dashboard', ruta: '/agricultor' },
      { label: 'Pesajes', ruta: '/agricultor/pesajes' },
      { label: 'Transportes', ruta: '/agricultor/transportes' },
      { label: 'Transportistas', ruta: '/agricultor/transportistas' },
    ];

  }

  else if (rol === Rol.BENEFICIO) {

    this.tabs = [
      { label: 'Cuentas', ruta: '/beneficio/cuentas' },
      { label: 'Transportes', ruta: '/beneficio/transportes' },
      { label: 'Transportistas', ruta: '/beneficio/transportistas' },
      { label: 'Agricultores', ruta: '/beneficio/agricultores' }
    ];

  }

  else if (rol === Rol.PESOCABAL) {

    this.tabs = [
      { label: 'Cuentas', ruta: '/pesocabal/cuentas' }
    ];

  }
}
  isActive(ruta: string) { return this.router.url === ruta; }
  cerrarSesion() { this.authService.logout(); this.router.navigate(['/login']); }
}


