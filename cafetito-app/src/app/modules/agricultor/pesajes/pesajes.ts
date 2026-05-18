import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { DetalleCatalogo, Pesaje } from '../../../core/models/models';
import { PesajesService } from '../../../core/services/pesajes';
import { CatalogosService } from '../../../core/services/catalogos';

@Component({
  standalone: false,
  selector: 'app-pesajes',
  templateUrl: './pesajes.html',
  styleUrls: ['./pesajes.css']
})
export class PesajesComponent implements OnInit {

  pesajes: Pesaje[] = [];
  medidas: DetalleCatalogo[] = [];

  loading = false;
  showForm = false;

  mensajeExito = '';
  mensajeError = '';

  form!: FormGroup;

  constructor(
    private pesajesService: PesajesService,
    private catalogosService: CatalogosService,
    private fb: FormBuilder,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.inicializarFormulario();
    this.cargarMedidas();
    this.cargarDatos();
  }

  inicializarFormulario(): void {
    this.form = this.fb.group({
      medida: ['', Validators.required],
      pesoTotalActual: [null, [Validators.required, Validators.min(1)]],
      observaciones: ['', [Validators.maxLength(255)]]
    });
  }

  cargarMedidas(): void {
    this.catalogosService.listarMedidas().subscribe({
      next: data => {
        this.medidas = data || [];
      },
      error: err => {
        console.error('Error al cargar medidas:', err);
        this.mensajeError = 'No se pudieron cargar las medidas de peso.';
      }
    });
  }

  cargarDatos(): void {
  console.log('Entrando a cargarDatos');

  this.loading = true;
  this.mensajeError = '';

  this.pesajesService.listar().subscribe({
    next: data => {
      console.log('DATA RECIBIDA EN ANGULAR:', data);

      this.pesajes = data || [];
      this.loading = false;

      this.cdr.detectChanges();
    },
    error: err => {
      console.error('ERROR REAL EN ANGULAR:', err);

      this.pesajes = [];
      this.loading = false;
      this.mensajeError = err?.error?.error || 'No se pudieron cargar los pesajes.';

      this.cdr.detectChanges();
    }
  });
}

  nuevo(): void {
    this.form.reset();
    this.showForm = true;
    this.limpiarMensajes();
  }

  guardar(): void {
    this.limpiarMensajes();

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const body: Pesaje = {
      estado: {
        idDetalleCatalogo: 2
      },
      medida: {
        idDetalleCatalogo: Number(this.form.value.medida)
      },
      pesoTotalActual: Number(this.form.value.pesoTotalActual),
      observaciones: this.form.value.observaciones
    };

    this.pesajesService.crear(body).subscribe({
      next: () => {
        this.mensajeExito = 'El pesaje se creó correctamente.';
        this.showForm = false;
        this.form.reset();
        this.cargarDatos();
      },
      error: err => {
        console.error('Error al guardar:', err);
        this.mensajeError = err?.error?.error || 'No se pudo guardar el pesaje.';
      }
    });
  }

  verDetalle(pesaje: Pesaje): void {
    this.router.navigate(['/agricultor/parcialidades', pesaje.idPesaje]);
  }

  cancelar(): void {
    this.showForm = false;
    this.form.reset();
    this.limpiarMensajes();
  }

  limpiarMensajes(): void {
    this.mensajeExito = '';
    this.mensajeError = '';
  }
}