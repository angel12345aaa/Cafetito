import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app';

import { AuthInterceptor } from './core/interceptors/auth.interceptor';

import { NavbarComponent } from './shared/components/navbar/navbar';

import { LoginComponent } from './modules/auth/login/login';

import { DashboardAgricultorComponent } from './modules/agricultor/dashboard/dashboard';
import { PesajesComponent } from './modules/agricultor/pesajes/pesajes';
import { Parcialidades } from './modules/agricultor/parcialidades/parcialidades';
import { TransportesAgricultorComponent }from './modules/agricultor/transportes/transportes';
import { TransportistasAgricultorComponent } from './modules/agricultor/transportistas/transportistas';

import { DashboardBeneficioComponent } from './modules/beneficio/dashboard/dashboard';
import { CuentasBeneficioComponent } from './modules/beneficio/cuentas/cuentas';
import { TransportesBeneficioComponent } from './modules/beneficio/transportes/transportes';
import { TransportistasBeneficioComponent } from './modules/beneficio/transportistas/transportistas';
import { AgricultoresComponent } from './modules/beneficio/agricultores/agricultores';

import { DashboardPesoCabalComponent } from './modules/pesocabal/dashboard/dashboard';
import { CuentasPesoCabalComponent } from './modules/pesocabal/cuentas/cuentas';

@NgModule({
  declarations: [
  AppComponent,
  NavbarComponent,

  LoginComponent,

  PesajesComponent,
  Parcialidades,
  TransportesAgricultorComponent,
  TransportistasAgricultorComponent,

  DashboardBeneficioComponent,
  CuentasBeneficioComponent,
  TransportesBeneficioComponent,
  TransportistasBeneficioComponent,
  AgricultoresComponent,

  DashboardPesoCabalComponent,
  CuentasPesoCabalComponent
],

  imports: [
    BrowserModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }