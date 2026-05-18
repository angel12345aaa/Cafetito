package com.Beneficio.Beneficio.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalle_catalogo", schema = "beneficio")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DetalleCatalogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_catalogo")
    private Long idDetalleCatalogo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_catalogo", nullable = false)
    private Catalogo catalogo;

    @Column(name = "codigo", length = 50)
    private String codigo;

    @Column(name = "valor", length = 100)
    private String valor;

    @Column(name = "factor_conversion")
    private Double factorConversion;

    @Column(name = "orden")
    private Integer orden;
}