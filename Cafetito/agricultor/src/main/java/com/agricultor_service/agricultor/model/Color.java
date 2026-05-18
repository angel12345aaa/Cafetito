package com.agricultor_service.agricultor.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "color", schema = "agricultor")
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_catalogo")
    private Long idCatalogo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "id_catalogo_publico")
    private Integer idCatalogoPublico;
}