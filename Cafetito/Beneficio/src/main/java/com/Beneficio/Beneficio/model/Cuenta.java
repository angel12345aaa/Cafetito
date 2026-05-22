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
    @Column(name = "id_agricultor")
    private Long idAgricultor;

    @Column(name = "peso_total")
    private Double pesoTotal;

    @Column(name = "cantidad_parcialidades")
    private Integer cantidadParcialidades;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    @Column(name = "fecha_llegada")
    private LocalDateTime fechaLlegada;

    @Column(name = "estado")
    private String estado;

    @Column(name = "diferencia_total")
    private Double diferenciaTotal;

    @Column(name = "tolerancia")
    private Double tolerancia;

    @Column(name = "resultado_tolerancia")
    private String resultadoTolerancia;

    @JsonIgnore
    @OneToMany(mappedBy = "cuenta")
    private List<Transito> transitos;

    @JsonIgnore
    @OneToMany(mappedBy = "cuenta")
    private List<HistorialCuenta> historial;

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

    public Double getPesoTotal() {
        return pesoTotal;
    }

    public void setPesoTotal(Double pesoTotal) {
        this.pesoTotal = pesoTotal;
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
}