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

        return parcialidadRepository.findByCuenta_IdCuenta(
                idCuenta
        );
    }

    public List<ParcialidadBeneficio> listarPendientesPesoCabal() {

        return parcialidadRepository.findByEstadoAndCuenta_EstadoIn(
                "RECIBIDA",
                List.of(
                        "PESAJE_INICIADO",
                        "PESAJE_FINALIZADO"
                )
        );
    }

    public List<ParcialidadBeneficio> listarPesadas() {

        return parcialidadRepository.findByEstado(
                "PESAJE_REALIZADO"
        );
    }

    public List<ParcialidadBeneficio> listarConBoleta() {

        return parcialidadRepository.findByBoletaTrue();
    }

    @Transactional
    public ParcialidadBeneficio recibir(
            ParcialidadBeneficio parcialidad,
            String usuario) {

        Cuenta cuenta =
                cuentaRepository.findById(
                        parcialidad.getCuenta().getIdCuenta()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Cuenta no encontrada"
                        ));

        validarCuenta(cuenta);

        validarTransporte(parcialidad);

        validarTransportista(parcialidad);

        validarDuplicado(parcialidad);

        Double nuevoAcumulado =
                cuenta.getPesoAcumulado()
                        + parcialidad.getPesoEnviado();

        Double maximo =
                cuenta.getPesoObjetivo()
                        + cuenta.getTolerancia();

        parcialidad.setCuenta(cuenta);

        if (nuevoAcumulado > maximo) {

            parcialidad.setEstado(
                    "RECHAZADA"
            );

            parcialidad.setDetalle(
                    "Excede peso permitido"
            );

            parcialidad.setFechaRecepcionParcialidad(
                    LocalDateTime.now()
            );

            parcialidadRepository.save(
                    parcialidad
            );

            throw new RuntimeException(
                    "Parcialidad rechazada"
            );
        }

        parcialidad.setEstado(
                "RECIBIDA"
        );

        parcialidad.setDetalle(
                "Pendiente Peso Cabal"
        );

        parcialidad.setFechaRecepcionParcialidad(
                LocalDateTime.now()
        );

        ParcialidadBeneficio guardada =
                parcialidadRepository.save(
                        parcialidad
                );

        cuenta.setPesoAcumulado(
                nuevoAcumulado
        );

        cuenta.setSaldoPendiente(
                cuenta.getPesoObjetivo()
                        - nuevoAcumulado
        );

        cuenta.setCantidadParcialidades(
                cuenta.getCantidadParcialidades() + 1
        );

        Double diferenciaFinal =
                nuevoAcumulado
                        - cuenta.getPesoObjetivo();

        if (Math.abs(
                diferenciaFinal
        ) <= cuenta.getTolerancia()) {

            cuenta.setEstado(
                    "PESAJE_FINALIZADO"
            );

            cuenta.setDiferenciaTotal(
                    diferenciaFinal
            );

            cuenta.setResultadoTolerancia(
                    "ACEPTADO_EN_PARAMETRO"
            );

        } else if (
                "CUENTA_CREADA".equals(
                        cuenta.getEstado()
                )) {

            cuenta.setEstado(
                    "PESAJE_INICIADO"
            );
        }

        Cuenta cuentaActualizada =
                cuentaRepository.saveAndFlush(
                        cuenta
                );

        guardada.setCuenta(
                cuentaActualizada
        );

        historialService.registrarCambio(
                cuentaActualizada,
                cuentaActualizada.getEstado(),
                cuentaActualizada.getDiferenciaTotal(),
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

    @Transactional
    public ParcialidadBeneficio actualizarPesoBascula(
            Long idParcialidad,
            Double pesoBascula,
            String tipoMedida,
            String observaciones,
            String usuario) {

        ParcialidadBeneficio parcialidad =
                parcialidadRepository.findById(
                        idParcialidad
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Parcialidad no encontrada"
                        ));

        if (!"RECIBIDA".equals(
                parcialidad.getEstado())) {

            throw new RuntimeException(
                    "La parcialidad no se encuentra disponible para pesaje"
            );
        }

        if (parcialidad.getPesoBascula() != null) {

            throw new RuntimeException(
                    "La parcialidad ya tiene peso báscula registrado"
            );
        }

        Cuenta cuenta =
                parcialidad.getCuenta();

        Double diferenciaPeso =
                pesoBascula
                        - parcialidad.getPesoEnviado();

        parcialidad.setPesoBascula(
                pesoBascula
        );

        parcialidad.setDiferenciaPeso(
                diferenciaPeso
        );

        parcialidad.setTipoMedida(
                tipoMedida
        );

        parcialidad.setObservaciones(
                observaciones
        );

        parcialidad.setEstado(
                "PESAJE_REALIZADO"
        );

        parcialidad.setDetalle(
                "Pesaje Realizado"
        );

        parcialidad.setFechaPesoBascula(
                LocalDateTime.now()
        );

        ParcialidadBeneficio actualizada =
                parcialidadRepository.save(
                        parcialidad
                );

        Double total =
                cuenta.getPesoBasculaTotal() == null
                        ? 0.0
                        : cuenta.getPesoBasculaTotal();

        cuenta.setPesoBasculaTotal(
                total + pesoBascula
        );

        cuentaRepository.saveAndFlush(
                cuenta
        );

        bitacoraService.registrarOperacion(
                "ACTUALIZAR_PESO_BASCULA",
                usuario,
                cuenta.getIdCuenta(),
                "Peso cabal parcialidad "
                        + idParcialidad
        );

        return actualizada;
    }

    @Transactional
    public ParcialidadBeneficio generarBoleta(
            Long idParcialidad,
            String usuario) {

        ParcialidadBeneficio parcialidad =
                parcialidadRepository.findById(
                        idParcialidad
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Parcialidad no encontrada"
                        ));

        if (!"PESAJE_REALIZADO".equals(
                parcialidad.getEstado())) {

            throw new RuntimeException(
                    "No se puede generar boleta sin pesaje"
            );
        }

        if (Boolean.TRUE.equals(
                parcialidad.getBoleta())) {

            throw new RuntimeException(
                    "La boleta ya existe"
            );
        }

        parcialidad.setBoleta(
                true
        );

        parcialidad.setFechaBoleta(
                LocalDateTime.now()
        );

        parcialidad.setDetalle(
                "Boleta Generada"
        );

        ParcialidadBeneficio actualizada =
                parcialidadRepository.save(
                        parcialidad
                );

        bitacoraService.registrarOperacion(
                "GENERAR_BOLETA",
                usuario,
                parcialidad.getCuenta().getIdCuenta(),
                "Boleta parcialidad "
                        + idParcialidad
        );

        return actualizada;
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

        if ("PESAJE_FINALIZADO".equals(
                cuenta.getEstado())) {

            throw new RuntimeException(
                    "Cuenta ya finalizada"
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
                        parcialidad.getIdParcialidadAgricultor())) {

            throw new RuntimeException(
                    "Parcialidad ya procesada"
            );
        }
    }
}