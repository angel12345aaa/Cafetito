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

    private static final double TOLERANCIA_DEFAULT = 5.0;

    private static final String CUENTA_CREADA = "CUENTA_CREADA";
    private static final String CUENTA_INICIADA = "CUENTA_INICIADA";
    private static final String CUENTA_COMPLETADA = "CUENTA_COMPLETADA";
    private static final String CUENTA_CERRADA = "CUENTA_CERRADA";
    private static final String CUENTA_CONFIRMADA = "CUENTA_CONFIRMADA";

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

        if (cuenta.getPesoObjetivo() == null || cuenta.getPesoObjetivo() <= 0) {
            throw new RuntimeException("El peso objetivo de la cuenta debe ser mayor a 0");
        }

        if (cuenta.getFechaEnvio() == null) {
            cuenta.setFechaEnvio(LocalDateTime.now());
        }

        if (cuenta.getEstado() == null || cuenta.getEstado().isBlank()) {
            cuenta.setEstado(CUENTA_CREADA);
        }

        if (cuenta.getTolerancia() == null) {
            cuenta.setTolerancia(TOLERANCIA_DEFAULT);
        }

        cuenta.setPesoAcumulado(
                cuenta.getPesoAcumulado() != null ? cuenta.getPesoAcumulado() : 0.0
        );

        cuenta.setSaldoPendiente(
                cuenta.getPesoObjetivo() - cuenta.getPesoAcumulado()
        );

        cuenta.setCantidadParcialidades(
                cuenta.getCantidadParcialidades() != null ? cuenta.getCantidadParcialidades() : 0
        );

        Cuenta guardada = cuentaRepository.save(cuenta);

        historialService.registrarCambio(
                guardada,
                guardada.getEstado(),
                0.0,
                guardada.getTolerancia()
        );

        bitacoraService.registrarOperacion(
                "CREAR_CUENTA",
                usuario,
                guardada.getIdCuenta(),
                "Se creó la cuenta ID " + guardada.getIdCuenta()
                        + " con peso objetivo " + guardada.getPesoObjetivo()
        );

        return guardada;
    }

    @Transactional
    public Cuenta actualizar(Long id, Cuenta cuenta, String usuario) {

        Cuenta existente = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        if (!CUENTA_CREADA.equalsIgnoreCase(existente.getEstado())) {
            throw new RuntimeException(
                    "La cuenta se encuentra en estado: " + existente.getEstado()
                            + " no es posible editarla"
            );
        }

        if (cuenta.getPesoObjetivo() == null || cuenta.getPesoObjetivo() <= 0) {
            throw new RuntimeException("El peso objetivo de la cuenta debe ser mayor a 0");
        }

        existente.setIdAgricultor(cuenta.getIdAgricultor());
        existente.setPesoObjetivo(cuenta.getPesoObjetivo());
        existente.setSaldoPendiente(cuenta.getPesoObjetivo() - existente.getPesoAcumulado());
        existente.setFechaEnvio(cuenta.getFechaEnvio());

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
    public Cuenta cambiarEstado(Long id,
                                String nuevoEstado,
                                Double diferenciaTotal,
                                String usuario) {

        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        String estadoActual = cuenta.getEstado();

        if (estadoActual == null || estadoActual.isBlank()) {
            throw new RuntimeException("La cuenta no tiene estado actual");
        }

        if (nuevoEstado == null || nuevoEstado.isBlank()) {
            throw new RuntimeException("Debe indicar el nuevo estado");
        }

        estadoActual = estadoActual.trim().toUpperCase();
        nuevoEstado = nuevoEstado.trim().toUpperCase();

        if (estadoActual.equals(nuevoEstado)) {
            throw new RuntimeException("La cuenta ya se encuentra " + estadoActual);
        }

        validarCambioEstado(estadoActual, nuevoEstado);

        cuenta.setEstado(nuevoEstado);

        if (CUENTA_CERRADA.equals(nuevoEstado)) {
            cuenta.setFechaLlegada(LocalDateTime.now());
        }

        if (CUENTA_CONFIRMADA.equals(nuevoEstado)) {
            Double diferencia = cuenta.getDiferenciaTotal() != null
                    ? cuenta.getDiferenciaTotal()
                    : diferenciaTotal != null ? diferenciaTotal : 0.0;

            cuenta.setDiferenciaTotal(diferencia);
            cuenta.setTolerancia(
                    cuenta.getTolerancia() != null ? cuenta.getTolerancia() : TOLERANCIA_DEFAULT
            );
            cuenta.setResultadoTolerancia(calcularResultadoTolerancia(diferencia, cuenta.getTolerancia()));
        }

        Cuenta actualizada = cuentaRepository.save(cuenta);

        historialService.registrarCambio(
                actualizada,
                nuevoEstado,
                actualizada.getDiferenciaTotal() != null ? actualizada.getDiferenciaTotal() : 0.0,
                actualizada.getTolerancia() != null ? actualizada.getTolerancia() : TOLERANCIA_DEFAULT
        );

        bitacoraService.registrarOperacion(
                "CAMBIAR_ESTADO_CUENTA",
                usuario,
                actualizada.getIdCuenta(),
                "La cuenta ID " + actualizada.getIdCuenta() + " cambió a " + nuevoEstado
        );

        return actualizada;
    }

    private void validarCambioEstado(String estadoActual, String nuevoEstado) {

        if (CUENTA_COMPLETADA.equals(estadoActual)
                && CUENTA_CERRADA.equals(nuevoEstado)) {
            return;
        }

        if (CUENTA_CERRADA.equals(estadoActual)
                && CUENTA_CONFIRMADA.equals(nuevoEstado)) {
            return;
        }

        if (CUENTA_CERRADA.equals(nuevoEstado)) {
            throw new RuntimeException(
                    "La cuenta se encuentra en estado: '" + estadoActual
                            + "' no es posible Cerrar la Cuenta"
            );
        }

        if (CUENTA_CONFIRMADA.equals(nuevoEstado)) {
            throw new RuntimeException(
                    "La cuenta se encuentra en estado: '" + estadoActual
                            + "' no es posible Confirmar la Cuenta"
            );
        }

        throw new RuntimeException(
                "Cambio de estado no permitido: " + estadoActual + " a " + nuevoEstado
        );
    }

    private String calcularResultadoTolerancia(Double diferenciaTotal, Double tolerancia) {

        if (diferenciaTotal == null) {
            return "ACEPTADO_EN_PARAMETRO";
        }

        if (Math.abs(diferenciaTotal) <= tolerancia) {
            return "ACEPTADO_EN_PARAMETRO";
        }

        if (diferenciaTotal > tolerancia) {
            return "SOBRANTE";
        }

        return "FALTANTE";
    }
}