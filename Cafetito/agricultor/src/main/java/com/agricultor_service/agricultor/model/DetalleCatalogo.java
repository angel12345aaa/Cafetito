package com.agricultor_service.agricultor.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "detalle_catalogo", schema = "agricultor")
public class DetalleCatalogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_catalogo")
    private Long idDetalleCatalogo;

    @ManyToOne
    @JoinColumn(name = "id_catalogo", nullable = false)
    private Catalogo catalogo;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "valor")
    private String valor;

    @Column(name = "factor_conversion")
    private Double factorConversion;

    @Column(name = "orden")
    private Integer orden;
}