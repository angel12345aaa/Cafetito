import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import {  Marca, Color, Linea, Modelo, Transporte} from '../../../core/models/models';
import { CatalogosService } from '../../../core/services/catalogos';

@Component({
  standalone: false,
  selector: 'app-transportes-agricultor',
  templateUrl: './transportes.html',
  styleUrls: ['./transportes.css']
})
export class TransportesAgricultorComponent implements OnInit {

  transportes: Transporte[] = [];

  marcas: Marca[] = [];
  colores: Color[] = [];
  lineas: Linea[] = [];
  modelos: Modelo[] = [];

  loading = false;
  showForm = false;
  error = '';
  mensajeExito = '';

  form: FormGroup;

  private apiUrl = 'http://localhost:8090/api/agricultor/transportes';

  constructor(
    private http: HttpClient,
    private fb: FormBuilder,
    private catalogosService: CatalogosService,
    private cdr: ChangeDetectorRef
  ) {
    this.form = this.fb.group({
      placa: ['', Validators.required],
      marca: ['', Validators.required],
      color: ['', Validators.required],
      linea: ['', Validators.required],
      modelo: ['', Validators.required],
      observaciones: ['']
    });
  }

  ngOnInit(): void {
    this.cargarCatalogos();
    this.cargarDatos();
  }

  cargarCatalogos(): void {
    this.catalogosService.listarMarcas().subscribe({
      next: data => {
        this.marcas = data || [];
        this.cdr.detectChanges();
      }
    });

    this.catalogosService.listarColores().subscribe({
      next: data => {
        this.colores = data || [];
        this.cdr.detectChanges();
      }
    });

    this.catalogosService.listarLineas().subscribe({
      next: data => {
        this.lineas = data || [];
        this.cdr.detectChanges();
      }
    });

    this.catalogosService.listarModelos().subscribe({
      next: data => {
        this.modelos = data || [];
        this.cdr.detectChanges();
      }
    });
  }

  cargarDatos(): void {
    this.loading = true;
    this.error = '';
    this.cdr.detectChanges();

    this.http.get<Transporte[]>(this.apiUrl).subscribe({
      next: data => {
        this.transportes = Array.isArray(data) ? data : [];
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: err => {
        this.transportes = [];
        this.loading = false;
        this.error =
          err?.error?.error ||
          err?.error?.mensaje ||
          'No se pudieron cargar los transportes';
        this.cdr.detectChanges();
      }
    });
  }

  nuevo(): void {
    this.showForm = true;
    this.error = '';
    this.mensajeExito = '';
    this.form.reset();
  }

  guardar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const body = {
      placa: this.form.value.placa,
      marca: {
        idCatalogo: Number(this.form.value.marca)
      },
      color: {
        idCatalogo: Number(this.form.value.color)
      },
      linea: {
        idCatalogo: Number(this.form.value.linea)
      },
      modelo: {
        idCatalogo: Number(this.form.value.modelo)
      },
      observaciones: this.form.value.observaciones
    };

    this.http.post(this.apiUrl, body).subscribe({
      next: () => {
        this.showForm = false;
        this.form.reset();
        this.mensajeExito = 'Transporte creado correctamente';
        this.cargarDatos();
      },
      error: err => {
        this.error =
          err?.error?.error ||
          err?.error?.mensaje ||
          'Error al guardar transporte';
        this.cdr.detectChanges();
      }
    });
  }

  cancelar(): void {
    this.showForm = false;
    this.form.reset();
  }

    obtenerEstado(estado?: number): string {
    if (estado === 1) {
      return 'Activo';
    }

    if (estado === 0) {
      return 'Inactivo';
    }

    return 'Activo';
  }

  obtenerDisponible(disponible?: boolean): string {
    return disponible ? 'Sí' : 'No';
  }
}