import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { Cuenta, Parcialidad } from '../../../core/models/models';
import { PesocabalService } from '../../../core/services/pesocabal';

type VistaPesoCabal = 'CUENTAS' | 'PENDIENTES' | 'PESADAS' | 'BOLETAS';

@Component({
  standalone: false,
  selector: 'app-cuentas-pesocabal',
  templateUrl: './cuentas.html',
  styleUrls: ['./cuentas.css']
})
export class CuentasPesoCabalComponent implements OnInit {

  vista: VistaPesoCabal = 'CUENTAS';

  cuentas: Cuenta[] = [];
  parcialidades: Parcialidad[] = [];

  loading = false;
  error = '';
  mensaje = '';

  parcialidadSeleccionada: Parcialidad | null = null;

  pesoForm: FormGroup;

  constructor(
    private pesocabalService: PesocabalService,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {
    this.pesoForm = this.fb.group({
      pesoBascula: ['', [Validators.required, Validators.min(0.01)]],
      tipoMedida: ['QUINTAL', [Validators.required]],
      observaciones: ['']
    });
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const vista = params['vista'] as VistaPesoCabal;

      if (vista === 'PENDIENTES' || vista === 'PESADAS' || vista === 'BOLETAS') {
        this.cambiarVista(vista);
      } else {
        this.cambiarVista('CUENTAS');
      }
    });
  }

  cambiarVista(vista: VistaPesoCabal): void {
    this.vista = vista;
    this.error = '';
    this.mensaje = '';
    this.parcialidadSeleccionada = null;

    this.cuentas = [];
    this.parcialidades = [];

    this.pesoForm.reset({
      tipoMedida: 'QUINTAL'
    });

    if (vista === 'CUENTAS') this.cargarCuentas();
    if (vista === 'PENDIENTES') this.cargarPendientes();
    if (vista === 'PESADAS') this.cargarPesadas();
    if (vista === 'BOLETAS') this.cargarBoletas();
  }

  cargarCuentas(): void {
    this.loading = true;
    this.cdr.detectChanges();

    this.pesocabalService.listarCuentas().subscribe({
      next: data => {
        this.cuentas = data || [];
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: err => {
        this.error = err?.error?.mensaje || 'No se pudieron cargar las cuentas.';
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  cargarPendientes(): void {
    this.loading = true;
    this.cdr.detectChanges();

    this.pesocabalService.listarPendientes().subscribe({
      next: data => {
        this.parcialidades = data || [];
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: err => {
        this.error = err?.error?.mensaje || 'No se pudieron cargar las pendientes.';
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  cargarPesadas(): void {
    this.loading = true;
    this.cdr.detectChanges();

    this.pesocabalService.listarPesadas().subscribe({
      next: data => {
        this.parcialidades = data || [];
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: err => {
        this.error = err?.error?.mensaje || 'No se pudieron cargar las pesadas.';
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  cargarBoletas(): void {
    this.loading = true;
    this.cdr.detectChanges();

    this.pesocabalService.listarBoletas().subscribe({
      next: data => {
        this.parcialidades = data || [];
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: err => {
        this.error = err?.error?.mensaje || 'No se pudieron cargar las boletas.';
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  seleccionarParcialidad(parcialidad: Parcialidad): void {
    this.parcialidadSeleccionada = parcialidad;

    this.pesoForm.reset({
      pesoBascula: parcialidad.pesoEnviado || '',
      tipoMedida: parcialidad.tipoMedida || 'QUINTAL',
      observaciones: 'Peso registrado por Peso Cabal'
    });
  }

  actualizarPeso(): void {
    if (this.pesoForm.invalid || !this.parcialidadSeleccionada?.idParcialidadBeneficio) return;

    this.pesocabalService.actualizarPeso(
      this.parcialidadSeleccionada.idParcialidadBeneficio,
      this.pesoForm.value
    ).subscribe({
      next: () => {
        this.mensaje = 'Peso registrado correctamente.';
        this.cancelar();
        this.cargarPendientes();
      },
      error: err => {
        this.error = err?.error?.mensaje || err?.error?.error || 'No se pudo registrar el peso.';
      }
    });
  }

  generarBoleta(parcialidad: Parcialidad): void {
    if (!parcialidad.idParcialidadBeneficio) return;

    this.pesocabalService.generarBoleta(parcialidad.idParcialidadBeneficio).subscribe({
      next: () => {
        this.mensaje = 'Boleta generada correctamente.';
        this.cargarPesadas();
      },
      error: err => {
        this.error = err?.error?.mensaje || err?.error?.error || 'No se pudo generar la boleta.';
      }
    });
  }

  cancelar(): void {
    this.parcialidadSeleccionada = null;
    this.pesoForm.reset({
      tipoMedida: 'QUINTAL'
    });
  }
}