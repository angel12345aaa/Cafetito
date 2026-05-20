import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import {
  DetalleCatalogo,
  Marca,
  Color,
  Linea,
  Modelo,
  Licencia
} from '../models/models';

@Injectable({
  providedIn: 'root'
})
export class CatalogosService {

  private readonly API_URL =
    'http://localhost:8090/api/agricultor/catalogos';

  constructor(
    private http: HttpClient
  ) {}

  listarMedidas():
  Observable<DetalleCatalogo[]> {

    return this.http.get<
      DetalleCatalogo[]
    >(
      `${this.API_URL}/medidas`
    );

  }

  listarEstadosPesaje():
  Observable<DetalleCatalogo[]> {

    return this.http.get<
      DetalleCatalogo[]
    >(
      `${this.API_URL}/estados-pesaje`
    );

  }

  listarMarcas():
  Observable<Marca[]> {

    return this.http.get<
      Marca[]
    >(
      `${this.API_URL}/marcas`
    );

  }

  listarColores():
  Observable<Color[]> {

    return this.http.get<
      Color[]
    >(
      `${this.API_URL}/colores`
    );

  }

  listarLineas():
  Observable<Linea[]> {

    return this.http.get<
      Linea[]
    >(
      `${this.API_URL}/lineas`
    );

  }

  listarModelos():
  Observable<Modelo[]> {

    return this.http.get<
      Modelo[]
    >(
      `${this.API_URL}/modelos`
    );

  }

  listarLicencias():
  Observable<Licencia[]> {

    return this.http.get<
      Licencia[]
    >(
      `${this.API_URL}/licencias`
    );

  }

}