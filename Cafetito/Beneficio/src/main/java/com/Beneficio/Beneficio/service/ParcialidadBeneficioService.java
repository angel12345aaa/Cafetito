package com.Beneficio.Beneficio.service;

import com.Beneficio.Beneficio.model.Cuenta;
import com.Beneficio.Beneficio.model.ParcialidadBeneficio;
import com.Beneficio.Beneficio.repository.CuentaRepository;
import com.Beneficio.Beneficio.repository.ParcialidadBeneficioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParcialidadBeneficioService {

    private final ParcialidadBeneficioRepository parcialidadRepository;
    private final CuentaRepository cuentaRepository;
    private final HistorialCuentaService historialService;
    private final BitacoraService bitacoraService;

    public List<ParcialidadBeneficio> listarPorCuenta(Long idCuenta) {

        return parcialidadRepository.findByCuenta_IdCuenta(idCuenta);
    }

    @Transactional
    public ParcialidadBeneficio recibir(ParcialidadBeneficio parcialidad,
                                        String usuario) {

        Cuenta cuenta = cuentaRepository.findById(
                parcialidad.getCuenta().getIdCuenta()
        ).orElseThrow(() ->
                new RuntimeException("Cuenta no encontrada"));

        validarCuenta(cuenta);
        validarTransporte(parcialidad);
        validarTransportista(parcialidad);
        validarDuplicado(parcialidad);

        Double nuevoAcumulado =
                cuenta.getPesoAcumulado() + parcialidad.getPesoEnviado();

        Double maximo =
                cuenta.getPesoObjetivo() + cuenta.getTolerancia();

        parcialidad.setCuenta(cuenta);

        if (nuevoAcumulado > maximo) {

            parcialidad.setEstado("RECHAZADA");
            parcialidad.setDetalle("Excede peso permitido");
            parcialidad.setFechaRecepcionParcialidad(LocalDateTime.now());

            ParcialidadBeneficio rechazada =
                    parcialidadRepository.save(parcialidad);

            rechazada.setCuenta(cuenta);

            throw new RuntimeException("Parcialidad rechazada");
        }

        parcialidad.setEstado("RECIBIDA");
        parcialidad.setDetalle("Pendiente Peso Cabal");
        parcialidad.setFechaRecepcionParcialidad(LocalDateTime.now());

        ParcialidadBeneficio guardada =
                parcialidadRepository.save(parcialidad);

        cuenta.setPesoAcumulado(nuevoAcumulado);

        cuenta.setSaldoPendiente(
                cuenta.getPesoObjetivo() - nuevoAcumulado
        );

        cuenta.setCantidadParcialidades(
                cuenta.getCantidadParcialidades() + 1
        );

        Double diferenciaFinal =
                nuevoAcumulado - cuenta.getPesoObjetivo();

        if (Math.abs(diferenciaFinal)
                <= cuenta.getTolerancia()) {

            cuenta.setEstado(
                    "PESAJE_FINALIZADO"
            );

            cuenta.setDiferenciaTotal(
                    diferenciaFinal
            );

            cuenta.setResultadoTolerancia(
                    "ACEPTADO_EN_PARAMETRO"
            );

        } else if ("CUENTA_CREADA".equals(
                cuenta.getEstado())) {

            cuenta.setEstado(
                    "PESAJE_INICIADO"
            );
        }

        historialService.registrarCambio(
                cuentaActualizada,
                cuentaActualizada.getEstado(),
                0.0,
                cuentaActualizada.getTolerancia()
        );

        bitacoraService.registrarOperacion(
                "RECIBIR_PARCIALIDAD",
                usuario,
                cuentaActualizada.getIdCuenta(),
                "Parcialidad "
                        + guardada.getIdParcialidadBeneficio()
                        + " recibida"
        );

        return guardada;
    }

    private void validarCuenta(Cuenta cuenta) {

        if ("CUENTA_CERRADA".equals(
                cuenta.getEstado())) {

            throw new RuntimeException(
                    "Cuenta cerrada"
            );
        }

        if ("CUENTA_CONFIRMADA".equals(
                cuenta.getEstado())) {

            throw new RuntimeException(
                    "Cuenta confirmada"
            );
        }
    }

    private void validarTransporte(
            ParcialidadBeneficio parcialidad) {

        if (parcialidad.getEstadoTransporte() == null
                || parcialidad.getEstadoTransporte() != 1) {

            throw new RuntimeException(
                    "Transporte bloqueado"
            );
        }
    }

    private void validarTransportista(
            ParcialidadBeneficio parcialidad) {

        if (parcialidad.getEstadoTransportista() == null
                || parcialidad.getEstadoTransportista() != 1) {

            throw new RuntimeException(
                    "Transportista bloqueado"
            );
        }
    }

    private void validarDuplicado(
            ParcialidadBeneficio parcialidad) {

        if (parcialidadRepository
                .existsByIdParcialidadAgricultor(
                        parcialidad.getIdParcialidadAgricultor()
                )) {

            throw new RuntimeException(
                    "Parcialidad ya procesada"
            );
        }
    }

}