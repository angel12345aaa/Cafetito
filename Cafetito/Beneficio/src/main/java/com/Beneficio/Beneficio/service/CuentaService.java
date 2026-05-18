package com.Beneficio.Beneficio.service;

import com.Beneficio.Beneficio.model.Cuenta;
import com.Beneficio.Beneficio.repository.CuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final HistorialCuentaService historialService;
    private final BitacoraService bitacoraService;

    private static final Double TOLERANCIA_DEFAULT = 0.0;

    public List<Cuenta> listar() {
        return cuentaRepository.findAll();
    }

    public Optional<Cuenta> obtener(Long id) {
        return cuentaRepository.findById(id);
    }

    public List<Cuenta> listarPorAgricultor(Long nitAgricultor) {
        return cuentaRepository.findByIdAgricultor(nitAgricultor);
    }

    public List<Cuenta> listarPorEstado(String estado) {
        return cuentaRepository.findByEstado(estado);
    }

    @Transactional
    public Cuenta crear(Cuenta cuenta, String usuario) {

        cuenta.setIdCuenta(null);

        if (cuenta.getFechaEnvio() == null) {
            cuenta.setFechaEnvio(LocalDateTime.now());
        }

        if (cuenta.getEstado() == null || cuenta.getEstado().isBlank()) {
            cuenta.setEstado("ENVIADA");
        }

        Cuenta guardada = cuentaRepository.save(cuenta);

        historialService.registrarCambio(
                guardada,
                guardada.getEstado(),
                0.0,
                TOLERANCIA_DEFAULT
        );

        bitacoraService.registrarOperacion(
                "CREAR_CUENTA",
                usuario,
                guardada.getIdCuenta(),
                "Se creó la cuenta ID " + guardada.getIdCuenta()
        );

        return guardada;
    }

    @Transactional
    public Cuenta actualizar(Long id, Cuenta cuenta, String usuario) {

        Cuenta existente = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        existente.setIdAgricultor(cuenta.getIdAgricultor());
        existente.setPesoTotal(cuenta.getPesoTotal());
        existente.setCantidadParcialidades(cuenta.getCantidadParcialidades());
        existente.setFechaEnvio(cuenta.getFechaEnvio());
        existente.setFechaLlegada(cuenta.getFechaLlegada());

        Cuenta actualizada = cuentaRepository.save(existente);

        bitacoraService.registrarOperacion(
                "ACTUALIZAR_CUENTA",
                usuario,
                actualizada.getIdCuenta(),
                "Se actualizó la cuenta ID " + actualizada.getIdCuenta()
        );

        return actualizada;
    }

    @Transactional
    public Cuenta cambiarEstado(Long id, String nuevoEstado, Double diferenciaTotal, String usuario) {

        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        cuenta.setEstado(nuevoEstado);

        if ("RECIBIDA".equalsIgnoreCase(nuevoEstado)) {
            cuenta.setFechaLlegada(LocalDateTime.now());
        }

        Cuenta actualizada = cuentaRepository.save(cuenta);

        historialService.registrarCambio(
                actualizada,
                nuevoEstado,
                diferenciaTotal != null ? diferenciaTotal : 0.0,
                TOLERANCIA_DEFAULT
        );

        bitacoraService.registrarOperacion(
                "CAMBIAR_ESTADO_CUENTA",
                usuario,
                actualizada.getIdCuenta(),
                "La cuenta ID " + actualizada.getIdCuenta() + " cambió a " + nuevoEstado
        );

        return actualizada;
    }
}