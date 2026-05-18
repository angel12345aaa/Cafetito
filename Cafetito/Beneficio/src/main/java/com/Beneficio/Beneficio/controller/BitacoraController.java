package com.Beneficio.Beneficio.controller;

import com.Beneficio.Beneficio.model.Bitacora;
import com.Beneficio.Beneficio.service.BitacoraService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bitacora")
@RequiredArgsConstructor
public class BitacoraController {

    private final BitacoraService bitacoraService;

    @GetMapping
    public ResponseEntity<List<Bitacora>> listar() {
        return ResponseEntity.ok(bitacoraService.listar());
    }

    @GetMapping("/cuenta/{cuenta}")
    public ResponseEntity<List<Bitacora>> porCuenta(@PathVariable Long cuenta) {
        return ResponseEntity.ok(bitacoraService.listarPorCuenta(cuenta));
    }

    @GetMapping("/usuario/{usuario}")
    public ResponseEntity<List<Bitacora>> porUsuario(@PathVariable String usuario) {
        return ResponseEntity.ok(bitacoraService.listarPorUsuario(usuario));
    }

    @GetMapping("/rango")
    public ResponseEntity<List<Bitacora>> porRango(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return ResponseEntity.ok(bitacoraService.listarPorRangoFechas(desde, hasta));
    }
}