package com.Beneficio.Beneficio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cuenta", schema = "beneficio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cuenta")
    private Long idCuenta;

    @JsonProperty("nitAgricultor")
    @Column(name = "id_agricultor", nullable = false)
    private Long idAgricultor;

    @JsonProperty("pesoTotal")
    @Column(name = "peso_total")
    private Double pesoTotal;

    @JsonProperty("cantidadParcialidades")
    @Column(name = "cantidad_parcialidades")
    private Integer cantidadParcialidades;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    @JsonProperty("estado")
    @Column(name = "estado", length = 50)
    private String estado;

    @Column(name = "fecha_llegada")
    private LocalDateTime fechaLlegada;

    @JsonIgnore
    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HistorialCuenta> historial;

    @JsonIgnore
    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transito> transitos;
}