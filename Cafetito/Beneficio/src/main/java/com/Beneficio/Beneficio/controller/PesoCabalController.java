package com.Beneficio.Beneficio.controller;

import com.Beneficio.Beneficio.dto.ActualizarPesoBasculaRequest;
import com.Beneficio.Beneficio.model.ParcialidadBeneficio;
import com.Beneficio.Beneficio.service.ParcialidadBeneficioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/peso-cabal")
@RequiredArgsConstructor
public class PesoCabalController {

    private final ParcialidadBeneficioService parcialidadService;

    @GetMapping("/parcialidades/pendientes")
    public ResponseEntity<List<ParcialidadBeneficio>> listarPendientes() {
        return ResponseEntity.ok(
                parcialidadService.listarPendientesPesoCabal()
        );
    }

    @GetMapping("/parcialidades/pesadas")
    public ResponseEntity<List<ParcialidadBeneficio>> listarPesadas() {
        return ResponseEntity.ok(
                parcialidadService.listarPesadas()
        );
    }

    @GetMapping("/parcialidades/boletas")
    public ResponseEntity<List<ParcialidadBeneficio>> listarConBoleta() {
        return ResponseEntity.ok(
                parcialidadService.listarConBoleta()
        );
    }

    @PutMapping("/parcialidades/{idParcialidad}/peso")
    public ResponseEntity<ParcialidadBeneficio> actualizarPeso(
            @PathVariable Long idParcialidad,
            @RequestBody ActualizarPesoBasculaRequest request,
            @AuthenticationPrincipal UserDetails user) {

        String usuario = user != null ? user.getUsername() : "peso-cabal";

        return ResponseEntity.ok(
                parcialidadService.actualizarPesoBascula(
                        idParcialidad,
                        request.getPesoBascula(),
                        request.getTipoMedida(),
                        request.getObservaciones(),
                        usuario
                )
        );
    }

    @PutMapping("/parcialidades/{idParcialidad}/boleta")
    public ResponseEntity<ParcialidadBeneficio> generarBoleta(
            @PathVariable Long idParcialidad,
            @AuthenticationPrincipal UserDetails user) {

        String usuario = user != null ? user.getUsername() : "peso-cabal";

        return ResponseEntity.ok(
                parcialidadService.generarBoleta(
                        idParcialidad,
                        usuario
                )
        );
    }
}