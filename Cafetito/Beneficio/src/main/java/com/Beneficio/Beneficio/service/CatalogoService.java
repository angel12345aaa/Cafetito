package com.Beneficio.Beneficio.service;

import com.Beneficio.Beneficio.model.Catalogo;
import com.Beneficio.Beneficio.model.DetalleCatalogo;
import com.Beneficio.Beneficio.repository.CatalogoRepository;
import com.Beneficio.Beneficio.repository.DetalleCatalogoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CatalogoService {

    private final CatalogoRepository catalogoRepository;
    private final DetalleCatalogoRepository detalleRepository;

    public List<Catalogo> listar() {
        return catalogoRepository.findAll();
    }

    public Catalogo crear(Catalogo c) {
        return catalogoRepository.save(c);
    }

    public Optional<Catalogo> obtenerPorNombre(String nombre) {
        return catalogoRepository.findByNombreCatalogo(nombre);
    }

    public List<DetalleCatalogo> detallesPorCatalogo(Long idCatalogo) {
        return detalleRepository.findByCatalogo_IdCatalogoOrderByOrdenAsc(idCatalogo);
    }

    public List<DetalleCatalogo> detallesPorNombre(String nombreCatalogo) {
        return detalleRepository.findByCatalogo_NombreCatalogoOrderByOrdenAsc(nombreCatalogo);
    }

    public Optional<DetalleCatalogo> detallePorCodigo(String nombreCatalogo, String codigo) {
        return detalleRepository.findByCatalogo_NombreCatalogoAndCodigo(nombreCatalogo, codigo);
    }

    public DetalleCatalogo crearDetalle(DetalleCatalogo d) {
        return detalleRepository.save(d);
    }
}