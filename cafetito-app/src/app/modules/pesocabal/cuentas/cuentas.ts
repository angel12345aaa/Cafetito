import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  standalone: false,
  selector: 'app-cuentas-pesocabal',
  templateUrl: './cuentas.html',
  styleUrls: ['./cuentas.css']
})
export class CuentasPesoCabalComponent implements OnInit {

  cuentas: any[] = [];
  loading = false;
  error = '';

  cuentaSeleccionada: any = null;
  pesoForm: FormGroup;

  constructor(
    private http: HttpClient,
    private fb: FormBuilder
  ) {
    this.pesoForm = this.fb.group({
      pesoTotal: ['', [Validators.required, Validators.min(0.01)]],
      cantidadParcialidades: ['', [Validators.required, Validators.min(1)]]
    });
  }

  ngOnInit(): void {
    this.cargarDatos();
  }

  cargarDatos(): void {
    this.loading = true;
    this.error = '';

    this.http.get<any[]>('/api/cuentas').subscribe({
      next: data => {
        this.cuentas = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Error al cargar las cuentas';
        this.loading = false;
      }
    });
  }

  seleccionar(cuenta: any): void {
    this.cuentaSeleccionada = cuenta;

    this.pesoForm.patchValue({
      pesoTotal: cuenta.pesoTotal,
      cantidadParcialidades: cuenta.cantidadParcialidades
    });
  }

  actualizarPeso(): void {
    if (this.pesoForm.invalid || !this.cuentaSeleccionada) {
      return;
    }

    const body = {
      ...this.cuentaSeleccionada,
      pesoTotal: this.pesoForm.value.pesoTotal,
      cantidadParcialidades: this.pesoForm.value.cantidadParcialidades
    };

    this.http.put(`/api/cuentas/${this.cuentaSeleccionada.idCuenta}`, body).subscribe({
      next: () => {
        this.cuentaSeleccionada = null;
        this.pesoForm.reset();
        this.cargarDatos();
      },
      error: () => {
        this.error = 'Error al actualizar el peso de la cuenta';
      }
    });
  }

  cancelar(): void {
    this.cuentaSeleccionada = null;
    this.pesoForm.reset();
  }
}