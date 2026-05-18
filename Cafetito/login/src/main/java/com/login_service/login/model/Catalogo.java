package com.login_service.login.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa la tabla 'catalogo' en el esquema 'pesocabal'.
 */
@Entity
@Table(name = "catalogo", schema = "pesocabal") // Vincula al esquema correcto
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Catalogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_catalogo")
    private Long idCatalogo;

    @Column(name = "nombre_categoria", nullable = false)
    private String nombreCategoria;

    @Column(name = "descripcion")
    private String descripcion;
}