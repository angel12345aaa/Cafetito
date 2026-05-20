import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

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

  private apiUrl = 'http://localhost:8090/api/cuentas';

  constructor(
    private http: HttpClient,
    private fb: FormBuilder,
    private router: Router,
    private cdr: ChangeDetectorRef
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
    this.cdr.detectChanges();

    this.http.get<any[]>(this.apiUrl).subscribe({
      next: data => {
        this.cuentas = Array.isArray(data) ? data : [];
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.cuentas = [];
        this.loading = false;
        this.error = 'No se pudieron cargar las cuentas';
        this.cdr.detectChanges();
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
    if (this.pesoForm.invalid || !this.cuentaSeleccionada) return;

    const body = {
      ...this.cuentaSeleccionada,
      pesoTotal: this.pesoForm.value.pesoTotal,
      cantidadParcialidades: this.pesoForm.value.cantidadParcialidades
    };

    this.http.put(`${this.apiUrl}/${this.cuentaSeleccionada.idCuenta}`, body).subscribe({
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

  verParcialidades(idCuenta: number): void {
    this.router.navigate(['/agricultor/parcialidades', idCuenta]);
  }

  cancelar(): void {
    this.cuentaSeleccionada = null;
    this.pesoForm.reset();
  }
}