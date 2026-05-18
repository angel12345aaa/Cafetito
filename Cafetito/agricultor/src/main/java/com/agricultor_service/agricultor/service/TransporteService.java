package com.agricultor_service.agricultor.service;

import com.agricultor_service.agricultor.model.Agricultor;
import com.agricultor_service.agricultor.model.Transporte;
import com.agricultor_service.agricultor.repository.AgricultorRepository;
import com.agricultor_service.agricultor.repository.TransporteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransporteService {

    private final TransporteRepository transporteRepository;
    private final AgricultorRepository agricultorRepository;

    public TransporteService(TransporteRepository transporteRepository,
                             AgricultorRepository agricultorRepository) {
        this.transporteRepository = transporteRepository;
        this.agricultorRepository = agricultorRepository;
    }

    public List<Transporte> listarPorAgricultor(Long idAgricultor) {
        return transporteRepository.findByAgricultor_IdAgricultor(idAgricultor);
    }

    public List<Transporte> listarDisponibles(Long idAgricultor) {
        return transporteRepository.findByAgricultor_IdAgricultorAndDisponibleTrue(idAgricultor);
    }

    public Transporte crear(Long idAgricultor, Transporte transporte) {
        if (transporteRepository.existsByPlaca(transporte.getPlaca())) {
            throw new RuntimeException("Ya existe un transporte con la placa: " + transporte.getPlaca());
        }

        Agricultor agricultor = agricultorRepository.findById(idAgricultor)
                .orElseThrow(() -> new RuntimeException("Agricultor no encontrado"));

        transporte.setAgricultor(agricultor);
        transporte.setDisponible(true);
        return transporteRepository.save(transporte);
    }
}