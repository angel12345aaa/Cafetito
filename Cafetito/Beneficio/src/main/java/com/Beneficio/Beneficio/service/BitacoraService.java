package com.Beneficio.Beneficio.service;

import com.Beneficio.Beneficio.model.Bitacora;
import com.Beneficio.Beneficio.repository.BitacoraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BitacoraService {

    private final BitacoraRepository bitacoraRepository;

    public Bitacora registrar(Bitacora b) {
        if (b.getFechaSistema() == null) {
            b.setFechaSistema(LocalDateTime.now());
        }
        return bitacoraRepository.save(b);
    }

    public Bitacora registrarOperacion(String operacion, String usuario, Long cuenta, String observacion) {
        Bitacora b = Bitacora.builder()
                .operacion(operacion)
                .usuario(usuario)
                .cuenta(cuenta)
                .observacion(observacion)
                .fechaSistema(LocalDateTime.now())
                .build();
        return bitacoraRepository.save(b);
    }

    public List<Bitacora> listar() {
        return bitacoraRepository.findAll();
    }

    public List<Bitacora> listarPorCuenta(Long cuenta) {
        return bitacoraRepository.findByCuenta(cuenta);
    }

    public List<Bitacora> listarPorUsuario(String usuario) {
        return bitacoraRepository.findByUsuario(usuario);
    }

    public List<Bitacora> listarPorRangoFechas(LocalDateTime desde, LocalDateTime hasta) {
        return bitacoraRepository.findByFechaSistemaBetween(desde, hasta);
    }
}