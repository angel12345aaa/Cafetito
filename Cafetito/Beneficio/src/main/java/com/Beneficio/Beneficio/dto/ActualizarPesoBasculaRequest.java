package com.Beneficio.Beneficio.dto;

public class ActualizarPesoBasculaRequest {

    private Double pesoBascula;
    private String tipoMedida;
    private String observaciones;

    public ActualizarPesoBasculaRequest() {
    }

    public Double getPesoBascula() {
        return pesoBascula;
    }

    public void setPesoBascula(Double pesoBascula) {
        this.pesoBascula = pesoBascula;
    }

    public String getTipoMedida() {
        return tipoMedida;
    }

    public void setTipoMedida(String tipoMedida) {
        this.tipoMedida = tipoMedida;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}