import { Component } from '@angular/core';

@Component({
  standalone: false,
  selector: 'app-dashboard-pesocabal',
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css']
})
export class DashboardPesoCabalComponent {

  tarjetas = [
    {
      titulo: 'Cuentas',
      descripcion: 'Consultar cuentas disponibles para pesaje.',
      ruta: '/pesocabal/cuentas',
      queryParams: { vista: 'CUENTAS' }
    },
    {
      titulo: 'Parcialidades pendientes',
      descripcion: 'Registrar peso báscula de parcialidades recibidas.',
      ruta: '/pesocabal/cuentas',
      queryParams: { vista: 'PENDIENTES' }
    },
    {
      titulo: 'Boletas',
      descripcion: 'Consultar parcialidades con boleta generada.',
      ruta: '/pesocabal/cuentas',
      queryParams: { vista: 'BOLETAS' }
    }
  ];

}