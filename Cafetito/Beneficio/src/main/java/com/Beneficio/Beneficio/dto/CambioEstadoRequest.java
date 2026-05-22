package com.Beneficio.Beneficio.dto;

public class CambioEstadoRequest {

    private String nuevoEstado;
    private Integer nuevoEstadoNumerico;
    private Double diferenciaTotal;
    private String observaciones;

    public CambioEstadoRequest() {
    }

    public String getNuevoEstado() {
        return nuevoEstado;
    }

    public void setNuevoEstado(String nuevoEstado) {
        this.nuevoEstado = nuevoEstado;
    }

    public Integer getNuevoEstadoNumerico() {
        return nuevoEstadoNumerico;
    }

    public void setNuevoEstadoNumerico(Integer nuevoEstadoNumerico) {
        this.nuevoEstadoNumerico = nuevoEstadoNumerico;
    }

    public Double getDiferenciaTotal() {
        return diferenciaTotal;
    }

    public void setDiferenciaTotal(Double diferenciaTotal) {
        this.diferenciaTotal = diferenciaTotal;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}