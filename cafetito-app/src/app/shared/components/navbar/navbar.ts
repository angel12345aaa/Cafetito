import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth';
import { Rol } from '../../../core/models/models';

@Component({
  standalone: false,
  selector: 'app-navbar',
  templateUrl: './navbar.html',
  styleUrls: ['./navbar.css']
})
export class NavbarComponent implements OnInit {

  tabs: { label: string; ruta: string }[] = [];

  nombreUsuario = '';
  rolLabel = '';

  constructor(
    public authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const user = this.authService.currentUser;
    const rol = this.authService.rol;

    this.nombreUsuario = user?.usuario || 'Usuario';
    this.rolLabel = this.obtenerRolLabel(rol);

    this.cargarTabs(rol);
  }

  cargarTabs(rol: Rol | null): void {

    if (rol === Rol.AGRICULTOR) {
      this.tabs = [
        { label: 'Dashboard', ruta: '/agricultor' },
        { label: 'Pesajes', ruta: '/agricultor/pesajes' },
        { label: 'Transportes', ruta: '/agricultor/transportes' },
        { label: 'Transportistas', ruta: '/agricultor/transportistas' }
      ];
      return;
    }

    if (rol === Rol.BENEFICIO) {
      this.tabs = [
        { label: 'Dashboard', ruta: '/beneficio' },
        { label: 'Cuentas', ruta: '/beneficio/cuentas' },
        { label: 'Transportes', ruta: '/beneficio/transportes' },
        { label: 'Transportistas', ruta: '/beneficio/transportistas' },
        { label: 'Agricultores', ruta: '/beneficio/agricultores' }
      ];
      return;
    }

    if (rol === Rol.PESOCABAL) {
      this.tabs = [
        { label: 'Dashboard', ruta: '/pesocabal' },
        { label: 'Cuentas', ruta: '/pesocabal/cuentas' }
      ];
      return;
    }

    this.tabs = [];
  }

  obtenerRolLabel(rol: Rol | null): string {

    if (rol === Rol.AGRICULTOR) {
      return 'Agricultor';
    }

    if (rol === Rol.BENEFICIO) {
      return 'Beneficio';
    }

    if (rol === Rol.PESOCABAL) {
      return 'Peso Cabal';
    }

    return 'Sin rol';
  }

  isActive(ruta: string): boolean {
    return this.router.url === ruta;
  }

  cerrarSesion(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}