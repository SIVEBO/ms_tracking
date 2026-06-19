package com.sivebo.ms_tracking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sivebo.ms_tracking.dto.request.HistorialLogisticoRequestDTO;
import com.sivebo.ms_tracking.dto.response.HistorialLogisticoResponseDTO;
import com.sivebo.ms_tracking.exception.MicroserviceValidationException;
import com.sivebo.ms_tracking.model.EstadoMaestro;
import com.sivebo.ms_tracking.model.GuiaDespacho;
import com.sivebo.ms_tracking.model.HistorialLogistico;
import com.sivebo.ms_tracking.repository.EstadoMaestroRepository;
import com.sivebo.ms_tracking.repository.GuiaDespachoRepository;
import com.sivebo.ms_tracking.repository.HistorialLogisticoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistorialLogisticoService {

        private final HistorialLogisticoRepository historialRepository;
        private final GuiaDespachoRepository guiaDespachoRepository;
        private final EstadoMaestroRepository estadoMaestroRepository;

        private HistorialLogisticoResponseDTO mapToDTO(HistorialLogistico h) {
                return new HistorialLogisticoResponseDTO(
                        h.getId(),
                        h.getGuia().getId(),
                        h.getEstado().getTipoEstado().name(),
                        h.getIdSucursalActual(),
                        h.getIdUsuario(),
                        h.getFechaHora(),
                        h.getComentario()
                );
        }

        @Transactional(readOnly = true)
        public List<HistorialLogisticoResponseDTO> getAll() {
                return historialRepository.findAll()
                        .stream().map(this::mapToDTO).toList();
        }

        @Transactional(readOnly = true)
        public List<HistorialLogisticoResponseDTO> getByGuiaId(Long guiaId) {
                return historialRepository.findByGuia_IdOrderByFechaHoraAsc(guiaId)
                        .stream().map(this::mapToDTO).toList();
        }

        @Transactional(readOnly = true)
        public Optional<HistorialLogisticoResponseDTO> getEstadoActual(Long guiaId) {
                return historialRepository.findTopByGuia_IdOrderByFechaHoraDesc(guiaId)
                        .map(this::mapToDTO);
        }

        @Transactional
        public HistorialLogisticoResponseDTO create(HistorialLogisticoRequestDTO dto) {
                GuiaDespacho guia = guiaDespachoRepository.findById(dto.getIdGuia())
                        .orElseThrow(() -> new MicroserviceValidationException(
                                "Guía con id " + dto.getIdGuia() + " no encontrada"));
                EstadoMaestro estado = estadoMaestroRepository.findById(dto.getIdEstado())
                        .orElseThrow(() -> new MicroserviceValidationException(
                                "Estado con id " + dto.getIdEstado() + " no encontrado"));
                return mapToDTO(historialRepository.save(
                        new HistorialLogistico(
                                null, guia, estado,
                                dto.getIdSucursalActual(), dto.getIdUsuario(),
                                dto.getFechaHora(), dto.getComentario()
                        )
                ));
        }

        @Transactional
        public Boolean delete(Long id) {
                historialRepository.deleteById(id);
                return !historialRepository.existsById(id);
        }
}
