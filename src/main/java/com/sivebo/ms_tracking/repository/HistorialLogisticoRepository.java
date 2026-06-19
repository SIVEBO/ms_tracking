package com.sivebo.ms_tracking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sivebo.ms_tracking.model.HistorialLogistico;

public interface HistorialLogisticoRepository extends JpaRepository<HistorialLogistico, Long> {

        List<HistorialLogistico> findByGuia_IdOrderByFechaHoraAsc(Long guiaId);

        Optional<HistorialLogistico> findTopByGuia_IdOrderByFechaHoraDesc(Long guiaId);
}
