export interface LoginRequest {
  nombre: string;
  contrasena: string;
}

export interface LoginResponse {
  token: string;
  rol: Rol | number | string;
  usuario: string;
  nitAgricultor?: string;
}

export enum Rol {
  AGRICULTOR = 'ROLE_AGRICULTOR',
  BENEFICIO = 'ROLE_BENEFICIO',
  PESOCABAL = 'ROLE_PESOCABAL'
}

export interface Agricultor {
  nitAgricultor: string;
  nombre: string;
  activo: boolean;
  observaciones: string;
  fecha: string;
}

export interface Transporte {
  idTransporte: string;
  nitAgricultor: string | Agricultor;
  placa?: string;
  marca?: string;
  color?: string;
  linea?: string;
  modelo?: number;
  activo: boolean;
  observaciones: string;
  fechaCreacion: string;
  disponible?: boolean;
  pesajeAsociado?: number;
}

export interface CrearTransporteRequest {
  placa: string;
  tipoPlaca: string;
  marca: string;
  color: string;
  linea: string;
  modelo: number;
  nitAgricultor: string;
}

export interface ActualizarEstadoTransporteRequest {
  idTransporte: string;
  activo: boolean;
  observaciones: string;
}

export interface Transportista {
  idTransportista: number;
  nitAgricultor: string | Agricultor;
  nombre: string;
  cui: string;
  tipoLicencia: string;
  fechaNacimiento: string;
  fechaVencimientoLicencia: string;
  activo: boolean;
  observaciones: string;
  fechaCreacion: string;
  disponible?: boolean;
  pesajeAsociado?: number;
}

export interface CrearTransportistaRequest {
  cui: string;
  nombreCompleto: string;
  fechaNacimiento: string;
  tipoLicencia: string;
  fechaVencimientoLicencia: string;
  nitAgricultor: string;
}

export interface ActualizarEstadoTransportistaRequest {
  idTransportista: number;
  activo: boolean;
  observaciones: string;
}

export interface DetalleCatalogo {
  idDetalleCatalogo: number;
  codigo?: string;
  valor?: string;
}

export interface Pesaje {
  idPesaje?: number;
  noCuenta?: string;
  estado?: DetalleCatalogo;
  medida?: DetalleCatalogo;
  pesoTotalActual?: number;
  fecha?: string;
  observaciones?: string;
  cantidadParcialidades?: number;
}

export interface CrearPesajeRequest {
  medida: DetalleCatalogo;
  nitAgricultor: string;
}

export interface Cuenta {
  idCuenta: number;
  nitAgricultor: string | Agricultor;
  idPesaje: number;
  idEstado: number;
  estadoCuenta: string;
  fechaCreacion: string;
  pesoEnviado: number;
  pesoTotalObtenido: number;
  diferenciaTotal: number;
  cantParcialidades?: number;
  fechaEnvio?: string;
  tolerancia?: number;
}

export enum EstadoCuenta {
  CUENTA_CREADA    = 'Cuenta Creada',
  CUENTA_ABIERTA   = 'Cuenta Abierta',
  PESAJE_INICIADO  = 'Pesaje Iniciado',
  PESAJE_FINALIZADO= 'Pesaje Finalizado',
  CUENTA_CERRADA   = 'Cuenta Cerrada',
  CUENTA_CONFIRMADA= 'Cuenta Confirmada'
}

export interface CambiarEstadoCuentaRequest {
  idCuenta: number;
  state: number;
}

export interface Parcialidad {
  idParcialidad: number;
  idCuenta: number | Cuenta;
  idTransporte: string;
  idTransportista: number;
  placaTransporte?: string;
  nombreTransportista?: string;
  cuiTransportista?: string;
  aceptado?: boolean;
  observaciones?: string;
  pesoEnviado: number;
  pesoBascula?: number;
  diferenciaPeso?: number;
  tipoMedida: string;
  fechaRecepcionParcialidad?: string;
  fechaPesoBascula?: string;
  detalle?: string;
  boleta?: boolean;
  fechaBoleta?: string;
}

export interface CrearParcialidadRequest {
  idCuenta: number;
  idTransporte: string;
  idTransportista: number;
  pesoEnviado: number;
  tipoMedida: string;
}

export interface ActualizarPesoParcialidadRequest {
  idParcialidad: number;
  pesoObtenido: number;
  medidaPeso: string;
  observaciones: string;
  fechaPeso: string;
}

export interface ApiResponse<T> {
  codigo: number;
  mensaje: string;
  datos?: T;
}


