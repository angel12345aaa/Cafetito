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

    @Column(name = "peso")
    private Double peso;

    @Column(name = "estado")
    private String estado;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

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

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}