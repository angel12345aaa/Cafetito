import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  standalone: false,
  selector: 'app-transportes-beneficio',
  templateUrl: './transportes.html',
  styleUrls: ['./transportes.css']
})
export class TransportesBeneficioComponent implements OnInit {
  transportes: any[] = [];
  loading = false;
  showForm = false;
  editando = false;
  form: any = {};

  constructor(private http: HttpClient) {}

  ngOnInit(): void { this.cargarDatos(); }

  cargarDatos(): void {
    this.loading = true;
    this.http.get<any[]>('/api/beneficio/transportes').subscribe({
      next: data => { this.transportes = data; this.loading = false; },
      error: err => { console.error(err); this.loading = false; }
    });
  }

  nuevo(): void { this.form = {}; this.editando = false; this.showForm = true; }

  editar(item: any): void { this.form = { ...item }; this.editando = true; this.showForm = true; }

  guardar(): void {
    const req = this.editando
      ? this.http.put(`/api/beneficio/transportes/${this.form.id}`, this.form)
      : this.http.post('/api/beneficio/transportes', this.form);
    req.subscribe({ next: () => { this.showForm = false; this.cargarDatos(); }, error: err => console.error(err) });
  }

  eliminar(id: number): void {
    if (confirm('¿Eliminar este registro?')) {
      this.http.delete(`/api/beneficio/transportes/${id}`).subscribe({ next: () => this.cargarDatos(), error: err => console.error(err) });
    }
  }

  cancelar(): void { this.showForm = false; this.form = {}; }
}
