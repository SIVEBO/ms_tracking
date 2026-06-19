package com.sivebo.ms_tracking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import com.sivebo.ms_tracking.model.EstadoMaestro;

@Repository
public interface EstadoMaestroRepository extends JpaRepository<EstadoMaestro, Object>{
        
        @NativeQuery("SELECT tipo_estado FROM estado_maestro WHERE tipo_estado = ?1")
        public Long GetIdByTipoEstado(String tipoEstado);
}
