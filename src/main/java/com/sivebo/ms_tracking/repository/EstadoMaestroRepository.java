package com.sivebo.ms_tracking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sivebo.ms_tracking.model.EstadoMaestro;
import com.sivebo.ms_tracking.model.TipoEstado;

public interface EstadoMaestroRepository extends JpaRepository<EstadoMaestro, Long> {

        Optional<EstadoMaestro> findByTipoEstado(TipoEstado tipoEstado);
}
