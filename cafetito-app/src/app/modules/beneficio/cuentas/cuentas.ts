import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { BeneficioService } from '../../../core/services/beneficio';
import { Cuenta } from '../../../core/models/models';

type VistaCuenta = 'TODAS' | 'PESO_CABAL' | 'CERRADAS' | 'CONFIRMADAS';

@Component({
  standalone: false,
  selector: 'app-cuentas-beneficio',
  templateUrl: './cuentas.html',
  styleUrls: ['./cuentas.css']
})
export class CuentasBeneficioComponent implements OnInit {

  vista: VistaCuenta = 'TODAS';

  cuentas: Cuenta[] = [];

  loading = false;
  error = '';
  mensaje = '';
  showForm = false;

  form: FormGroup;

    constructor(
    private beneficioService: BeneficioService,
    private fb: FormBuilder,
    private cdr: ChangeDetectorRef
    ) {
    this.form = this.fb.group({
      nitAgricultor: ['', Validators.required],
      pesoObjetivo: ['', [Validators.required, Validators.min(0.01)]]
    });
  }

  ngOnInit(): void {
    this.cambiarVista('TODAS');
  }

  cambiarVista(vista: VistaCuenta): void {
    this.vista = vista;
    this.error = '';
    this.mensaje = '';
    this.showForm = false;

    if (vista === 'TODAS') {
      this.cargarTodas();
    }

    if (vista === 'PESO_CABAL') {
      this.cargarPesoCabal();
    }

    if (vista === 'CERRADAS') {
      this.cargarCerradas();
    }

    if (vista === 'CONFIRMADAS') {
      this.cargarConfirmadas();
    }
  }

        cargarTodas(): void {
        this.loading = true;
        this.cdr.detectChanges();

        this.beneficioService.listarCuentas().subscribe({
            next: data => {
            this.cuentas = data || [];
            this.loading = false;
            this.cdr.detectChanges();
            },
            error: () => {
            this.error = 'No se pudieron cargar las cuentas.';
            this.cuentas = [];
            this.loading = false;
            this.cdr.detectChanges();
            }
        });
        }

            cargarPesoCabal(): void {
            this.loading = true;
            this.cdr.detectChanges();

            this.beneficioService.listarCuentasPesoCabal().subscribe({
                next: data => {
                this.cuentas = data || [];
                this.loading = false;
                this.cdr.detectChanges();
                },
                error: () => {
                this.error = 'No se pudieron cargar las cuentas.';
                this.cuentas = [];
                this.loading = false;
                this.cdr.detectChanges();
                }
            });
            }

            cargarCerradas(): void {
            this.loading = true;
            this.cdr.detectChanges();

            this.beneficioService.listarCuentasCerradas().subscribe({
                next: data => {
                this.cuentas = data || [];
                this.loading = false;
                this.cdr.detectChanges();
                },
                error: () => {
                this.error = 'No se pudieron cargar las cuentas.';
                this.cuentas = [];
                this.loading = false;
                this.cdr.detectChanges();
                }
            });
            }

  cargarConfirmadas(): void {
  this.loading = true;
  this.cdr.detectChanges();

  this.beneficioService.listarCuentasConfirmadas().subscribe({
    next: data => {
      this.cuentas = data || [];
      this.loading = false;
      this.cdr.detectChanges();
    },
    error: () => {
      this.error = 'No se pudieron cargar las cuentas.';
      this.cuentas = [];
      this.loading = false;
      this.cdr.detectChanges();
    }
  });
}

  nueva(): void {
    this.showForm = true;
    this.error = '';
    this.mensaje = '';
    this.form.reset();
  }

  guardar(): void {
    if (this.form.invalid) {
      return;
    }

    const cuenta: Cuenta = {
      nitAgricultor: Number(this.form.value.nitAgricultor),
      pesoObjetivo: Number(this.form.value.pesoObjetivo)
    };

    this.beneficioService.crearCuenta(cuenta).subscribe({
      next: () => {
        this.cdr.detectChanges();
        this.mensaje = 'Cuenta creada correctamente.';
        this.showForm = false;
        this.form.reset();
        this.cambiarVista('TODAS');
      },
      error: err => {
        this.cdr.detectChanges();
        this.error =
          err?.error?.mensaje ||
          err?.error?.error ||
          'No se pudo crear la cuenta.';
      }
    });
  }

  cerrarCuenta(cuenta: Cuenta): void {
    if (!cuenta.idCuenta) {
      return;
    }

    this.beneficioService.cambiarEstadoCuenta(
      cuenta.idCuenta,
      {
        nuevoEstado: 'CUENTA_CERRADA',
        diferenciaTotal: cuenta.diferenciaTotal || 0,
        observaciones: 'Cuenta cerrada desde Beneficio'
      }
    ).subscribe({
      next: () => {
        this.mensaje = 'Cuenta cerrada correctamente.';
        this.cambiarVista(this.vista);
      },
      error: err => {
        this.error =
          err?.error?.mensaje ||
          err?.error?.error ||
          'No se pudo cerrar la cuenta.';
      }
    });
  }

  confirmarCuenta(cuenta: Cuenta): void {
    if (!cuenta.idCuenta) {
      return;
    }

    this.beneficioService.cambiarEstadoCuenta(
      cuenta.idCuenta,
      {
        nuevoEstado: 'CUENTA_CONFIRMADA',
        diferenciaTotal: cuenta.diferenciaTotal || 0,
        observaciones: 'Cuenta confirmada desde Beneficio'
      }
    ).subscribe({
      next: () => {
        this.mensaje = 'Cuenta confirmada correctamente.';
        this.cambiarVista(this.vista);
      },
      error: err => {
        this.error =
          err?.error?.mensaje ||
          err?.error?.error ||
          'No se pudo confirmar la cuenta.';
      }
    });
  }

  puedeCerrar(cuenta: Cuenta): boolean {
    return cuenta.estado === 'PESAJE_FINALIZADO';
  }

  puedeConfirmar(cuenta: Cuenta): boolean {
    return cuenta.estado === 'CUENTA_CERRADA';
  }

  cancelar(): void {
    this.showForm = false;
    this.form.reset();
  }
}