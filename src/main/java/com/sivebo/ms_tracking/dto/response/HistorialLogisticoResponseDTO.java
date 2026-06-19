package com.sivebo.ms_tracking.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialLogisticoResponseDTO {

        Long id;
        Long idGuia;
        String nombreEstado;
        Long idSucursalActual;
        Long idUsuario;
        LocalDateTime fechaHora;
        String comentario;
}
