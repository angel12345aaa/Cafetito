package com.Beneficio.Beneficio.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "bitacora", schema = "beneficio")
public class Bitacora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bitacora")
    private Long idBitacora;

    @Column(name = "operacion", length = 100)
    private String operacion;

    @Column(name = "usuario", length = 100)
    private String usuario;

    @Column(name = "codigo_peso", length = 50)
    private String codigoPeso;

    @Column(name = "cuenta")
    private Long cuenta;

    @Column(name = "parcial")
    private Long parcial;

    @Column(name = "peso_real")
    private Double pesoReal;

    @Column(name = "peso_observado")
    private Double pesoObservado;

    @Column(name = "medida", length = 20)
    private String medida;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "extemporaneo")
    private Boolean extemporaneo;

    @Column(name = "transportista", length = 100)
    private String transportista;

    @Column(name = "transporte", length = 50)
    private String transporte;

    @Column(name = "estado", length = 50)
    private String estado;

    @Column(name = "observacion", length = 500)
    private String observacion;

    @Column(name = "fecha_sistema")
    private LocalDateTime fechaSistema;

    public Bitacora() {
    }

    public Long getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(Long idBitacora) {
        this.idBitacora = idBitacora;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCodigoPeso() {
        return codigoPeso;
    }

    public void setCodigoPeso(String codigoPeso) {
        this.codigoPeso = codigoPeso;
    }

    public Long getCuenta() {
        return cuenta;
    }

    public void setCuenta(Long cuenta) {
        this.cuenta = cuenta;
    }

    public Long getParcial() {
        return parcial;
    }

    public void setParcial(Long parcial) {
        this.parcial = parcial;
    }

    public Double getPesoReal() {
        return pesoReal;
    }

    public void setPesoReal(Double pesoReal) {
        this.pesoReal = pesoReal;
    }

    public Double getPesoObservado() {
        return pesoObservado;
    }

    public void setPesoObservado(Double pesoObservado) {
        this.pesoObservado = pesoObservado;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Boolean getExtemporaneo() {
        return extemporaneo;
    }

    public void setExtemporaneo(Boolean extemporaneo) {
        this.extemporaneo = extemporaneo;
    }

    public String getTransportista() {
        return transportista;
    }

    public void setTransportista(String transportista) {
        this.transportista = transportista;
    }

    public String getTransporte() {
        return transporte;
    }

    public void setTransporte(String transporte) {
        this.transporte = transporte;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public LocalDateTime getFechaSistema() {
        return fechaSistema;
    }

    public void setFechaSistema(LocalDateTime fechaSistema) {
        this.fechaSistema = fechaSistema;
    }
}