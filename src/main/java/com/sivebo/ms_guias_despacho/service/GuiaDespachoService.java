package com.sivebo.ms_guias_despacho.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.sivebo.ms_guias_despacho.dto.GuiaDespachoRequestDTO;
import com.sivebo.ms_guias_despacho.dto.GuiaDespachoResponseDTO;
import com.sivebo.ms_guias_despacho.model.GuiaDespacho;
import com.sivebo.ms_guias_despacho.repository.GuiaDespachoRepository;
import com.sivebo.ms_guias_despacho.utils.WebClientUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class GuiaDespachoService {
        
        
        private final GuiaDespachoRepository guiaDespachoRepository;
        
        private final WebClientUtil webClientUtil;

        @Qualifier("admisionWebClient")
        private final WebClient admisionWebClient;
        
        private GuiaDespachoResponseDTO mapToDTO(GuiaDespacho guiaDespacho) {
                return new GuiaDespachoResponseDTO(
                        guiaDespacho.getId(),
                        guiaDespacho.getCodigoTracking(),
                        guiaDespacho.getIdAdmision()
                );
        }
        
        public List<GuiaDespachoResponseDTO> getAll() {
                return guiaDespachoRepository.findAll()
                        .stream()
                        .map(this::mapToDTO)
                        .collect(Collectors.toList());
        }
        
        public Optional<GuiaDespachoResponseDTO> getById(Long id) {
                return guiaDespachoRepository.findById(id).map(this::mapToDTO);
        }
        
        public Optional<GuiaDespachoResponseDTO> getByCodigoTracking(String codigoTracking) {
                return guiaDespachoRepository.findByCodigoTracking(codigoTracking).map(this::mapToDTO);
        }

        public Optional<GuiaDespachoResponseDTO> getByIdAdmision(Long idAdmision) {
                return guiaDespachoRepository.findByIdAdmision(idAdmision).map(this::mapToDTO);
        }

        public GuiaDespachoResponseDTO create(GuiaDespachoRequestDTO dto){
                webClientUtil.validateMicroServiceById(dto.getIdAdmision(), "admisiones", admisionWebClient);
                return mapToDTO(guiaDespachoRepository.save(
                        new GuiaDespacho(
                        null,
                        dto.getCodigoTracking(),
                        dto.getIdAdmision()
                        )
                ));
        }
        
        public Boolean delete(Long id){
                guiaDespachoRepository.deleteById(id);
                return !guiaDespachoRepository.existsById(id);
        }
}
