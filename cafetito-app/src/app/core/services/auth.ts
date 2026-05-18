import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { LoginRequest, LoginResponse, Rol } from '../models/models';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly API = `${environment.apiGatewayUrl}${environment.endpoints.auth}`;

  constructor(private http: HttpClient, private router: Router) {}

  login(credentials: LoginRequest): Observable<LoginResponse> {
  console.log('URL LOGIN:', `${this.API}/login`);

  return this.http.post<LoginResponse>(`${this.API}/login`, credentials).pipe(
    tap(response => {
      localStorage.setItem('cafetito_user', JSON.stringify(response));
      localStorage.setItem('cafetito_token', response.token);
    })
  );
}

  logout(): void {
    localStorage.removeItem('cafetito_user');
    localStorage.removeItem('cafetito_token');
    this.router.navigate(['/login']);
  }

  get currentUser(): LoginResponse | null {
    const data = localStorage.getItem('cafetito_user');
    return data ? JSON.parse(data) : null;
  }

  get token(): string | null {
    return localStorage.getItem('cafetito_token');
  }

  get rol(): Rol | null {
    const rawRol = this.currentUser?.rol;
    if (rawRol === 3 || rawRol === '3') return Rol.AGRICULTOR;
    if (rawRol === 1 || rawRol === '1') return Rol.BENEFICIO;
    if (rawRol === 2 || rawRol === '2') return Rol.PESOCABAL;
    return null;
  }

  redirectByRol(): void {
    switch (this.rol) {
      case Rol.AGRICULTOR: this.router.navigate(['/agricultor']); break;
      case Rol.BENEFICIO: this.router.navigate(['/beneficio/cuentas']); break;
      case Rol.PESOCABAL: this.router.navigate(['/pesocabal/cuentas']); break;
      default: this.router.navigate(['/login']);
    }
  }

}