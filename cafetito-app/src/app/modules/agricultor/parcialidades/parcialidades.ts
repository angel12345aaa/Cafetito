import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-parcialidades',
  standalone: false,
  templateUrl: './parcialidades.html',
  styleUrls: ['./parcialidades.css']
})
export class Parcialidades implements OnInit {

  idCuenta!: number;
  parcialidades: any[] = [];
  loading = false;
  error = '';
  showForm = false;

  form: FormGroup;

  private apiBase = 'http://localhost:8090/api/agricultor/cuentas';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    private fb: FormBuilder
  ) {
    this.form = this.fb.group({
      placa: ['', Validators.required],
      idTransportista: ['', Validators.required],
      pesoActual: ['', [Validators.required, Validators.min(0.01)]],
      observaciones: ['']
    });
  }

  ngOnInit(): void {
    this.idCuenta = Number(this.route.snapshot.paramMap.get('idCuenta'));
    this.cargarParcialidades();
  }

  cargarParcialidades(): void {
    this.loading = true;
    this.error = '';

    this.http.get<any[]>(`${this.apiBase}/${this.idCuenta}/parcialidades`)
      .subscribe({
        next: data => {
          this.parcialidades = data || [];
          this.loading = false;
        },
        error: () => {
          this.parcialidades = [];
          this.loading = false;
          this.error = 'No se pudieron cargar las parcialidades';
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

    this.http.post(`${this.apiBase}/${this.idCuenta}/parcialidades`, this.form.value)
      .subscribe({
        next: () => {
          this.showForm = false;
          this.form.reset();
          this.cargarParcialidades();
        },
        error: () => {
          this.error = 'Error al registrar la parcialidad';
        }
      });
  }

  cancelar(): void {
    this.showForm = false;
    this.form.reset();
  }

  regresar(): void {
    this.router.navigate(['/pesocabal/cuentas']);
  }
}