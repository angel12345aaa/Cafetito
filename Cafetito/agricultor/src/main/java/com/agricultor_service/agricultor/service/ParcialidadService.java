package com.agricultor_service.agricultor.service;

import com.agricultor_service.agricultor.model.Parcialidad;
import com.agricultor_service.agricultor.model.Pesaje;
import com.agricultor_service.agricultor.model.Transporte;
import com.agricultor_service.agricultor.model.Transportista;
import com.agricultor_service.agricultor.repository.ParcialidadRepository;
import com.agricultor_service.agricultor.repository.PesajeRepository;
import com.agricultor_service.agricultor.repository.TransporteRepository;
import com.agricultor_service.agricultor.repository.TransportistaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ParcialidadService {

    private final ParcialidadRepository parcialidadRepository;
    private final PesajeRepository pesajeRepository;
    private final TransporteRepository transporteRepository;
    private final TransportistaRepository transportistaRepository;

    public ParcialidadService(ParcialidadRepository parcialidadRepository,
                              PesajeRepository pesajeRepository,
                              TransporteRepository transporteRepository,
                              TransportistaRepository transportistaRepository) {
        this.parcialidadRepository = parcialidadRepository;
        this.pesajeRepository = pesajeRepository;
        this.transporteRepository = transporteRepository;
        this.transportistaRepository = transportistaRepository;
    }

    public List<Parcialidad> listarPorPesaje(Long idPesaje) {
        return parcialidadRepository.findByPesaje_IdPesaje(idPesaje);
    }

    public Parcialidad crear(Long idPesaje, Parcialidad parcialidad) {
        Pesaje pesaje = pesajeRepository.findById(idPesaje)
                .orElseThrow(() -> new RuntimeException("Pesaje no encontrado"));

        // FA06: validar estado de la cuenta
        if (pesaje.getEstado() == null || pesaje.getEstado().getValor() == null
                || !pesaje.getEstado().getValor().equalsIgnoreCase("Cuenta Creada")) {
            throw new RuntimeException("No se puede agregar parcialidades, el estado del pesaje no es 'Cuenta Creada'");
        }

        Transporte transporte = transporteRepository.findByPlaca(parcialidad.getPlaca())
                .orElseThrow(() -> new RuntimeException("Transporte no encontrado"));

        transportistaRepository.findById(parcialidad.getIdTransportista())
                .orElseThrow(() -> new RuntimeException("Transportista no encontrado"));

        parcialidad.setPesaje(pesaje);
        parcialidad.setFechaRecepcion(LocalDate.now());
        parcialidad.setHoraRecepcion(LocalTime.now());

        Parcialidad saved = parcialidadRepository.save(parcialidad);

        // Actualizar cantidad de parcialidades en el pesaje
        pesaje.setCantidadParcialidades(pesaje.getCantidadParcialidades() + 1);
        pesajeRepository.save(pesaje);

        return saved;
    }
}