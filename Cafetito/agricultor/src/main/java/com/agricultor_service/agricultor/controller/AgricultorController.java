package com.agricultor_service.agricultor.controller;

import com.agricultor_service.agricultor.model.Agricultor;
import com.agricultor_service.agricultor.service.AgricultorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agricultor")
public class AgricultorController {

    private final AgricultorService agricultorService;

    public AgricultorController(AgricultorService agricultorService) {
        this.agricultorService = agricultorService;
    }

    @GetMapping
    public ResponseEntity<List<Agricultor>> listar() {
        return ResponseEntity.ok(
                agricultorService.listar()
        );
    }
}