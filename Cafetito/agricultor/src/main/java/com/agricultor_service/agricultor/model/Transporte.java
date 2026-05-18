package com.agricultor_service.agricultor.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transportes", schema = "agricultor")
public class Transporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transporte")
    private Long idTransporte;

    @Column(name = "placa", nullable = false, unique = true)
    private String placa;

    @ManyToOne
    @JoinColumn(name = "id_agricultor", nullable = false)
    private Agricultor agricultor;

    @ManyToOne
    @JoinColumn(name = "id_marca")
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "id_color")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "id_linea")
    private Linea linea;

    @ManyToOne
    @JoinColumn(name = "id_modelo")
    private Modelo modelo;

    @Column(name = "estado")
    private Integer estado;

    @Column(name = "disponible")
    private Boolean disponible;

    @Column(name = "pesaje_asociado")
    private Long pesajeAsociado;

    @Column(name = "observaciones")
    private String observaciones;
}