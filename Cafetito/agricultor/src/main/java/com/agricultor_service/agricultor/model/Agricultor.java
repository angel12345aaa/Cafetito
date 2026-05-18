package com.agricultor_service.agricultor.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "agricultor", schema = "agricultor")
public class Agricultor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agricultor")
    private Long idAgricultor;

    @Column(name = "nit", nullable = false, unique = true)
    private String nit;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "fecha_creacion")
    private java.time.LocalDateTime fechaCreacion;
}