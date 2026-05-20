import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Pesaje } from '../models/models';

@Injectable({
  providedIn: 'root'
})
export class PesajesService {

  private readonly API_URL =
    'http://localhost:8090/api/agricultor/pesajes';

  constructor(
    private http: HttpClient
  ) {}

  listar(): Observable<Pesaje[]> {
    return this.http.get<Pesaje[]>(this.API_URL);
  }

  crear(body: Pesaje): Observable<Pesaje> {
    return this.http.post<Pesaje>(this.API_URL, body);
  }

  actualizar(id: number, body: Pesaje): Observable<Pesaje> {
    return this.http.put<Pesaje>(`${this.API_URL}/${id}`, body);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API_URL}/${id}`);
  }

  finalizar(idPesaje: number): Observable<Pesaje> {
    return this.http.put<Pesaje>(
      `${this.API_URL}/${idPesaje}/finalizar`,
      {}
    );
  }
}