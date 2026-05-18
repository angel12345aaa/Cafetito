import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  standalone: false,
  selector: 'app-transportes-agricultor',
  templateUrl: './transportes.html',
  styleUrls: ['./transportes.css']
})
export class TransportesAgricultorComponent implements OnInit {
  transportes: any[] = [];
  loading = false;
  showForm = false;
  editando = false;
  error = '';
  form: any = {};

  constructor(private http: HttpClient) {}

  ngOnInit(): void { this.cargarDatos(); }

  cargarDatos(): void {
    this.loading = true;
    this.error = '';
    this.http.get<any[]>('/api/agricultor/transportes').subscribe({
      next: data => { this.transportes = data; this.loading = false; },
      error: err => { this.error = 'Error al cargar transportes'; this.loading = false; }
    });
  }

  nuevo(): void { this.form = {}; this.editando = false; this.showForm = true; }

  editar(item: any): void { this.form = { ...item }; this.editando = true; this.showForm = true; }

  guardar(): void {
    const req = this.editando
      ? this.http.put(`/api/agricultor/transportes/${this.form.id}`, this.form)
      : this.http.post('/api/agricultor/transportes', this.form);
    req.subscribe({ next: () => { this.showForm = false; this.cargarDatos(); }, error: err => { this.error = 'Error al guardar'; } });
  }

  eliminar(id: number): void {
    if (confirm('¿Eliminar este registro?')) {
      this.http.delete(`/api/agricultor/transportes/${id}`).subscribe({ next: () => this.cargarDatos(), error: err => { this.error = 'Error al eliminar'; } });
    }
  }

  cancelar(): void { this.showForm = false; this.form = {}; }
}
