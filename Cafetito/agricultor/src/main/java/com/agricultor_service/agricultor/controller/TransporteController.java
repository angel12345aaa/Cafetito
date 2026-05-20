package com.agricultor_service.agricultor.controller;

import com.agricultor_service.agricultor.model.Transporte;
import com.agricultor_service.agricultor.service.TransporteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/agricultor/transportes")
public class TransporteController {

    private final TransporteService transporteService;

    public TransporteController(TransporteService transporteService) {
        this.transporteService = transporteService;
    }

    @GetMapping
    public ResponseEntity<?> listar(HttpServletRequest request) {
        try {
            Long idAgricultor = (Long) request.getAttribute("idAgricultor");
            List<Transporte> transportes = transporteService.listarPorAgricultor(idAgricultor);
            return ResponseEntity.ok(transportes);
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
            List<Transporte> transportes = transporteService.listarDisponibles(idAgricultor);
            return ResponseEntity.ok(transportes);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Transporte transporte,
                                   HttpServletRequest request) {
        try {
            Long idAgricultor = (Long) request.getAttribute("idAgricultor");
            Transporte nuevo = transporteService.crear(idAgricultor, transporte);
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