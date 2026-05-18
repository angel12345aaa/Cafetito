import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import {
  Pesaje, CrearPesajeRequest,
  Transporte, CrearTransporteRequest,
  Transportista, CrearTransportistaRequest,
  Parcialidad, CrearParcialidadRequest, ApiResponse
} from '../models/models';

@Injectable({ providedIn: 'root' })
export class AgricultorService {
  private readonly BASE = `${environment.apiGatewayUrl}${environment.endpoints.agricultor}`;

  constructor(private http: HttpClient) {}

  getPesajes(nitAgricultor: string): Observable<Pesaje[]> {
    return this.http.get<Pesaje[]>(`${this.BASE}/pesajes`, {
      params: new HttpParams().set('nitAgricultor', nitAgricultor)
    });
  }

  crearPesaje(data: CrearPesajeRequest): Observable<ApiResponse<Pesaje>> {
    return this.http.post<ApiResponse<Pesaje>>(`${this.BASE}/pesajes`, data);
  }

  getParcialidades(idCuenta: number): Observable<Parcialidad[]> {
    return this.http.get<Parcialidad[]>(`${this.BASE}/parcialidades`, {
      params: new HttpParams().set('idCuenta', idCuenta)
    });
  }

  crearParcialidad(data: CrearParcialidadRequest): Observable<ApiResponse<Parcialidad>> {
    return this.http.post<ApiResponse<Parcialidad>>(`${this.BASE}/parcialidades`, data);
  }

  getTransportes(nitAgricultor: string): Observable<Transporte[]> {
    return this.http.get<Transporte[]>(`${this.BASE}/transportes`, {
      params: new HttpParams().set('nitAgricultor', nitAgricultor)
    });
  }

  crearTransporte(data: CrearTransporteRequest): Observable<ApiResponse<Transporte>> {
    return this.http.post<ApiResponse<Transporte>>(`${this.BASE}/transportes`, data);
  }

  getTransportistas(nitAgricultor: string): Observable<Transportista[]> {
    return this.http.get<Transportista[]>(`${this.BASE}/transportistas`, {
      params: new HttpParams().set('nitAgricultor', nitAgricultor)
    });
  }

  crearTransportista(data: CrearTransportistaRequest): Observable<ApiResponse<Transportista>> {
    return this.http.post<ApiResponse<Transportista>>(`${this.BASE}/transportistas`, data);
  }

  getTransportesDisponibles(nitAgricultor: string, idPesaje: number): Observable<Transporte[]> {
    return this.http.get<Transporte[]>(`${this.BASE}/transportes/disponibles`, {
      params: new HttpParams()
        .set('nitAgricultor', nitAgricultor)
        .set('idPesaje', idPesaje)
    });
  }

  getTransportistasDisponibles(nitAgricultor: string, idPesaje: number): Observable<Transportista[]> {
    return this.http.get<Transportista[]>(`${this.BASE}/transportistas/disponibles`, {
      params: new HttpParams()
        .set('nitAgricultor', nitAgricultor)
        .set('idPesaje', idPesaje)
    });
  }
}


