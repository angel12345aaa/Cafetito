package com.Beneficio.Beneficio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cuenta", schema = "beneficio")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cuenta")
    private Long idCuenta;

    @JsonProperty("nitAgricultor")
    @Column(name = "id_agricultor", nullable = false)
    private Long idAgricultor;

    @Column(name = "peso_objetivo", nullable = false)
    private Double pesoObjetivo;

    @Column(name = "peso_acumulado")
    private Double pesoAcumulado;

    @Column(name = "peso_bascula_total")
    private Double pesoBasculaTotal;

    @Column(name = "saldo_pendiente")
    private Double saldoPendiente;

    @Column(name = "cantidad_parcialidades")
    private Integer cantidadParcialidades;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    @Column(name = "fecha_llegada")
    private LocalDateTime fechaLlegada;

    @Column(name = "estado", length = 50)
    private String estado;

    @Column(name = "diferencia_total")
    private Double diferenciaTotal;

    @Column(name = "tolerancia")
    private Double tolerancia;

    @Column(name = "resultado_tolerancia", length = 100)
    private String resultadoTolerancia;

    @JsonIgnore
    @OneToMany(mappedBy = "cuenta")
    private List<Transito> transitos;

    @JsonIgnore
    @OneToMany(mappedBy = "cuenta")
    private List<HistorialCuenta> historial;

    @JsonIgnore
    @OneToMany(mappedBy = "cuenta")
    private List<ParcialidadBeneficio> parcialidades;

    public Cuenta() {
    }

    public Long getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Long getIdAgricultor() {
        return idAgricultor;
    }

    public void setIdAgricultor(Long idAgricultor) {
        this.idAgricultor = idAgricultor;
    }

    public Double getPesoObjetivo() {
        return pesoObjetivo;
    }

    public void setPesoObjetivo(Double pesoObjetivo) {
        this.pesoObjetivo = pesoObjetivo;
    }

    public Double getPesoAcumulado() {
        return pesoAcumulado;
    }

    public void setPesoAcumulado(Double pesoAcumulado) {
        this.pesoAcumulado = pesoAcumulado;
    }

    public Double getPesoBasculaTotal() {
        return pesoBasculaTotal;
    }

    public void setPesoBasculaTotal(Double pesoBasculaTotal) {
        this.pesoBasculaTotal = pesoBasculaTotal;
    }

    public Double getSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(Double saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    public Integer getCantidadParcialidades() {
        return cantidadParcialidades;
    }

    public void setCantidadParcialidades(Integer cantidadParcialidades) {
        this.cantidadParcialidades = cantidadParcialidades;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public LocalDateTime getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(LocalDateTime fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getDiferenciaTotal() {
        return diferenciaTotal;
    }

    public void setDiferenciaTotal(Double diferenciaTotal) {
        this.diferenciaTotal = diferenciaTotal;
    }

    public Double getTolerancia() {
        return tolerancia;
    }

    public void setTolerancia(Double tolerancia) {
        this.tolerancia = tolerancia;
    }

    public String getResultadoTolerancia() {
        return resultadoTolerancia;
    }

    public void setResultadoTolerancia(String resultadoTolerancia) {
        this.resultadoTolerancia = resultadoTolerancia;
    }

    public List<Transito> getTransitos() {
        return transitos;
    }

    public void setTransitos(List<Transito> transitos) {
        this.transitos = transitos;
    }

    public List<HistorialCuenta> getHistorial() {
        return historial;
    }

    public void setHistorial(List<HistorialCuenta> historial) {
        this.historial = historial;
    }

    public List<ParcialidadBeneficio> getParcialidades() {
        return parcialidades;
    }

    public void setParcialidades(List<ParcialidadBeneficio> parcialidades) {
        this.parcialidades = parcialidades;
    }
}