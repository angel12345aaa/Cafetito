package com.Beneficio.Beneficio.service;

import com.Beneficio.Beneficio.model.Cuenta;
import com.Beneficio.Beneficio.model.HistorialCuenta;
import com.Beneficio.Beneficio.repository.HistorialCuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistorialCuentaService {

    private final HistorialCuentaRepository historialRepository;

    public HistorialCuenta registrarCambio(Cuenta cuenta, String nuevoEstado,
                                           Double diferenciaTotal, Double tolerancia) {
        HistorialCuenta h = HistorialCuenta.builder()
                .cuenta(cuenta)
                .idAgricultor(cuenta.getIdAgricultor())
                .estado(nuevoEstado)
                .diferenciaTotal(diferenciaTotal)
                .tolerancia(tolerancia)
                .fechaRegistro(LocalDateTime.now())
                .build();
        return historialRepository.save(h);
    }

    public List<HistorialCuenta> listarPorCuenta(Long idCuenta) {
        return historialRepository.findByCuenta_IdCuenta(idCuenta);
    }

    public List<HistorialCuenta> listarPorAgricultor(Long idAgricultor) {
        return historialRepository.findByIdAgricultor(idAgricultor);
    }
}