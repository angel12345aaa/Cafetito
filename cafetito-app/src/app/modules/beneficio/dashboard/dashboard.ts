import { Component } from '@angular/core';

@Component({
  standalone: false,
  selector: 'app-dashboard-beneficio',
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css']
})
export class DashboardBeneficioComponent {

  tarjetas = [
    {
      titulo: 'Cuentas',
      descripcion: 'Crear, cerrar y confirmar cuentas.',
      ruta: '/beneficio/cuentas'
    },
    {
      titulo: 'Transportes',
      descripcion: 'Consultar y bloquear transportes.',
      ruta: '/beneficio/transportes'
    },
    {
      titulo: 'Transportistas',
      descripcion: 'Consultar y bloquear transportistas.',
      ruta: '/beneficio/transportistas'
    },
    {
      titulo: 'Agricultores',
      descripcion: 'Revisar agricultores registrados.',
      ruta: '/beneficio/agricultores'
    },
  ];

}