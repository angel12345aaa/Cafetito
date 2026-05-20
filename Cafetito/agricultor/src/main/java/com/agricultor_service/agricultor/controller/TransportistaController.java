package com.agricultor_service.agricultor.controller;

import com.agricultor_service.agricultor.model.Transportista;
import com.agricultor_service.agricultor.service.TransportistaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/agricultor/transportistas")
public class TransportistaController {

    private final TransportistaService transportistaService;

    public TransportistaController(TransportistaService transportistaService) {
        this.transportistaService = transportistaService;
    }

    @GetMapping
    public ResponseEntity<?> listar(HttpServletRequest request) {
        try {
            Long idAgricultor = (Long) request.getAttribute("idAgricultor");
            List<Transportista> transportistas = transportistaService.listarPorAgricultor(idAgricultor);
            return ResponseEntity.ok(transportistas);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/disponibles")
    public ResponseEntity<?> listarDisponibles(HttpServletRequest request) {
        try {
            Long idAgricultor = (Long) request.getAttribute("idAgricultor");
            List<Transportista> transportistas = transportistaService.listarDisponibles(idAgricultor);
            return ResponseEntity.ok(transportistas);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Transportista transportista,
                                   HttpServletRequest request) {
        try {
            Long idAgricultor = (Long) request.getAttribute("idAgricultor");
            Transportista nuevo = transportistaService.crear(idAgricultor, transportista);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}