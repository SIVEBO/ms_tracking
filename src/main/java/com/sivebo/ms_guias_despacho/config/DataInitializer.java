package com.sivebo.ms_guias_despacho.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sivebo.ms_guias_despacho.model.EstadoMaestro;
import com.sivebo.ms_guias_despacho.model.TipoEstado;
import com.sivebo.ms_guias_despacho.repository.EstadoMaestroRepository;
import com.sivebo.ms_guias_despacho.repository.GuiaDespachoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner{

        private final GuiaDespachoRepository guiaDespachoRepository;
        private final EstadoMaestroRepository estadoMaestroRepository;

        @Override
        public void run(String... args){
                if(estadoMaestroRepository.count() > 0){
                        log.info(">>> Estados maestros ya cargados");
                        return;

                }
                log.info(">>> Cargando estados maestros ...");
                estadoMaestroRepository.save(new EstadoMaestro(null, TipoEstado.RECIBIDO));
                estadoMaestroRepository.save(new EstadoMaestro(null, TipoEstado.TRANSITO));
                estadoMaestroRepository.save(new EstadoMaestro(null, TipoEstado.ENTREGADO));
                estadoMaestroRepository.save(new EstadoMaestro(null, TipoEstado.DEVUELTO));
                
                log.info(">>> Estados Maestros iniciales cargadas exitosamente.");

        }
        
}
