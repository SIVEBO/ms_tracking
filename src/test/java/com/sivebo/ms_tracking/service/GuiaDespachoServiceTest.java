package com.sivebo.ms_tracking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.sivebo.ms_tracking.dto.request.GuiaDespachoRequestDTO;
import com.sivebo.ms_tracking.dto.response.GuiaDespachoResponseDTO;
import com.sivebo.ms_tracking.exception.MicroserviceValidationException;
import com.sivebo.ms_tracking.model.EstadoMaestro;
import com.sivebo.ms_tracking.model.GuiaDespacho;
import com.sivebo.ms_tracking.model.HistorialLogistico;
import com.sivebo.ms_tracking.model.TipoEstado;
import com.sivebo.ms_tracking.repository.EstadoMaestroRepository;
import com.sivebo.ms_tracking.repository.GuiaDespachoRepository;
import com.sivebo.ms_tracking.repository.HistorialLogisticoRepository;
import com.sivebo.ms_tracking.utils.WebClientUtil;

@ExtendWith(MockitoExtension.class)
class GuiaDespachoServiceTest {

        @Mock GuiaDespachoRepository guiaDespachoRepository;
        @Mock EstadoMaestroRepository estadoMaestroRepository;
        @Mock HistorialLogisticoRepository historialRepository;
        @Mock WebClientUtil webClientUtil;
        @Mock WebClient admisionWebClient;

        @InjectMocks GuiaDespachoService service;

        private static final GuiaDespacho GUIA =
                new GuiaDespacho(1L, "ABC123456789", 10L, LocalDateTime.of(2026, 1, 1, 0, 0));

        private static final EstadoMaestro RECIBIDO =
                new EstadoMaestro(1L, TipoEstado.RECIBIDO, 1);

        @Test
        void getAll_returnsAllMapped() {
                when(guiaDespachoRepository.findAll()).thenReturn(List.of(GUIA));

                List<GuiaDespachoResponseDTO> result = service.getAll();

                assertEquals(1, result.size());
                GuiaDespachoResponseDTO dto = result.get(0);
                assertEquals(GUIA.getId(), dto.getId());
                assertEquals(GUIA.getCodigoTracking(), dto.getCodigoTracking());
                assertEquals(GUIA.getIdAdmision(), dto.getIdAdmision());
                assertEquals(GUIA.getFechaCreacion(), dto.getFechaCreacion());
        }

        @Test
        void getById_found_returnsDTO() {
                when(guiaDespachoRepository.findById(1L)).thenReturn(Optional.of(GUIA));

                Optional<GuiaDespachoResponseDTO> result = service.getById(1L);

                assertTrue(result.isPresent());
                assertEquals(GUIA.getCodigoTracking(), result.get().getCodigoTracking());
        }

        @Test
        void getById_notFound_returnsEmpty() {
                when(guiaDespachoRepository.findById(99L)).thenReturn(Optional.empty());

                assertTrue(service.getById(99L).isEmpty());
        }

        @Test
        void getByCodigoTracking_found_returnsDTO() {
                when(guiaDespachoRepository.findByCodigoTracking("ABC123456789"))
                        .thenReturn(Optional.of(GUIA));

                Optional<GuiaDespachoResponseDTO> result = service.getByCodigoTracking("ABC123456789");

                assertTrue(result.isPresent());
                assertEquals(1L, result.get().getId());
        }

        @Test
        void getByIdAdmision_found_returnsDTO() {
                when(guiaDespachoRepository.findByIdAdmision(10L)).thenReturn(Optional.of(GUIA));

                Optional<GuiaDespachoResponseDTO> result = service.getByIdAdmision(10L);

                assertTrue(result.isPresent());
                assertEquals(10L, result.get().getIdAdmision());
        }

        @Test
        void create_validRequest_savesGuiaAndHistorial() {
                GuiaDespachoRequestDTO dto = new GuiaDespachoRequestDTO("ABC123456789", 10L);
                GuiaDespacho saved = new GuiaDespacho(1L, "ABC123456789", 10L, LocalDateTime.now());

                doNothing().when(webClientUtil)
                        .validateMicroServiceById(anyLong(), anyString(), any(WebClient.class));
                when(guiaDespachoRepository.save(any())).thenReturn(saved);
                when(estadoMaestroRepository.findByTipoEstado(TipoEstado.RECIBIDO))
                        .thenReturn(Optional.of(RECIBIDO));
                when(historialRepository.save(any())).thenReturn(
                        new HistorialLogistico(1L, saved, RECIBIDO, null, null, LocalDateTime.now(), null));

                GuiaDespachoResponseDTO result = service.create(dto);

                assertNotNull(result);
                assertEquals("ABC123456789", result.getCodigoTracking());
                assertNotNull(result.getFechaCreacion());
                verify(historialRepository).save(any(HistorialLogistico.class));
        }

        @Test
        void create_admisionNotFound_throwsMicroserviceValidationException() {
                GuiaDespachoRequestDTO dto = new GuiaDespachoRequestDTO("ABC123456789", 10L);
                doThrow(new MicroserviceValidationException("admision no existe"))
                        .when(webClientUtil).validateMicroServiceById(eq(10L), anyString(), any(WebClient.class));

                assertThrows(MicroserviceValidationException.class, () -> service.create(dto));
        }

        @Test
        void create_recibidoMissing_throwsMicroserviceValidationException() {
                GuiaDespachoRequestDTO dto = new GuiaDespachoRequestDTO("ABC123456789", 10L);
                doNothing().when(webClientUtil)
                        .validateMicroServiceById(anyLong(), anyString(), any(WebClient.class));
                when(guiaDespachoRepository.save(any())).thenReturn(GUIA);
                when(estadoMaestroRepository.findByTipoEstado(TipoEstado.RECIBIDO))
                        .thenReturn(Optional.empty());

                assertThrows(MicroserviceValidationException.class, () -> service.create(dto));
        }

        @Test
        void delete_deletesAndReturnsTrue() {
                doNothing().when(guiaDespachoRepository).deleteById(1L);
                when(guiaDespachoRepository.existsById(1L)).thenReturn(false);

                assertTrue(service.delete(1L));
        }
}
