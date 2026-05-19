package com.agricultor_service.agricultor.service;

import com.agricultor_service.agricultor.model.Parcialidad;
import com.agricultor_service.agricultor.repository.ParcialidadRepository;
import com.agricultor_service.agricultor.repository.TransporteRepository;
import com.agricultor_service.agricultor.repository.TransportistaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ParcialidadService {

    private final ParcialidadRepository parcialidadRepository;
    private final TransporteRepository transporteRepository;
    private final TransportistaRepository transportistaRepository;

    public ParcialidadService(ParcialidadRepository parcialidadRepository,
                              TransporteRepository transporteRepository,
                              TransportistaRepository transportistaRepository) {
        this.parcialidadRepository = parcialidadRepository;
        this.transporteRepository = transporteRepository;
        this.transportistaRepository = transportistaRepository;
    }

    public List<Parcialidad> listarPorCuenta(Long idCuenta) {
        return parcialidadRepository.findByIdCuenta(idCuenta);
    }

    public Parcialidad crear(Long idCuenta, Parcialidad parcialidad) {

        if (idCuenta == null) {
            throw new RuntimeException("El id de la cuenta es obligatorio");
        }

        if (parcialidad.getPlaca() == null || parcialidad.getPlaca().isBlank()) {
            throw new RuntimeException("La placa del transporte es obligatoria");
        }

        if (parcialidad.getIdTransportista() == null) {
            throw new RuntimeException("El transportista es obligatorio");
        }

        transporteRepository.findByPlaca(parcialidad.getPlaca())
                .orElseThrow(() -> new RuntimeException("Transporte no encontrado"));

        transportistaRepository.findById(parcialidad.getIdTransportista())
                .orElseThrow(() -> new RuntimeException("Transportista no encontrado"));

        parcialidad.setIdParcialidad(null);
        parcialidad.setIdCuenta(idCuenta);
        parcialidad.setFechaRecepcion(LocalDate.now());
        parcialidad.setHoraRecepcion(LocalTime.now());

        return parcialidadRepository.save(parcialidad);
    }
}