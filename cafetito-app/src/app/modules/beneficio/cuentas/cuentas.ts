import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  standalone: false,
  selector: 'app-cuentas-beneficio',
  templateUrl: './cuentas.html',
  styleUrls: ['./cuentas.css']
})
export class CuentasBeneficioComponent implements OnInit {

  cuentas: any[] = [];
  loading = false;
  error = '';
  showForm = false;

  form: FormGroup;

  private apiUrl = 'http://localhost:8090/api/cuentas';

  constructor(
    private http: HttpClient,
    private fb: FormBuilder
  ) {
    this.form = this.fb.group({
      nitAgricultor: ['', Validators.required],
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

    this.http.get<any[]>(this.apiUrl).subscribe({
      next: data => {
        console.log('CUENTAS BENEFICIO:', data);
        this.cuentas = [...data];
        this.loading = false;
        },      
        error: () => {
        this.cuentas = [];
        this.loading = false;
        this.error = 'No se pudieron cargar las cuentas';
      }
    });
  }

  nueva(): void {
    this.showForm = true;
    this.form.reset();
  }

  guardar(): void {
    if (this.form.invalid) {
      return;
    }

    this.http.post(this.apiUrl, this.form.value).subscribe({
      next: () => {
        this.showForm = false;
        this.form.reset();
        this.cargarDatos();
      },
      error: () => {
        this.error = 'No se pudo crear la cuenta';
      }
    });
  }

  cancelar(): void {
    this.showForm = false;
    this.form.reset();
  }
}