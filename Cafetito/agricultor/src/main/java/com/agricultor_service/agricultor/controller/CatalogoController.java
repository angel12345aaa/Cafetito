package com.agricultor_service.agricultor.controller;

import com.agricultor_service.agricultor.model.*;
import com.agricultor_service.agricultor.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agricultor/catalogos")
public class CatalogoController {

    private final DetalleCatalogoRepository detalleCatalogoRepository;
    private final MarcaRepository marcaRepository;
    private final ColorRepository colorRepository;
    private final LineaRepository lineaRepository;
    private final ModeloRepository modeloRepository;
    private final LicenciaRepository licenciaRepository;

    public CatalogoController(
            DetalleCatalogoRepository detalleCatalogoRepository,
            MarcaRepository marcaRepository,
            ColorRepository colorRepository,
            LineaRepository lineaRepository,
            ModeloRepository modeloRepository,
            LicenciaRepository licenciaRepository
    ) {
        this.detalleCatalogoRepository = detalleCatalogoRepository;
        this.marcaRepository = marcaRepository;
        this.colorRepository = colorRepository;
        this.lineaRepository = lineaRepository;
        this.modeloRepository = modeloRepository;
        this.licenciaRepository = licenciaRepository;
    }

    @GetMapping("/medidas")
    public ResponseEntity<List<DetalleCatalogo>> listarMedidas() {
        return ResponseEntity.ok(
                detalleCatalogoRepository.findByCatalogo_IdCatalogo(2L)
        );
    }

    @GetMapping("/estados-pesaje")
    public ResponseEntity<List<DetalleCatalogo>> listarEstadosPesaje() {
        return ResponseEntity.ok(
                detalleCatalogoRepository.findByCatalogo_IdCatalogo(1L)
        );
    }

    @GetMapping("/marcas")
    public ResponseEntity<List<Marca>> listarMarcas() {
        return ResponseEntity.ok(marcaRepository.findAll());
    }

    @GetMapping("/colores")
    public ResponseEntity<List<Color>> listarColores() {
        return ResponseEntity.ok(colorRepository.findAll());
    }

    @GetMapping("/lineas")
    public ResponseEntity<List<Linea>> listarLineas() {
        return ResponseEntity.ok(lineaRepository.findAll());
    }

    @GetMapping("/modelos")
    public ResponseEntity<List<Modelo>> listarModelos() {
        return ResponseEntity.ok(modeloRepository.findAll());
    }

    @GetMapping("/licencias")
    public ResponseEntity<List<Licencia>> listarLicencias() {
        return ResponseEntity.ok(licenciaRepository.findAll());
    }
}