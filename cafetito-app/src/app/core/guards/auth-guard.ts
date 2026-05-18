import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot
} from '@angular/router';

import { AuthService } from '../services/auth';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {

    // Verifica token
    const token = this.authService.token;

    if (!token) {
      this.router.navigate(['/login']);
      return false;
    }

    // Obtiene roles permitidos
    const allowedRoles = route.data['roles'];

    // Rol actual
    const currentRol = this.authService.rol;

    // Validar rol
    if (allowedRoles && !allowedRoles.includes(currentRol)) {
      this.router.navigate(['/login']);
      return false;
    }

    return true;
  }
}