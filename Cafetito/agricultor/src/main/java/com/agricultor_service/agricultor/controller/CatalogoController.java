package com.agricultor_service.agricultor.controller;

import com.agricultor_service.agricultor.model.DetalleCatalogo;
import com.agricultor_service.agricultor.repository.DetalleCatalogoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agricultor/catalogos")
public class CatalogoController {

    private final DetalleCatalogoRepository detalleCatalogoRepository;

    public CatalogoController(DetalleCatalogoRepository detalleCatalogoRepository) {
        this.detalleCatalogoRepository = detalleCatalogoRepository;
    }

    @GetMapping("/medidas")
    public ResponseEntity<List<DetalleCatalogo>> listarMedidas() {
        return ResponseEntity.ok(detalleCatalogoRepository.findByCatalogo_IdCatalogo(2L));
    }

    @GetMapping("/estados-pesaje")
    public ResponseEntity<List<DetalleCatalogo>> listarEstadosPesaje() {
        return ResponseEntity.ok(detalleCatalogoRepository.findByCatalogo_IdCatalogo(1L));
    }
}