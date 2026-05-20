import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
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

  idPesaje!: number;

  pesaje: any = null;
  parcialidades: any[] = [];
  transportes: any[] = [];
  transportistas: any[] = [];

  loading = false;
  error = '';
  mensajeExito = '';
  showForm = false;

  form: FormGroup;

  private apiPesajes = 'http://localhost:8090/api/agricultor/pesajes';
  private apiTransportes = 'http://localhost:8090/api/agricultor/transportes/disponibles';
  private apiTransportistas = 'http://localhost:8090/api/agricultor/transportistas/disponibles';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    private fb: FormBuilder,
    private cdr: ChangeDetectorRef
  ) {
    this.form = this.fb.group({
      placa: ['', Validators.required],
      idTransportista: ['', Validators.required],
      pesoActual: ['', [Validators.required, Validators.min(0.01)]],
      observaciones: ['']
    });
  }

  ngOnInit(): void {
    this.idPesaje = Number(this.route.snapshot.paramMap.get('idPesaje'));

    this.cargarDetallePesaje();
    this.cargarParcialidades();
    this.cargarDisponibles();
  }

  cargarDetallePesaje(): void {
    this.http.get<any>(`${this.apiPesajes}/${this.idPesaje}`).subscribe({
      next: data => {
        this.pesaje = data;
        this.cdr.detectChanges();
      },
      error: err => {
        this.error = err?.error?.error || 'No se pudo cargar el detalle del pesaje';
      }
    });
  }

  cargarParcialidades(): void {
    this.loading = true;
    this.error = '';

    this.http.get<any[]>(`${this.apiPesajes}/${this.idPesaje}/parcialidades`).subscribe({
      next: data => {
        this.parcialidades = Array.isArray(data) ? data : [];
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: err => {
        this.parcialidades = [];
        this.loading = false;
        this.error = err?.error?.error || 'No se pudieron cargar las parcialidades';
      }
    });
  }

  cargarDisponibles(): void {
    this.http.get<any[]>(this.apiTransportes).subscribe({
      next: data => this.transportes = Array.isArray(data) ? data : [],
      error: () => this.transportes = []
    });

    this.http.get<any[]>(this.apiTransportistas).subscribe({
      next: data => this.transportistas = Array.isArray(data) ? data : [],
      error: () => this.transportistas = []
    });
  }

  nueva(): void {
    this.error = '';
    this.mensajeExito = '';

    if (this.transportes.length === 0) {
      this.error = 'No existen transportes disponibles';
      return;
    }

    if (this.transportistas.length === 0) {
      this.error = 'No existen transportistas disponibles';
      return;
    }

    this.showForm = true;
    this.form.reset();
  }

  guardar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const body = {
      placa: this.form.value.placa,
      idTransportista: Number(this.form.value.idTransportista),
      pesoActual: Number(this.form.value.pesoActual),
      observaciones: this.form.value.observaciones
    };

    this.http.post(`${this.apiPesajes}/${this.idPesaje}/parcialidades`, body).subscribe({
      next: () => {
        this.mensajeExito = 'Parcialidad creada correctamente';
        this.showForm = false;
        this.form.reset();

        this.cargarDetallePesaje();
        this.cargarParcialidades();
        this.cargarDisponibles();
      },
      error: err => {
        this.error = err?.error?.error || 'Error al registrar la parcialidad';
      }
    });
  }

  cancelar(): void {
    this.showForm = false;
    this.form.reset();
  }

  regresar(): void {
    this.router.navigate(['/agricultor/pesajes']);
  }

  obtenerMedida(): string {
    return this.pesaje?.medida?.valor || 'Sin medida';
  }

  obtenerNombreTransportista(idTransportista: number): string {
    const encontrado = this.transportistas.find(
      t => Number(t.idTransportista) === Number(idTransportista)
    );

    return encontrado ? encontrado.nombre : String(idTransportista);
  }
}