package com.agricultor_service.agricultor.controller;

import com.agricultor_service.agricultor.model.Parcialidad;
import com.agricultor_service.agricultor.service.ParcialidadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/agricultor/cuentas/{idCuenta}/parcialidades")
public class ParcialidadController {

    private final ParcialidadService parcialidadService;

    public ParcialidadController(ParcialidadService parcialidadService) {
        this.parcialidadService = parcialidadService;
    }

    @GetMapping
    public ResponseEntity<?> listar(@PathVariable Long idCuenta) {
        try {
            List<Parcialidad> parcialidades = parcialidadService.listarPorCuenta(idCuenta);
            return ResponseEntity.ok(parcialidades);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@PathVariable Long idCuenta,
                                   @RequestBody Parcialidad parcialidad) {
        try {
            Parcialidad nueva = parcialidadService.crear(idCuenta, parcialidad);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}