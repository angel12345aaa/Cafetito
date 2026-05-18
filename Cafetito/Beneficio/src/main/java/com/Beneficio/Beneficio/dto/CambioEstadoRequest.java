package com.Beneficio.Beneficio.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CambioEstadoRequest {
    private String nuevoEstado;
    private Double diferenciaTotal;
}