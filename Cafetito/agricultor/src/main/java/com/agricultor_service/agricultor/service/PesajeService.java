package com.agricultor_service.agricultor.service;

import com.agricultor_service.agricultor.model.Agricultor;
import com.agricultor_service.agricultor.model.Pesaje;
import com.agricultor_service.agricultor.repository.AgricultorRepository;
import com.agricultor_service.agricultor.repository.PesajeRepository;
import org.springframework.stereotype.Service;
import com.agricultor_service.agricultor.model.DetalleCatalogo;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PesajeService {

    private final PesajeRepository pesajeRepository;
    private final AgricultorRepository agricultorRepository;

    public PesajeService(PesajeRepository pesajeRepository,
                         AgricultorRepository agricultorRepository) {
        this.pesajeRepository = pesajeRepository;
        this.agricultorRepository = agricultorRepository;
    }

    public List<Pesaje> listarPorAgricultor(Long idAgricultor) {
        return pesajeRepository.findByAgricultor_IdAgricultor(idAgricultor);
    }

    public Pesaje crear(Long idAgricultor, Pesaje pesaje) {

        Agricultor agricultor = agricultorRepository.findById(idAgricultor)
                .orElseThrow(() -> new RuntimeException("Agricultor no encontrado"));

        pesaje.setAgricultor(agricultor);

        // Estado por defecto = Pesaje Iniciado
        DetalleCatalogo estado = new DetalleCatalogo();
        estado.setIdDetalleCatalogo(2L);

        pesaje.setEstado(estado);

        // Medida
        DetalleCatalogo medida = new DetalleCatalogo();

        if (pesaje.getMedida() != null) {

            String valor = pesaje.getMedida().getValor();

            switch (valor.toUpperCase()) {

                case "QUINTAL":
                    medida.setIdDetalleCatalogo(4L);
                    break;

                case "KILOGRAMO":
                    medida.setIdDetalleCatalogo(5L);
                    break;

                case "LIBRA":
                    medida.setIdDetalleCatalogo(6L);
                    break;

                default:
                    throw new RuntimeException("Medida inválida");
            }
        }

        pesaje.setMedida(medida);

        pesaje.setFecha(LocalDateTime.now());
        pesaje.setCantidadParcialidades(0);

        return pesajeRepository.save(pesaje);
    }

    public Pesaje obtenerPorId(Long idPesaje) {
        return pesajeRepository.findById(idPesaje)
                .orElseThrow(() -> new RuntimeException("Pesaje no encontrado"));
    }
}