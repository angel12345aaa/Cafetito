package com.agricultor_service.agricultor.service;

import com.agricultor_service.agricultor.model.Agricultor;
import com.agricultor_service.agricultor.model.Transportista;
import com.agricultor_service.agricultor.repository.AgricultorRepository;
import com.agricultor_service.agricultor.repository.TransportistaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransportistaService {

    private final TransportistaRepository transportistaRepository;
    private final AgricultorRepository agricultorRepository;

    public TransportistaService(TransportistaRepository transportistaRepository,
                                AgricultorRepository agricultorRepository) {
        this.transportistaRepository = transportistaRepository;
        this.agricultorRepository = agricultorRepository;
    }

    public List<Transportista> listarPorAgricultor(Long idAgricultor) {
        return transportistaRepository.findByAgricultor_IdAgricultor(idAgricultor);
    }

    public List<Transportista> listarDisponibles(Long idAgricultor) {
        return transportistaRepository.findByAgricultor_IdAgricultorAndDisponibleTrue(idAgricultor);
    }

    public Transportista crear(Long idAgricultor, Transportista transportista) {
        if (transportistaRepository.existsByCui(transportista.getCui())) {
            throw new RuntimeException("Ya existe un transportista con el CUI: " + transportista.getCui());
        }

        LocalDate hoy = LocalDate.now();
        LocalDate fechaNac = transportista.getFechaNacimiento();
        int edad = hoy.getYear() - fechaNac.getYear();
        if (fechaNac.plusYears(edad).isAfter(hoy)) edad--;
        if (edad < 18) {
            throw new RuntimeException("El transportista es menor de edad");
        }

        if (transportista.getFechaVenciLicencia().isBefore(hoy)) {
            throw new RuntimeException("La licencia del transportista está vencida");
        }

        Agricultor agricultor = agricultorRepository.findById(idAgricultor)
                .orElseThrow(() -> new RuntimeException("Agricultor no encontrado"));

        transportista.setAgricultor(agricultor);
        transportista.setDisponible(true);
        return transportistaRepository.save(transportista);
    }
}