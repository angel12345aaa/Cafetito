package com.Beneficio.Beneficio.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "catalogo", schema = "beneficio")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Catalogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_catalogo")
    private Long idCatalogo;

    @Column(name = "nombre_catalogo", nullable = false, length = 100)
    private String nombreCatalogo;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @OneToMany(mappedBy = "catalogo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleCatalogo> detalles;
}