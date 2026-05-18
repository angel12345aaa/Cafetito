package com.Beneficio.Beneficio.controller;

import com.Beneficio.Beneficio.model.Catalogo;
import com.Beneficio.Beneficio.model.DetalleCatalogo;
import com.Beneficio.Beneficio.service.CatalogoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogos")
@RequiredArgsConstructor
public class CatalogoController {

    private final CatalogoService catalogoService;

    @GetMapping
    public ResponseEntity<List<Catalogo>> listar() {
        return ResponseEntity.ok(catalogoService.listar());
    }

    @PostMapping
    public ResponseEntity<Catalogo> crear(@RequestBody Catalogo c) {
        return ResponseEntity.ok(catalogoService.crear(c));
    }

    @GetMapping("/{nombre}/detalles")
    public ResponseEntity<List<DetalleCatalogo>> detallesPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(catalogoService.detallesPorNombre(nombre));
    }

    @PostMapping("/detalle")
    public ResponseEntity<DetalleCatalogo> crearDetalle(@RequestBody DetalleCatalogo d) {
        return ResponseEntity.ok(catalogoService.crearDetalle(d));
    }
}