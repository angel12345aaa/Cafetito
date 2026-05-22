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

    public List<Transito> listar() {
        return transitoRepository.findAll();
    }

    public List<Transito> listarPorCuenta(Long idCuenta) {
        return transitoRepository.findByCuenta_IdCuenta(idCuenta);
    }

    public Optional<Transito> obtener(Long id) {
        return transitoRepository.findById(id);
    }

    public List<Transito> buscarPorPlaca(String placa) {
        return transitoRepository.findByPlacaContainingIgnoreCase(placa);
    }

    public List<Transito> buscarPorCui(String cui) {
        return transitoRepository.findByCuiTransportistaContainingIgnoreCase(cui);
    }

    public List<Transito> filtrarPorEstadoTransporte(Integer estado) {
        return transitoRepository.findByEstadoTransporte(estado);
    }

    public List<Transito> filtrarPorEstadoTransportista(Integer estado) {
        return transitoRepository.findByEstadoTransportista(estado);
    }

    @Transactional
    public Transito registrarSalida(Transito transito, String usuario) {

        if (transito.getEstadoTransporte() == null || transito.getEstadoTransporte() == 0) {
            throw new RuntimeException("El transporte " + transito.getPlaca() + " no se encuentra activo.");
        }

        if (transito.getEstadoTransportista() == null || transito.getEstadoTransportista() == 0) {
            throw new RuntimeException("El transportista " + transito.getCuiTransportista() + " no se encuentra activo.");
        }

        transito.setFechaRegistro(LocalDateTime.now());
        transito.setHoraSalida(LocalDateTime.now());

        Transito guardado = transitoRepository.save(transito);

        bitacoraService.registrarOperacion(
                "REGISTRAR_TRANSITO",
                usuario,
                transito.getCuenta().getIdCuenta(),
                "Se registró tránsito para la placa " + transito.getPlaca()
        );

        return guardado;
    }

    @Transactional
    public Transito registrarLlegadaReal(Long idTransito, String usuario) {

        Transito transito = transitoRepository.findById(idTransito)
                .orElseThrow(() -> new RuntimeException("Tránsito no encontrado: " + idTransito));

        transito.setHoraLlegadaReal(LocalDateTime.now());

        Transito guardado = transitoRepository.save(transito);

        bitacoraService.registrarOperacion(
                "REGISTRAR_LLEGADA",
                usuario,
                transito.getCuenta().getIdCuenta(),
                "Llegada real registrada para tránsito " + idTransito
        );

        return guardado;
    }

    @Transactional
    public Transito cambiarEstadoTransporte(
            Long idTransito,
            Integer nuevoEstado,
            String observaciones,
            String usuario) {

        Transito transito = transitoRepository.findById(idTransito)
                .orElseThrow(() -> new RuntimeException("Tránsito no encontrado: " + idTransito));

        if (transito.getEstadoTransporte() != null
                && transito.getEstadoTransporte().equals(nuevoEstado)) {

            String estadoActual = nuevoEstado == 1 ? "Activo" : "Inactivo";
            throw new RuntimeException("El transporte ya se encuentra " + estadoActual);
        }

        transito.setEstadoTransporte(nuevoEstado);
        transito.setObservacionTransporte(observaciones);

        Transito guardado = transitoRepository.save(transito);

        bitacoraService.registrarOperacion(
                "CAMBIAR_ESTADO_TRANSPORTE",
                usuario,
                transito.getCuenta().getIdCuenta(),
                "El estado del transporte " + transito.getPlaca() + " se actualizó con éxito"
        );

        return guardado;
    }

    @Transactional
    public Transito cambiarEstadoTransportista(
            Long idTransito,
            Integer nuevoEstado,
            String observaciones,
            String usuario) {

        Transito transito = transitoRepository.findById(idTransito)
                .orElseThrow(() -> new RuntimeException("Tránsito no encontrado: " + idTransito));

        if (transito.getEstadoTransportista() != null
                && transito.getEstadoTransportista().equals(nuevoEstado)) {

            String estadoActual = nuevoEstado == 1 ? "Activo" : "Inactivo";
            throw new RuntimeException("El transportista ya se encuentra " + estadoActual);
        }

        transito.setEstadoTransportista(nuevoEstado);
        transito.setObservacionTransportista(observaciones);

        Transito guardado = transitoRepository.save(transito);

        bitacoraService.registrarOperacion(
                "CAMBIAR_ESTADO_TRANSPORTISTA",
                usuario,
                transito.getCuenta().getIdCuenta(),
                "El estado del transportista " + transito.getCuiTransportista() + " se actualizó con éxito"
        );

        return guardado;
    }
}