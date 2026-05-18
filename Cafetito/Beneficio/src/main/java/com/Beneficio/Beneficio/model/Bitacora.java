package com.Beneficio.Beneficio.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "bitacora", schema = "beneficio")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Bitacora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bitacora")
    private Long idBitacora;

    @Column(name = "operacion", length = 100)
    private String operacion;

    @Column(name = "usuario", length = 100)
    private String usuario;

    @Column(name = "codigo_peso", length = 50)
    private String codigoPeso;

    @Column(name = "cuenta")
    private Long cuenta;

    @Column(name = "parcial")
    private Long parcial;

    @Column(name = "peso_real")
    private Double pesoReal;

    @Column(name = "peso_observado")
    private Double pesoObservado;

    @Column(name = "medida", length = 20)
    private String medida;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "extemporaneo")
    private Boolean extemporaneo;

    @Column(name = "transportista", length = 100)
    private String transportista;

    @Column(name = "transporte", length = 50)
    private String transporte;

    @Column(name = "estado", length = 50)
    private String estado;

    @Column(name = "observacion", length = 500)
    private String observacion;

    @Column(name = "fecha_sistema")
    private LocalDateTime fechaSistema;
}