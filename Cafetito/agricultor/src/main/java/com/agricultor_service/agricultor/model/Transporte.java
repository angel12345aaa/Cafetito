package com.agricultor_service.agricultor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Agricultor agricultor;

    @ManyToOne
    @JoinColumn(name = "id_marca")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "id_color")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Color color;

    @ManyToOne
    @JoinColumn(name = "id_linea")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Linea linea;

    @ManyToOne
    @JoinColumn(name = "id_modelo")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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