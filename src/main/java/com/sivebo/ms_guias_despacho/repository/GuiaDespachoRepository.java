package com.sivebo.ms_guias_despacho.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

import com.sivebo.ms_guias_despacho.model.GuiaDespacho;

public interface GuiaDespachoRepository extends JpaRepository<GuiaDespacho, Object>{
        
        Optional<GuiaDespacho> findByCodigoTracking(String codigoTracking);

        Optional<GuiaDespacho> findByIdAdmision(Long idAdmision);

        @NativeQuery(
                "SELECT * FROM guia_despacho " +
                "JOIN estado_maestro ON guia_despacho.id_estado_maestro = estado_maestro.id " +
                "WHERE estado_maestro.tipo_estado = ?1")
        List<GuiaDespacho> findByEstadoMaestro(String estadoMaestro);
}
