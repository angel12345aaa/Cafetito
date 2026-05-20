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
        validarAgricultorToken(idAgricultor);
        return transporteRepository.findByAgricultor_IdAgricultor(idAgricultor);
    }

    public List<Transporte> listarDisponibles(Long idAgricultor) {
        validarAgricultorToken(idAgricultor);
        return transporteRepository.findByAgricultor_IdAgricultorAndDisponibleTrue(idAgricultor);
    }

    public Transporte crear(Long idAgricultor, Transporte transporte) {
        validarAgricultorToken(idAgricultor);

        if (transporte.getPlaca() == null || transporte.getPlaca().isBlank()) {
            throw new RuntimeException("La placa es obligatoria");
        }

        if (transporte.getMarca() == null || transporte.getMarca().getIdCatalogo() == null) {
            throw new RuntimeException("La marca es obligatoria");
        }

        if (transporte.getColor() == null || transporte.getColor().getIdCatalogo() == null) {
            throw new RuntimeException("El color es obligatorio");
        }

        if (transporte.getLinea() == null || transporte.getLinea().getIdCatalogo() == null) {
            throw new RuntimeException("La línea es obligatoria");
        }

        if (transporte.getModelo() == null || transporte.getModelo().getIdCatalogo() == null) {
            throw new RuntimeException("El modelo es obligatorio");
        }

        String placa = transporte.getPlaca().trim().toUpperCase();

        if (transporteRepository.existsByPlaca(placa)) {
            throw new RuntimeException("Ya existe un transporte con la placa: " + placa);
        }

        Agricultor agricultor = agricultorRepository.findById(idAgricultor)
                .orElseThrow(() -> new RuntimeException("Agricultor no encontrado"));

        transporte.setIdTransporte(null);
        transporte.setPlaca(placa);
        transporte.setAgricultor(agricultor);
        transporte.setDisponible(true);
        transporte.setEstado(1);
        transporte.setPesajeAsociado(null);

        return transporteRepository.save(transporte);
    }

    private void validarAgricultorToken(Long idAgricultor) {
        if (idAgricultor == null) {
            throw new RuntimeException("No se encontró el agricultor asociado al usuario autenticado");
        }
    }
}