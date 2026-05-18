package com.Beneficio.Beneficio.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transito", schema = "beneficio")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Transito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transito")
    private Long idTransito;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cuenta", nullable = false)
    private Cuenta cuenta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_estado_transito")
    private DetalleCatalogo estadoTransito;

    @Column(name = "hora_salida")
    private LocalDateTime horaSalida;

    @Column(name = "hora_llegada_est")
    private LocalDateTime horaLlegadaEstimada;

    @Column(name = "hora_llegada_real")
    private LocalDateTime horaLlegadaReal;
}