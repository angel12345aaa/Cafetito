package com.login_service.login.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usuario", schema = "pesocabal")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @Column(name = "id_rol", nullable = false)
    private Integer idRol;

    @Column(name = "estado", nullable = false)
    private Integer estado;

    @Column(name = "id_agricultor")
    private Integer idAgricultor;
}