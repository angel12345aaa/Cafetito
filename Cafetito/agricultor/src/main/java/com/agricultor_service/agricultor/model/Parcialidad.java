package com.agricultor_service.agricultor.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "parcialidad", schema = "agricultor")
public class Parcialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parcialidad")
    private Long idParcialidad;

    @ManyToOne
    @JoinColumn(name = "id_pesaje", nullable = false)
    private Pesaje pesaje;

    @Column(name = "placa")
    private String placa;

    @Column(name = "id_transportista")
    private Long idTransportista;

    @ManyToOne
    @JoinColumn(name = "estado")
    private DetalleCatalogo estado;

    @Column(name = "peso_actual")
    private Double pesoActual;

    @Column(name = "diferencia_peso")
    private Double diferenciaPeso;

    @Column(name = "fecha_recepcion")
    private LocalDate fechaRecepcion;

    @Column(name = "hora_recepcion")
    private LocalTime horaRecepcion;

    @Column(name = "observaciones")
    private String observaciones;
}