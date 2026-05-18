import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app';

import { AuthInterceptor } from './core/interceptors/auth.interceptor';

import { NavbarComponent } from './shared/components/navbar/navbar';

import { LoginComponent } from './modules/auth/login/login';

import { DashboardAgricultorComponent } from './modules/agricultor/dashboard/dashboard';
import { PesajesComponent } from './modules/agricultor/pesajes/pesajes';
import { TransportesAgricultorComponent } from './modules/agricultor/transportes/transportes';
import { TransportistasAgricultorComponent } from './modules/agricultor/transportistas/transportistas';
import { Parcialidades } from './modules/agricultor/parcialidades/parcialidades';

import { TransportesBeneficioComponent } from './modules/beneficio/transportes/transportes';
import { TransportistasBeneficioComponent } from './modules/beneficio/transportistas/transportistas';
import { AgricultoresComponent } from './modules/beneficio/agricultores/agricultores';

import { CuentasPesoCabalComponent } from './modules/pesocabal/cuentas/cuentas';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    LoginComponent,

    PesajesComponent,
    TransportesAgricultorComponent,
    TransportistasAgricultorComponent,
    Parcialidades,

    TransportesBeneficioComponent,
    TransportistasBeneficioComponent,
    AgricultoresComponent,

    CuentasPesoCabalComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,

    DashboardAgricultorComponent
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