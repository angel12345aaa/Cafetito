package com.Beneficio.Beneficio.service;

import com.Beneficio.Beneficio.model.Cuenta;
import com.Beneficio.Beneficio.model.ParcialidadBeneficio;
import com.Beneficio.Beneficio.repository.CuentaRepository;
import com.Beneficio.Beneficio.repository.ParcialidadBeneficioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParcialidadBeneficioService {

    private final ParcialidadBeneficioRepository parcialidadRepository;
    private final CuentaRepository cuentaRepository;
    private final HistorialCuentaService historialCuentaService;
    private final BitacoraService bitacoraService;

    private static final String CUENTA_CREADA = "CUENTA_CREADA";
    private static final String CUENTA_INICIADA = "CUENTA_INICIADA";
    private static final String CUENTA_COMPLETADA = "CUENTA_COMPLETADA";

    private static final String PARCIALIDAD_ACEPTADA = "ACEPTADA";
    private static final String PARCIALIDAD_RECHAZADA = "RECHAZADA";

    private static final double TOLERANCIA_DEFAULT = 5.0;

    public List<ParcialidadBeneficio> listarPorCuenta(Long idCuenta) {
        return parcialidadRepository.findByCuenta_IdCuenta(idCuenta);
    }

    @Transactional
    public ParcialidadBeneficio recibirParcialidad(ParcialidadBeneficio parcialidad, String usuario) {

        if (parcialidad.getCuenta() == null || parcialidad.getCuenta().getIdCuenta() == null) {
            throw new RuntimeException("Debe indicar la cuenta a la que pertenece la parcialidad");
        }

        if (parcialidad.getPeso() == null || parcialidad.getPeso() <= 0) {
            throw new RuntimeException("El peso de la parcialidad debe ser mayor a 0");
        }

        if (parcialidad.getIdParcialidadAgricultor() != null
                && parcialidadRepository.existsByIdParcialidadAgricultor(parcialidad.getIdParcialidadAgricultor())) {
            throw new RuntimeException("La parcialidad ya fue registrada en Beneficio");
        }

        Cuenta cuenta = cuentaRepository.findById(parcialidad.getCuenta().getIdCuenta())
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        validarCuentaPuedeRecibirParcialidad(cuenta);

        Double tolerancia = cuenta.getTolerancia() != null ? cuenta.getTolerancia() : TOLERANCIA_DEFAULT;
        Double pesoObjetivo = cuenta.getPesoObjetivo();

        if (pesoObjetivo == null || pesoObjetivo <= 0) {
            throw new RuntimeException("La cuenta no tiene peso objetivo válido");
        }

        Double pesoAcumuladoActual = cuenta.getPesoAcumulado() != null ? cuenta.getPesoAcumulado() : 0.0;
        Double nuevoAcumulado = pesoAcumuladoActual + parcialidad.getPeso();
        Double diferencia = nuevoAcumulado - pesoObjetivo;

        if (diferencia > tolerancia) {
            parcialidad.setEstado(PARCIALIDAD_RECHAZADA);
            parcialidad.setFechaRegistro(LocalDateTime.now());
            parcialidad.setObservaciones("Parcialidad rechazada: excede el peso objetivo por más de la tolerancia permitida");

            ParcialidadBeneficio rechazada = parcialidadRepository.save(parcialidad);

            bitacoraService.registrarOperacion(
                    "RECHAZAR_PARCIALIDAD",
                    usuario,
                    cuenta.getIdCuenta(),
                    "Parcialidad rechazada. Peso recibido: " + parcialidad.getPeso()
                            + ", acumulado actual: " + pesoAcumuladoActual
                            + ", objetivo: " + pesoObjetivo
            );

            return rechazada;
        }

        parcialidad.setCuenta(cuenta);
        parcialidad.setEstado(PARCIALIDAD_ACEPTADA);
        parcialidad.setFechaRegistro(LocalDateTime.now());

        ParcialidadBeneficio guardada = parcialidadRepository.save(parcialidad);

        cuenta.setPesoAcumulado(nuevoAcumulado);
        cuenta.setSaldoPendiente(pesoObjetivo - nuevoAcumulado);
        cuenta.setCantidadParcialidades(
                cuenta.getCantidadParcialidades() == null
                        ? 1
                        : cuenta.getCantidadParcialidades() + 1
        );

        if (CUENTA_CREADA.equalsIgnoreCase(cuenta.getEstado())) {
            cuenta.setEstado(CUENTA_INICIADA);
        }

        Double diferenciaFinal = cuenta.getPesoAcumulado() - pesoObjetivo;

        if (Math.abs(diferenciaFinal) <= tolerancia) {
            cuenta.setEstado(CUENTA_COMPLETADA);
            cuenta.setDiferenciaTotal(diferenciaFinal);
            cuenta.setResultadoTolerancia("ACEPTADO_EN_PARAMETRO");
        }

        Cuenta actualizada = cuentaRepository.save(cuenta);

        historialCuentaService.registrarCambio(
                actualizada,
                actualizada.getEstado(),
                actualizada.getDiferenciaTotal() != null ? actualizada.getDiferenciaTotal() : diferenciaFinal,
                tolerancia
        );

        bitacoraService.registrarOperacion(
                "RECIBIR_PARCIALIDAD",
                usuario,
                actualizada.getIdCuenta(),
                "Parcialidad aceptada. Peso recibido: " + parcialidad.getPeso()
                        + ", acumulado: " + actualizada.getPesoAcumulado()
                        + ", saldo: " + actualizada.getSaldoPendiente()
        );

        return guardada;
    }

    private void validarCuentaPuedeRecibirParcialidad(Cuenta cuenta) {

        if (cuenta.getEstado() == null || cuenta.getEstado().isBlank()) {
            throw new RuntimeException("La cuenta no tiene estado");
        }

        String estado = cuenta.getEstado().trim().toUpperCase();

        if (!estado.equals(CUENTA_CREADA)
                && !estado.equals(CUENTA_INICIADA)) {
            throw new RuntimeException(
                    "La cuenta se encuentra en estado: "
                            + cuenta.getEstado()
                            + ". No puede recibir parcialidades"
            );
        }
    }
}