package com.Beneficio.Beneficio.service;

import com.Beneficio.Beneficio.model.Transito;
import com.Beneficio.Beneficio.repository.TransitoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransitoService {

    private final TransitoRepository transitoRepository;
    private final BitacoraService bitacoraService;

    public List<Transito> listarPorCuenta(Long idCuenta) {
        return transitoRepository.findByCuenta_IdCuenta(idCuenta);
    }

    public Optional<Transito> obtener(Long id) {
        return transitoRepository.findById(id);
    }

    @Transactional
    public Transito registrarSalida(Transito t, String usuario) {
        t.setHoraSalida(LocalDateTime.now());
        Transito guardado = transitoRepository.save(t);
        bitacoraService.registrarOperacion(
                "REGISTRAR_SALIDA", usuario, t.getCuenta().getIdCuenta(),
                "Salida registrada para cuenta " + t.getCuenta().getIdCuenta()
        );
        return guardado;
    }

    @Transactional
    public Transito registrarLlegadaReal(Long idTransito, String usuario) {
        Transito t = transitoRepository.findById(idTransito)
                .orElseThrow(() -> new RuntimeException("Tránsito no encontrado: " + idTransito));
        t.setHoraLlegadaReal(LocalDateTime.now());
        Transito guardado = transitoRepository.save(t);
        bitacoraService.registrarOperacion(
                "REGISTRAR_LLEGADA", usuario, t.getCuenta().getIdCuenta(),
                "Llegada real registrada para tránsito " + idTransito
        );
        return guardado;
    }
}