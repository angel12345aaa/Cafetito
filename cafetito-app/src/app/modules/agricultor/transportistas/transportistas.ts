import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  standalone: false,
  selector: 'app-transportistas-agricultor',
  templateUrl: './transportistas.html',
  styleUrls: ['./transportistas.css']
})
export class TransportistasAgricultorComponent implements OnInit {
  transportistas: any[] = [];
  loading = false;
  showForm = false;
  editando = false;
  form: any = {};

  constructor(private http: HttpClient) {}

  ngOnInit(): void { this.cargarDatos(); }

  cargarDatos(): void {
    this.loading = true;
    this.http.get<any[]>('/api/agricultor/transportistas').subscribe({
      next: data => { this.transportistas = data; this.loading = false; },
      error: err => { console.error(err); this.loading = false; }
    });
  }

  nuevo(): void { this.form = {}; this.editando = false; this.showForm = true; }

  editar(item: any): void { this.form = { ...item }; this.editando = true; this.showForm = true; }

  guardar(): void {
    const req = this.editando
      ? this.http.put(`/api/agricultor/transportistas/${this.form.id}`, this.form)
      : this.http.post('/api/agricultor/transportistas', this.form);
    req.subscribe({ next: () => { this.showForm = false; this.cargarDatos(); }, error: err => console.error(err) });
  }

  eliminar(id: number): void {
    if (confirm('¿Eliminar este registro?')) {
      this.http.delete(`/api/agricultor/transportistas/${id}`).subscribe({ next: () => this.cargarDatos(), error: err => console.error(err) });
    }
  }

  cancelar(): void { this.showForm = false; this.form = {}; }
}
