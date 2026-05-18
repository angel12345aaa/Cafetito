package com.Beneficio.Beneficio.controller;

import com.Beneficio.Beneficio.model.HistorialCuenta;
import com.Beneficio.Beneficio.service.HistorialCuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historial")
@RequiredArgsConstructor
public class HistorialCuentaController {

    private final HistorialCuentaService historialService;

    @GetMapping("/cuenta/{idCuenta}")
    public ResponseEntity<List<HistorialCuenta>> porCuenta(@PathVariable Long idCuenta) {
        return ResponseEntity.ok(historialService.listarPorCuenta(idCuenta));
    }

    @GetMapping("/agricultor/{idAgricultor}")
    public ResponseEntity<List<HistorialCuenta>> porAgricultor(@PathVariable Long idAgricultor) {
        return ResponseEntity.ok(historialService.listarPorAgricultor(idAgricultor));
    }
}