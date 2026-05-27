package com.agricultor_service.agricultor.service;

import com.agricultor_service.agricultor.model.Agricultor;
import com.agricultor_service.agricultor.repository.AgricultorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgricultorService {

    private final AgricultorRepository agricultorRepository;

    public AgricultorService(AgricultorRepository agricultorRepository) {
        this.agricultorRepository = agricultorRepository;
    }

    public List<Agricultor> listar() {
        return agricultorRepository.findAll();
    }
}