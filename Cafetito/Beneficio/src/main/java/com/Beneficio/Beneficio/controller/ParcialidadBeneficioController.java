package com.Beneficio.Beneficio.controller;

import com.Beneficio.Beneficio.model.ParcialidadBeneficio;
import com.Beneficio.Beneficio.service.ParcialidadBeneficioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parcialidades")
@RequiredArgsConstructor
public class ParcialidadBeneficioController {

    private final ParcialidadBeneficioService parcialidadService;

    @GetMapping("/cuenta/{idCuenta}")
    public ResponseEntity<List<ParcialidadBeneficio>> listarPorCuenta(@PathVariable Long idCuenta) {
        return ResponseEntity.ok(
                parcialidadService.listarPorCuenta(idCuenta)
        );
    }

    @PostMapping
    public ResponseEntity<ParcialidadBeneficio> recibirParcialidad(
            @RequestBody ParcialidadBeneficio parcialidad,
            @AuthenticationPrincipal UserDetails user) {

        String usuario = user != null ? user.getUsername() : "sistema";

        return ResponseEntity.ok(
                parcialidadService.recibirParcialidad(parcialidad, usuario)
        );
    }

    @PostMapping("/interno")
    public ResponseEntity<ParcialidadBeneficio> recibirParcialidadInterna(
            @RequestBody ParcialidadBeneficio parcialidad) {

        return ResponseEntity.ok(
                parcialidadService.recibirParcialidad(parcialidad, "micro-agricultor")
        );
    }
}