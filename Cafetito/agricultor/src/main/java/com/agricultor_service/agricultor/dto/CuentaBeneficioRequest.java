package com.agricultor_service.agricultor.dto;

public class CuentaBeneficioRequest {

    private Long nitAgricultor;
    private Double pesoObjetivo;
    private Integer cantidadParcialidades;
    private String estado;

    public CuentaBeneficioRequest() {
    }

    public Long getNitAgricultor() {
        return nitAgricultor;
    }

    public void setNitAgricultor(Long nitAgricultor) {
        this.nitAgricultor = nitAgricultor;
    }

    public Double getPesoObjetivo() {
        return pesoObjetivo;
    }

    public void setPesoObjetivo(Double pesoObjetivo) {
        this.pesoObjetivo = pesoObjetivo;
    }

    public Integer getCantidadParcialidades() {
        return cantidadParcialidades;
    }

    public void setCantidadParcialidades(Integer cantidadParcialidades) {
        this.cantidadParcialidades = cantidadParcialidades;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}