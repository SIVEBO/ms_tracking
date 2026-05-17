package com.sivebo.ms_guias_despacho.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sivebo.ms_guias_despacho.dto.GuiaDespachoRequestDTO;
import com.sivebo.ms_guias_despacho.dto.GuiaDespachoResponseDTO;
import com.sivebo.ms_guias_despacho.service.GuiaDespachoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("api/v1/guias_admision")
@RequiredArgsConstructor
public class GuiaDespachoController {
        
        private final GuiaDespachoService guiaDespachoService;

        @GetMapping
        public List<GuiaDespachoResponseDTO> getAll() {
                return guiaDespachoService.getAll();
        }

        @GetMapping("{id}")
        public ResponseEntity<GuiaDespachoResponseDTO> getById(@PathVariable Long id) {
                return guiaDespachoService.getById(id)
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
        }

        
        @GetMapping("/search")
        public ResponseEntity<?> getByAtribute(
                @RequestParam(required = false) String codigoTracking,
                @RequestParam(required = false) String idAdmision){

                List<String> params = new ArrayList<>(List.of(codigoTracking, idAdmision));

                int num_null = 0;
                for(String value: params){
                        if(value == null) num_null++;
                }
                if(num_null == params.size()) {
                        return ResponseEntity.badRequest().body("Debe proporcionar un atributo de búsqueda valido");
                }else if(num_null > 1) {
                        return ResponseEntity.badRequest().body("Solo se permite un atributo de búsqueda a la vez");
                }else if(codigoTracking != null) {
                        log.info(">>> Buscando guia de despacho por codigo de tracking: {}", codigoTracking);
                        return guiaDespachoService.getByCodigoTracking(codigoTracking)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
                }else if(idAdmision != null){
                        log.info(">>> Buscando guia de despacho por id de admision: {}", idAdmision);
                        return guiaDespachoService.getByIdAdmision(Long.valueOf(idAdmision))
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
                }else{
                        return ResponseEntity.internalServerError().body("Error en el URL query");
                }
        }

        
        @PostMapping 
        public ResponseEntity<GuiaDespachoResponseDTO> create(@Valid @RequestBody GuiaDespachoRequestDTO dto) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(guiaDespachoService.create(dto));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> delete(@PathVariable Long id) {
                if (guiaDespachoService.delete(id)) {
                        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("GuiaDespacho eliminada");
                } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("GuiaDespacho no encontrada o no se pudo eliminar");
                }
        }
}
