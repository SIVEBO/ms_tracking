package com.sivebo.ms_tracking.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sivebo.ms_tracking.model.EstadoMaestro;
import com.sivebo.ms_tracking.model.TipoEstado;
import com.sivebo.ms_tracking.repository.EstadoMaestroRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

        private final EstadoMaestroRepository estadoMaestroRepository;

        @Override
        public void run(String... args){
                if(estadoMaestroRepository.count() > 0){
                        log.info(">>> Estados maestros ya cargados");
                        return;

                }
                log.info(">>> Cargando estados maestros ...");
                        estadoMaestroRepository.saveAll(List.of(
                                new EstadoMaestro(null, TipoEstado.RECIBIDO,    1),
                                new EstadoMaestro(null, TipoEstado.EN_TRANSITO, 2),
                                new EstadoMaestro(null, TipoEstado.ENTREGADO,   3),
                                new EstadoMaestro(null, TipoEstado.DEVUELTO,    4)
                        ));
                log.info(">>> Estados Maestros iniciales cargadas exitosamente.");

        }

}
