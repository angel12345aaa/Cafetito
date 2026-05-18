package com.Beneficio.Beneficio.repository;

import com.Beneficio.Beneficio.model.Bitacora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BitacoraRepository extends JpaRepository<Bitacora, Long> {
    List<Bitacora> findByCuenta(Long cuenta);
    List<Bitacora> findByUsuario(String usuario);
    List<Bitacora> findByFechaSistemaBetween(LocalDateTime desde, LocalDateTime hasta);
}