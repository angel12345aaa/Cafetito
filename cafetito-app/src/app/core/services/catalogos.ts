import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DetalleCatalogo } from '../models/models';

@Injectable({
  providedIn: 'root'
})
export class CatalogosService {

  private readonly API_URL = 'http://localhost:8090/api/agricultor/catalogos';

  constructor(private http: HttpClient) {}

  listarMedidas(): Observable<DetalleCatalogo[]> {
    return this.http.get<DetalleCatalogo[]>(`${this.API_URL}/medidas`);
  }

  listarEstadosPesaje(): Observable<DetalleCatalogo[]> {
    return this.http.get<DetalleCatalogo[]>(`${this.API_URL}/estados-pesaje`);
  }
}