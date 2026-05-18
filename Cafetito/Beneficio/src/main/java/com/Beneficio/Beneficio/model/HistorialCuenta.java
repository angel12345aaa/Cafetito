package com.Beneficio.Beneficio.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "historial_cuenta", schema = "beneficio")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class HistorialCuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private Long idHistorial;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cuenta", nullable = false)
    private Cuenta cuenta;

    @Column(name = "id_agricultor")
    private Long idAgricultor;

    @Column(name = "estado", length = 50)
    private String estado;

    @Column(name = "diferencia_total")
    private Double diferenciaTotal;

    @Column(name = "tolerancia")
    private Double tolerancia;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
}