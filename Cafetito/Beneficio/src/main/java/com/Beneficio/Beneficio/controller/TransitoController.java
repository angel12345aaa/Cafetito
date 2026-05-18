package com.Beneficio.Beneficio.controller;

import com.Beneficio.Beneficio.model.Transito;
import com.Beneficio.Beneficio.service.TransitoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transitos")
@RequiredArgsConstructor
public class TransitoController {

    private final TransitoService transitoService;

    @GetMapping("/cuenta/{idCuenta}")
    public ResponseEntity<List<Transito>> porCuenta(@PathVariable Long idCuenta) {
        return ResponseEntity.ok(transitoService.listarPorCuenta(idCuenta));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transito> obtener(@PathVariable Long id) {
        return transitoService.obtener(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/salida")
    public ResponseEntity<Transito> registrarSalida(@RequestBody Transito t,
                                                    @AuthenticationPrincipal UserDetails user) {
        String u = (user != null) ? user.getUsername() : "sistema";
        return ResponseEntity.ok(transitoService.registrarSalida(t, u));
    }

    @PutMapping("/{id}/llegada")
    public ResponseEntity<Transito> registrarLlegada(@PathVariable Long id,
                                                     @AuthenticationPrincipal UserDetails user) {
        String u = (user != null) ? user.getUsername() : "sistema";
        return ResponseEntity.ok(transitoService.registrarLlegadaReal(id, u));
    }
}