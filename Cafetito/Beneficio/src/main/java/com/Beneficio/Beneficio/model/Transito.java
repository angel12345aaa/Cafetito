package com.Beneficio.Beneficio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transito", schema = "beneficio")
public class Transito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transito")
    private Long idTransito;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cuenta", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "historial", "transitos"})
    private Cuenta cuenta;

    @Column(name = "placa", nullable = false, length = 20)
    private String placa;

    @Column(name = "cui_transportista", nullable = false, length = 13)
    private String cuiTransportista;

    @Column(name = "nombre_transportista", nullable = false, length = 150)
    private String nombreTransportista;

    @Column(name = "estado_transporte", nullable = false)
    private Integer estadoTransporte;

    @Column(name = "estado_transportista", nullable = false)
    private Integer estadoTransportista;

    @Column(name = "observacion_transporte")
    private String observacionTransporte;

    @Column(name = "observacion_transportista")
    private String observacionTransportista;

    @Column(name = "hora_salida")
    private LocalDateTime horaSalida;

    @Column(name = "hora_llegada_real")
    private LocalDateTime horaLlegadaReal;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    public Transito() {
    }

    public Long getIdTransito() {
        return idTransito;
    }

    public void setIdTransito(Long idTransito) {
        this.idTransito = idTransito;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
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

    public Integer getEstadoTransporte() {
        return estadoTransporte;
    }

    public void setEstadoTransporte(Integer estadoTransporte) {
        this.estadoTransporte = estadoTransporte;
    }

    public Integer getEstadoTransportista() {
        return estadoTransportista;
    }

    public void setEstadoTransportista(Integer estadoTransportista) {
        this.estadoTransportista = estadoTransportista;
    }

    public String getObservacionTransporte() {
        return observacionTransporte;
    }

    public void setObservacionTransporte(String observacionTransporte) {
        this.observacionTransporte = observacionTransporte;
    }

    public String getObservacionTransportista() {
        return observacionTransportista;
    }

    public void setObservacionTransportista(String observacionTransportista) {
        this.observacionTransportista = observacionTransportista;
    }

    public LocalDateTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalDateTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public LocalDateTime getHoraLlegadaReal() {
        return horaLlegadaReal;
    }

    public void setHoraLlegadaReal(LocalDateTime horaLlegadaReal) {
        this.horaLlegadaReal = horaLlegadaReal;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}