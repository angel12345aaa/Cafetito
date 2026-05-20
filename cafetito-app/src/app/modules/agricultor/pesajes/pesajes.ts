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
    this.form = this.fb.group({
      medida: ['', Validators.required],
      pesoTotalActual: [null, [Validators.required, Validators.min(1)]],
      observaciones: ['']
    });

    this.cargarMedidas();
    this.cargarDatos();
  }

  cargarMedidas(): void {
    this.catalogosService.listarMedidas().subscribe({
      next: data => {
        this.medidas = data || [];
        this.cdr.detectChanges();
      },
      error: () => {
        this.mensajeError = 'No se pudieron cargar las medidas';
        this.cdr.detectChanges();
      }
    });
  }

  cargarDatos(): void {
    this.loading = true;
    this.mensajeError = '';

    this.pesajesService.listar().subscribe({
      next: data => {
        this.pesajes = data || [];
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err: any) => {
        this.pesajes = [];
        this.loading = false;
        this.mensajeError = err?.error?.error || 'Error cargando pesajes';
        this.cdr.detectChanges();
      }
    });
  }

  nuevo(): void {
    this.showForm = true;
    this.form.reset();
    this.mensajeExito = '';
    this.mensajeError = '';
  }

  guardar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const body: Pesaje = {
      medida: {
        idDetalleCatalogo: Number(this.form.value.medida)
      },
      pesoTotalActual: Number(this.form.value.pesoTotalActual),
      observaciones: this.form.value.observaciones
    };

    this.pesajesService.crear(body).subscribe({
      next: () => {
        this.showForm = false;
        this.form.reset();
        this.mensajeExito = 'Pesaje creado correctamente';
        this.cargarDatos();
      },
      error: (err: any) => {
        this.mensajeError = err?.error?.error || 'No se pudo crear el pesaje';
        this.cdr.detectChanges();
      }
    });
  }

  verDetalle(pesaje: Pesaje): void {
    if (!pesaje.idPesaje) {
      return;
    }

    this.router.navigate([
      '/agricultor/pesajes',
      pesaje.idPesaje,
      'parcialidades'
    ]);
  }

  finalizar(pesaje: Pesaje): void {
    if (!pesaje.idPesaje) {
      return;
    }

    const confirmar = confirm('¿Desea finalizar el pesaje?');

    if (!confirmar) {
      return;
    }

    this.pesajesService.finalizar(pesaje.idPesaje).subscribe({
      next: () => {
        this.mensajeExito = 'Pesaje finalizado correctamente';
        this.cargarDatos();
      },
      error: (err: any) => {
        this.mensajeError = err?.error?.error || 'No se pudo finalizar el pesaje';
        this.cdr.detectChanges();
      }
    });
  }

  puedeFinalizar(pesaje: Pesaje): boolean {
    const cantidad = pesaje.cantidadParcialidades || 0;
    const estado = pesaje.estado?.idDetalleCatalogo;

    return cantidad > 0 && estado === 2;
  }

  cancelar(): void {
    this.showForm = false;
    this.form.reset();
  }

  obtenerMedida(id?: number): string {
    return this.medidas.find(
      m => Number(m.idDetalleCatalogo) === Number(id)
    )?.valor || 'Sin medida';
  }

  obtenerEstado(pesaje: Pesaje): string {
    return pesaje.estado?.valor || this.obtenerEstadoPorId(pesaje.estado?.idDetalleCatalogo);
  }

  obtenerEstadoPorId(id?: number): string {
    switch (id) {
      case 2:
        return 'Pesaje Iniciado';
      case 3:
        return 'Pesaje Finalizado';
      default:
        return 'Sin estado';
    }
  }
}