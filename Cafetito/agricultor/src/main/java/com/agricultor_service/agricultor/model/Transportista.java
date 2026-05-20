package com.agricultor_service.agricultor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transportista", schema = "agricultor")
public class Transportista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transportista")
    private Long idTransportista;

    @ManyToOne
    @JoinColumn(name = "id_agricultor", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Agricultor agricultor;

    @Column(name = "cui", nullable = false, unique = true)
    private String cui;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @ManyToOne
    @JoinColumn(name = "tipo_licencia")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Licencia tipoLicencia;

    @Column(name = "fecha_venci_licencia")
    private LocalDate fechaVenciLicencia;

    @Column(name = "estado")
    private Integer estado;

    @Column(name = "disponible")
    private Boolean disponible;

    @Column(name = "pesaje_asociado")
    private Long pesajeAsociado;
}