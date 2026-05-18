import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Cuenta, Parcialidad, ActualizarPesoParcialidadRequest, ApiResponse } from '../models/models';

@Injectable({ providedIn: 'root' })
export class PesocabalService {
  private readonly BASE = `${environment.apiGatewayUrl}${environment.endpoints.pesocabal}`;

  constructor(private http: HttpClient) {}

  getCuentasActivas(): Observable<Cuenta[]> {
    return this.http.get<Cuenta[]>(`${this.BASE}/cuentas/activas`);
  }

  getParcialidades(idCuenta: number): Observable<Parcialidad[]> {
    return this.http.get<Parcialidad[]>(`${this.BASE}/cuentas/${idCuenta}/parcialidades`);
  }

  actualizarPeso(data: ActualizarPesoParcialidadRequest): Observable<ApiResponse<Parcialidad>> {
    return this.http.put<ApiResponse<Parcialidad>>(`${this.BASE}/parcialidades/peso`, data);
  }

  generarBoleta(idParcialidad: number): Observable<Blob> {
    return this.http.get(`${this.BASE}/parcialidades/${idParcialidad}/boleta`, {
      responseType: 'blob'
    });
  }
}


