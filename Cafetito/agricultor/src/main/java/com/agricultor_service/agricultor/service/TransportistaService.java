package com.agricultor_service.agricultor.service;

import com.agricultor_service.agricultor.model.Agricultor;
import com.agricultor_service.agricultor.model.Transportista;
import com.agricultor_service.agricultor.repository.AgricultorRepository;
import com.agricultor_service.agricultor.repository.TransportistaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class TransportistaService {

    private final TransportistaRepository transportistaRepository;
    private final AgricultorRepository agricultorRepository;

    public TransportistaService(
            TransportistaRepository transportistaRepository,
            AgricultorRepository agricultorRepository) {
        this.transportistaRepository = transportistaRepository;
        this.agricultorRepository = agricultorRepository;
    }

    public List<Transportista> listarPorAgricultor(Long idAgricultor) {
        validarAgricultorToken(idAgricultor);
        return transportistaRepository.findByAgricultor_IdAgricultor(idAgricultor);
    }

    public List<Transportista> listarDisponibles(Long idAgricultor) {
        validarAgricultorToken(idAgricultor);
        return transportistaRepository.findByAgricultor_IdAgricultorAndDisponibleTrue(idAgricultor);
    }

    public Transportista crear(Long idAgricultor, Transportista transportista) {
        validarAgricultorToken(idAgricultor);

        if (transportista.getCui() == null || transportista.getCui().isBlank()) {
            throw new RuntimeException("El CUI es obligatorio");
        }

        if (transportista.getNombre() == null || transportista.getNombre().isBlank()) {
            throw new RuntimeException("El nombre completo es obligatorio");
        }

        if (transportista.getFechaNacimiento() == null) {
            throw new RuntimeException("La fecha de nacimiento es obligatoria");
        }

        if (transportista.getTipoLicencia() == null || transportista.getTipoLicencia().getIdCatalogo() == null) {
            throw new RuntimeException("El tipo de licencia es obligatorio");
        }

        if (transportista.getFechaVenciLicencia() == null) {
            throw new RuntimeException("La fecha de vencimiento de licencia es obligatoria");
        }

        String cui = transportista.getCui().trim();

        if (transportistaRepository.existsByCui(cui)) {
            throw new RuntimeException("Ya existe un transportista con el CUI: " + cui);
        }

        LocalDate hoy = LocalDate.now();
        int edad = Period.between(transportista.getFechaNacimiento(), hoy).getYears();

        if (edad < 18) {
            throw new RuntimeException("El transportista es menor de edad");
        }

        if (transportista.getFechaVenciLicencia().isBefore(hoy)) {
            throw new RuntimeException("La licencia del transportista está vencida");
        }

        Agricultor agricultor = agricultorRepository.findById(idAgricultor)
                .orElseThrow(() -> new RuntimeException("Agricultor no encontrado"));

        transportista.setIdTransportista(null);
        transportista.setCui(cui);
        transportista.setNombre(transportista.getNombre().trim());
        transportista.setAgricultor(agricultor);
        transportista.setDisponible(true);
        transportista.setEstado(1);
        transportista.setPesajeAsociado(null);

        return transportistaRepository.save(transportista);
    }

    private void validarAgricultorToken(Long idAgricultor) {
        if (idAgricultor == null) {
            throw new RuntimeException("No se encontró el agricultor asociado al usuario autenticado");
        }
    }
}