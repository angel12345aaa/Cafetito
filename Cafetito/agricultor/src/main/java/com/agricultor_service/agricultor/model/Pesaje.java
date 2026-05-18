package com.agricultor_service.agricultor.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pesaje", schema = "agricultor")
public class Pesaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pesaje")
    private Long idPesaje;

    @Column(name = "no_cuenta")
    private String noCuenta;

    @ManyToOne
    @JoinColumn(name = "id_agricultor", nullable = false)
    private Agricultor agricultor;

    @Column(name = "cantidad_parcialidades")
    private Integer cantidadParcialidades;

    @ManyToOne
    @JoinColumn(name = "estado")
    private DetalleCatalogo estado;

    @ManyToOne
    @JoinColumn(name = "medida")
    private DetalleCatalogo medida;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "peso_total_actual")
    private Double pesoTotalActual;

    @Column(name = "observaciones")
    private String observaciones;
}