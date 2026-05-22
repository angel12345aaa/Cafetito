package com.Beneficio.Beneficio.controller;

import com.Beneficio.Beneficio.dto.CambioEstadoRequest;
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

    @GetMapping
    public ResponseEntity<List<Transito>> listar() {
        return ResponseEntity.ok(transitoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transito> obtener(@PathVariable Long id) {
        return transitoService.obtener(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/cuenta/{idCuenta}")
    public ResponseEntity<List<Transito>> listarPorCuenta(@PathVariable Long idCuenta) {
        return ResponseEntity.ok(transitoService.listarPorCuenta(idCuenta));
    }

    @GetMapping("/placa/{placa}")
    public ResponseEntity<List<Transito>> buscarPlaca(@PathVariable String placa) {
        return ResponseEntity.ok(transitoService.buscarPorPlaca(placa));
    }

    @GetMapping("/cui/{cui}")
    public ResponseEntity<List<Transito>> buscarCui(@PathVariable String cui) {
        return ResponseEntity.ok(transitoService.buscarPorCui(cui));
    }

    @GetMapping("/estado-transporte/{estado}")
    public ResponseEntity<List<Transito>> estadoTransporte(@PathVariable Integer estado) {
        return ResponseEntity.ok(transitoService.filtrarPorEstadoTransporte(estado));
    }

    @GetMapping("/estado-transportista/{estado}")
    public ResponseEntity<List<Transito>> estadoTransportista(@PathVariable Integer estado) {
        return ResponseEntity.ok(transitoService.filtrarPorEstadoTransportista(estado));
    }

    @PostMapping
    public ResponseEntity<Transito> registrar(@RequestBody Transito transito,
                                              @AuthenticationPrincipal UserDetails user) {

        String usuario = user != null ? user.getUsername() : "sistema";

        return ResponseEntity.ok(
                transitoService.registrarSalida(transito, usuario)
        );
    }

    @PutMapping("/{id}/llegada")
    public ResponseEntity<Transito> llegada(@PathVariable Long id,
                                            @AuthenticationPrincipal UserDetails user) {

        String usuario = user != null ? user.getUsername() : "sistema";

        return ResponseEntity.ok(
                transitoService.registrarLlegadaReal(id, usuario)
        );
    }

    @PutMapping("/{id}/estado-transporte")
    public ResponseEntity<Transito> cambiarEstadoTransporte(
            @PathVariable Long id,
            @RequestBody CambioEstadoRequest request,
            @AuthenticationPrincipal UserDetails user) {

        String usuario = user != null ? user.getUsername() : "sistema";

        return ResponseEntity.ok(
                transitoService.cambiarEstadoTransporte(
                        id,
                        request.getNuevoEstadoNumerico(),
                        request.getObservaciones(),
                        usuario
                )
        );
    }

    @PutMapping("/{id}/estado-transportista")
    public ResponseEntity<Transito> cambiarEstadoTransportista(
            @PathVariable Long id,
            @RequestBody CambioEstadoRequest request,
            @AuthenticationPrincipal UserDetails user) {

        String usuario = user != null ? user.getUsername() : "sistema";

        return ResponseEntity.ok(
                transitoService.cambiarEstadoTransportista(
                        id,
                        request.getNuevoEstadoNumerico(),
                        request.getObservaciones(),
                        usuario
                )
        );
    }
}