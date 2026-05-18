import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import {
  Cuenta, CambiarEstadoCuentaRequest,
  Transporte, ActualizarEstadoTransporteRequest,
  Transportista, ActualizarEstadoTransportistaRequest,
  Agricultor, Parcialidad, ApiResponse
} from '../models/models';

@Injectable({ providedIn: 'root' })
export class BeneficioService {
  private readonly BASE = `${environment.apiGatewayUrl}${environment.endpoints.beneficio}`;

  constructor(private http: HttpClient) {}

  getCuentas(filtros?: { fecha?: string; noCuenta?: string; estado?: string }): Observable<Cuenta[]> {
    let params = new HttpParams();
    if (filtros?.fecha) params = params.set('fecha', filtros.fecha);
    if (filtros?.noCuenta) params = params.set('noCuenta', filtros.noCuenta);
    if (filtros?.estado) params = params.set('estado', filtros.estado);
    return this.http.get<Cuenta[]>(`${this.BASE}/cuentas`, { params });
  }

  getCuentaDetalle(idCuenta: number): Observable<Cuenta> {
    return this.http.get<Cuenta>(`${this.BASE}/cuentas/${idCuenta}`);
  }

  cambiarEstadoCuenta(data: CambiarEstadoCuentaRequest): Observable<ApiResponse<Cuenta>> {
    return this.http.put<ApiResponse<Cuenta>>(`${this.BASE}/cuentas/estado`, data);
  }

  getParcialidades(idCuenta: number): Observable<Parcialidad[]> {
    return this.http.get<Parcialidad[]>(`${this.BASE}/cuentas/${idCuenta}/parcialidades`);
  }

  recibirParcialidad(idParcialidad: number): Observable<ApiResponse<Parcialidad>> {
    return this.http.put<ApiResponse<Parcialidad>>(
      `${this.BASE}/parcialidades/${idParcialidad}/recibir`, {}
    );
  }

  rechazarParcialidad(idParcialidad: number): Observable<ApiResponse<Parcialidad>> {
    return this.http.put<ApiResponse<Parcialidad>>(
      `${this.BASE}/parcialidades/${idParcialidad}/rechazar`, {}
    );
  }

  getTransportes(filtros?: { placa?: string; estado?: string }): Observable<Transporte[]> {
    let params = new HttpParams();
    if (filtros?.placa) params = params.set('placa', filtros.placa);
    if (filtros?.estado) params = params.set('estado', filtros.estado);
    return this.http.get<Transporte[]>(`${this.BASE}/transportes`, { params });
  }

  actualizarEstadoTransporte(data: ActualizarEstadoTransporteRequest): Observable<ApiResponse<Transporte>> {
    return this.http.put<ApiResponse<Transporte>>(`${this.BASE}/transportes/estado`, data);
  }

  getTransportistas(filtros?: { cui?: string; estado?: string }): Observable<Transportista[]> {
    let params = new HttpParams();
    if (filtros?.cui) params = params.set('cui', filtros.cui);
    if (filtros?.estado) params = params.set('estado', filtros.estado);
    return this.http.get<Transportista[]>(`${this.BASE}/transportistas`, { params });
  }

  actualizarEstadoTransportista(data: ActualizarEstadoTransportistaRequest): Observable<ApiResponse<Transportista>> {
    return this.http.put<ApiResponse<Transportista>>(`${this.BASE}/transportistas/estado`, data);
  }

  getAgricultores(nit?: string): Observable<Agricultor[]> {
    let params = new HttpParams();
    if (nit) params = params.set('nit', nit);
    return this.http.get<Agricultor[]>(`${this.BASE}/agricultores`, { params });
  }

  getAgricultorDetalle(nitAgricultor: string): Observable<any> {
    return this.http.get<any>(`${this.BASE}/agricultores/${nitAgricultor}/detalle`);
  }
}


