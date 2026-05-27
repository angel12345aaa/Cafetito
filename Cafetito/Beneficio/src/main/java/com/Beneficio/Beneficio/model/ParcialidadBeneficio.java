package com.Beneficio.Beneficio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "parcialidad_beneficio", schema = "beneficio")
public class ParcialidadBeneficio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parcialidad_beneficio")
    private Long idParcialidadBeneficio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cuenta", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "historial", "transitos", "parcialidades"})
    private Cuenta cuenta;

    @Column(name = "id_parcialidad_agricultor")
    private Long idParcialidadAgricultor;

    @Column(name = "id_pesaje_agricultor")
    private Long idPesajeAgricultor;

    @Column(name = "placa_transporte", length = 20)
    private String placaTransporte;

    @Column(name = "estado_transporte")
    private Integer estadoTransporte;

    @Column(name = "observacion_transporte")
    private String observacionTransporte;

    @Column(name = "cui_transportista", length = 13)
    private String cuiTransportista;

    @Column(name = "nombre_transportista", length = 150)
    private String nombreTransportista;

    @Column(name = "estado_transportista")
    private Integer estadoTransportista;

    @Column(name = "observacion_transportista")
    private String observacionTransportista;

    @Column(name = "peso_enviado")
    private Double pesoEnviado;

    @Column(name = "peso_bascula")
    private Double pesoBascula;

    @Column(name = "diferencia_peso")
    private Double diferenciaPeso;

    @Column(name = "tipo_medida", length = 50)
    private String tipoMedida;

    @Column(name = "estado", length = 50)
    private String estado;

    @Column(name = "detalle", length = 150)
    private String detalle;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "fecha_recepcion_parcialidad")
    private LocalDateTime fechaRecepcionParcialidad;

    @Column(name = "fecha_peso_bascula")
    private LocalDateTime fechaPesoBascula;

    @Column(name = "boleta")
    private Boolean boleta;

    @Column(name = "fecha_boleta")
    private LocalDateTime fechaBoleta;

    public ParcialidadBeneficio() {
    }

    public Long getIdParcialidadBeneficio() {
        return idParcialidadBeneficio;
    }

    public void setIdParcialidadBeneficio(Long idParcialidadBeneficio) {
        this.idParcialidadBeneficio = idParcialidadBeneficio;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Long getIdParcialidadAgricultor() {
        return idParcialidadAgricultor;
    }

    public void setIdParcialidadAgricultor(Long idParcialidadAgricultor) {
        this.idParcialidadAgricultor = idParcialidadAgricultor;
    }

    public Long getIdPesajeAgricultor() {
        return idPesajeAgricultor;
    }

    public void setIdPesajeAgricultor(Long idPesajeAgricultor) {
        this.idPesajeAgricultor = idPesajeAgricultor;
    }

    public String getPlacaTransporte() {
        return placaTransporte;
    }

    public void setPlacaTransporte(String placaTransporte) {
        this.placaTransporte = placaTransporte;
    }

    public Integer getEstadoTransporte() {
        return estadoTransporte;
    }

    public void setEstadoTransporte(Integer estadoTransporte) {
        this.estadoTransporte = estadoTransporte;
    }

    public String getObservacionTransporte() {
        return observacionTransporte;
    }

    public void setObservacionTransporte(String observacionTransporte) {
        this.observacionTransporte = observacionTransporte;
    }

    public String getCuiTransportista() {
        return cuiTransportista;
    }

    public void setCuiTransportista(String cuiTransportista) {
        this.cuiTransportista = cuiTransportista;
    }

    public String getNombreTransportista() {
        return nombreTransportista;
    }

    public void setNombreTransportista(String nombreTransportista) {
        this.nombreTransportista = nombreTransportista;
    }

    public Integer getEstadoTransportista() {
        return estadoTransportista;
    }

    public void setEstadoTransportista(Integer estadoTransportista) {
        this.estadoTransportista = estadoTransportista;
    }

    public String getObservacionTransportista() {
        return observacionTransportista;
    }

    public void setObservacionTransportista(String observacionTransportista) {
        this.observacionTransportista = observacionTransportista;
    }

    public Double getPesoEnviado() {
        return pesoEnviado;
    }

    public void setPesoEnviado(Double pesoEnviado) {
        this.pesoEnviado = pesoEnviado;
    }

    public Double getPesoBascula() {
        return pesoBascula;
    }

    public void setPesoBascula(Double pesoBascula) {
        this.pesoBascula = pesoBascula;
    }

    public Double getDiferenciaPeso() {
        return diferenciaPeso;
    }

    public void setDiferenciaPeso(Double diferenciaPeso) {
        this.diferenciaPeso = diferenciaPeso;
    }

    public String getTipoMedida() {
        return tipoMedida;
    }

    public void setTipoMedida(String tipoMedida) {
        this.tipoMedida = tipoMedida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDateTime getFechaRecepcionParcialidad() {
        return fechaRecepcionParcialidad;
    }

    public void setFechaRecepcionParcialidad(LocalDateTime fechaRecepcionParcialidad) {
        this.fechaRecepcionParcialidad = fechaRecepcionParcialidad;
    }

    public LocalDateTime getFechaPesoBascula() {
        return fechaPesoBascula;
    }

    public void setFechaPesoBascula(LocalDateTime fechaPesoBascula) {
        this.fechaPesoBascula = fechaPesoBascula;
    }

    public Boolean getBoleta() {
        return boleta;
    }

    public void setBoleta(Boolean boleta) {
        this.boleta = boleta;
    }

    public LocalDateTime getFechaBoleta() {
        return fechaBoleta;
    }

    public void setFechaBoleta(LocalDateTime fechaBoleta) {
        this.fechaBoleta = fechaBoleta;
    }
}