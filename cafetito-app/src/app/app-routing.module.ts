import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuard } from './core/guards/auth-guard';
import { Rol } from './core/models/models';

import { LoginComponent } from './modules/auth/login/login';

import { DashboardAgricultorComponent } from './modules/agricultor/dashboard/dashboard';
import { Parcialidades } from './modules/agricultor/parcialidades/parcialidades';
import { PesajesComponent } from './modules/agricultor/pesajes/pesajes';
import { TransportesAgricultorComponent } from './modules/agricultor/transportes/transportes';
import { TransportistasAgricultorComponent } from './modules/agricultor/transportistas/transportistas';

import { DashboardBeneficioComponent } from './modules/beneficio/dashboard/dashboard';
import { CuentasBeneficioComponent } from './modules/beneficio/cuentas/cuentas';
import { TransportesBeneficioComponent } from './modules/beneficio/transportes/transportes';
import { TransportistasBeneficioComponent } from './modules/beneficio/transportistas/transportistas';
import { AgricultoresComponent } from './modules/beneficio/agricultores/agricultores';

import { DashboardPesoCabalComponent } from './modules/pesocabal/dashboard/dashboard';
import { CuentasPesoCabalComponent } from './modules/pesocabal/cuentas/cuentas';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },

  { path: 'login', component: LoginComponent },

  {
    path: 'agricultor',
    component: DashboardAgricultorComponent,
    canActivate: [AuthGuard],
    data: { roles: [Rol.AGRICULTOR] }
  },
  {
    path: 'agricultor/pesajes',
    component: PesajesComponent,
    canActivate: [AuthGuard],
    data: { roles: [Rol.AGRICULTOR] }
  },
  {
    path: 'agricultor/pesajes/:idPesaje/parcialidades',
    component: Parcialidades,
    canActivate: [AuthGuard],
    data: { roles: [Rol.AGRICULTOR] }
  },
  {
  path: 'agricultor/transportes',
  component: TransportesAgricultorComponent,
  canActivate: [AuthGuard],
  data: { roles: [Rol.AGRICULTOR] }
},
  {
    path: 'agricultor/transportistas',
    component: TransportistasAgricultorComponent,
    canActivate: [AuthGuard],
    data: { roles: [Rol.AGRICULTOR] }
  },

  {
    path: 'beneficio',
    component: DashboardBeneficioComponent,
    canActivate: [AuthGuard],
    data: { roles: [Rol.BENEFICIO] }
  },
  {
    path: 'beneficio/cuentas',
    component: CuentasBeneficioComponent,
    canActivate: [AuthGuard],
    data: { roles: [Rol.BENEFICIO] }
  },
  {
  path: 'beneficio/transportes',
  component: TransportesBeneficioComponent,
  canActivate: [AuthGuard],
  data: { roles: [Rol.BENEFICIO] }
},
  {
    path: 'beneficio/transportistas',
    component: TransportistasBeneficioComponent,
    canActivate: [AuthGuard],
    data: { roles: [Rol.BENEFICIO] }
  },
  {
    path: 'beneficio/agricultores',
    component: AgricultoresComponent,
    canActivate: [AuthGuard],
    data: { roles: [Rol.BENEFICIO] }
  },

  {
    path: 'pesocabal',
    component: DashboardPesoCabalComponent,
    canActivate: [AuthGuard],
    data: { roles: [Rol.PESOCABAL] }
  },
  {
    path: 'pesocabal/cuentas',
    component: CuentasPesoCabalComponent,
    canActivate: [AuthGuard],
    data: { roles: [Rol.PESOCABAL] }
  },

  { path: '**', redirectTo: '/login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }