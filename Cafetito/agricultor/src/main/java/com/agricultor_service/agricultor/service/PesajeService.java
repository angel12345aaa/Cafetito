package com.agricultor_service.agricultor.service;

import com.agricultor_service.agricultor.dto.CuentaBeneficioRequest;
import com.agricultor_service.agricultor.model.Agricultor;
import com.agricultor_service.agricultor.model.DetalleCatalogo;
import com.agricultor_service.agricultor.model.Pesaje;
import com.agricultor_service.agricultor.model.Transporte;
import com.agricultor_service.agricultor.model.Transportista;
import com.agricultor_service.agricultor.repository.AgricultorRepository;
import com.agricultor_service.agricultor.repository.PesajeRepository;
import com.agricultor_service.agricultor.repository.TransporteRepository;
import com.agricultor_service.agricultor.repository.TransportistaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PesajeService {

    private static final Long ESTADO_PESAJE_INICIADO = 2L;
    private static final Long ESTADO_PESAJE_FINALIZADO = 3L;

    private final PesajeRepository pesajeRepository;
    private final AgricultorRepository agricultorRepository;
    private final TransporteRepository transporteRepository;
    private final TransportistaRepository transportistaRepository;
    private final BeneficioClientService beneficioClientService;

    public PesajeService(PesajeRepository pesajeRepository,
                         AgricultorRepository agricultorRepository,
                         TransporteRepository transporteRepository,
                         TransportistaRepository transportistaRepository,
                         BeneficioClientService beneficioClientService) {

        this.pesajeRepository = pesajeRepository;
        this.agricultorRepository = agricultorRepository;
        this.transporteRepository = transporteRepository;
        this.transportistaRepository = transportistaRepository;
        this.beneficioClientService = beneficioClientService;
    }

    public List<Pesaje> listarPorAgricultor(Long idAgricultor) {
        if (idAgricultor == null) {
            throw new RuntimeException("No se encontró el agricultor asociado al usuario autenticado");
        }

        return pesajeRepository.findByAgricultor_IdAgricultor(idAgricultor);
    }

    public Pesaje obtenerPorId(Long idPesaje) {
        return pesajeRepository.findById(idPesaje)
                .orElseThrow(() -> new RuntimeException("Pesaje no encontrado"));
    }

    public Pesaje crear(Long idAgricultor, Pesaje pesaje) {
        if (idAgricultor == null) {
            throw new RuntimeException("No se encontró el agricultor asociado al usuario autenticado");
        }

        if (pesaje.getPesoTotalActual() == null || pesaje.getPesoTotalActual() <= 0) {
            throw new RuntimeException("El peso total actual debe ser mayor a 0");
        }

        Agricultor agricultor = agricultorRepository.findById(idAgricultor)
                .orElseThrow(() -> new RuntimeException("Agricultor no encontrado"));

        DetalleCatalogo estado = new DetalleCatalogo();
        estado.setIdDetalleCatalogo(ESTADO_PESAJE_INICIADO);

        pesaje.setIdPesaje(null);
        pesaje.setAgricultor(agricultor);
        pesaje.setEstado(estado);
        pesaje.setMedida(obtenerMedida(pesaje));
        pesaje.setFecha(LocalDateTime.now());
        pesaje.setCantidadParcialidades(0);

        return pesajeRepository.save(pesaje);
    }

    @Transactional
    public Pesaje finalizar(Long idPesaje) {
        Pesaje pesaje = pesajeRepository.findById(idPesaje)
                .orElseThrow(() -> new RuntimeException("Pesaje no encontrado"));

        if (pesaje.getCantidadParcialidades() == null || pesaje.getCantidadParcialidades() <= 0) {
            throw new RuntimeException("No se puede finalizar un pesaje sin parcialidades");
        }

        DetalleCatalogo estado = new DetalleCatalogo();
        estado.setIdDetalleCatalogo(ESTADO_PESAJE_FINALIZADO);
        pesaje.setEstado(estado);

        List<Transporte> transportes =
                transporteRepository.findByAgricultor_IdAgricultor(
                        pesaje.getAgricultor().getIdAgricultor()
                );

        for (Transporte transporte : transportes) {
            if (idPesaje.equals(transporte.getPesajeAsociado())) {
                transporte.setDisponible(true);
                transporte.setPesajeAsociado(null);
                transporteRepository.save(transporte);
            }
        }

        List<Transportista> transportistas =
                transportistaRepository.findByAgricultor_IdAgricultor(
                        pesaje.getAgricultor().getIdAgricultor()
                );

        for (Transportista transportista : transportistas) {
            if (idPesaje.equals(transportista.getPesajeAsociado())) {
                transportista.setDisponible(true);
                transportista.setPesajeAsociado(null);
                transportistaRepository.save(transportista);
            }
        }

        Pesaje pesajeFinalizado = pesajeRepository.save(pesaje);

        CuentaBeneficioRequest request = new CuentaBeneficioRequest();
        request.setNitAgricultor(pesajeFinalizado.getAgricultor().getIdAgricultor());
        request.setPesoObjetivo(pesajeFinalizado.getPesoTotalActual());
        request.setCantidadParcialidades(pesajeFinalizado.getCantidadParcialidades());
        request.setEstado("PESAJE_FINALIZADO");

        beneficioClientService.crearCuentaEnBeneficio(request);

        return pesajeFinalizado;
    }

    private DetalleCatalogo obtenerMedida(Pesaje pesaje) {
        if (pesaje.getMedida() == null) {
            throw new RuntimeException("La medida de peso es obligatoria");
        }

        Long idMedida = pesaje.getMedida().getIdDetalleCatalogo();

        if (idMedida == null) {
            throw new RuntimeException("Debe seleccionar una medida");
        }

        if (!idMedida.equals(4L)
                && !idMedida.equals(5L)
                && !idMedida.equals(6L)) {
            throw new RuntimeException("Medida inválida");
        }

        DetalleCatalogo medida = new DetalleCatalogo();
        medida.setIdDetalleCatalogo(idMedida);

        return medida;
    }
}