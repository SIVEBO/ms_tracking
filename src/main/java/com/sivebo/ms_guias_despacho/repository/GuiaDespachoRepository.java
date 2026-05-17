package com.sivebo.ms_guias_despacho.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sivebo.ms_guias_despacho.model.GuiaDespacho;

public interface GuiaDespachoRepository extends JpaRepository<GuiaDespacho, Object>{
        
        Optional<GuiaDespacho> findByCodigoTracking(String codigoTracking);

        Optional<GuiaDespacho> findByIdAdmision(Long idAdmision);
}
