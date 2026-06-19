package com.sivebo.ms_tracking.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialLogisticoRequestDTO {

        @NotNull(message = "El id de la guía es obligatorio")
        Long idGuia;

        @NotNull(message = "El id del estado es obligatorio")
        Long idEstado;

        Long idSucursalActual;

        Long idUsuario;

        @NotNull(message = "La fecha y hora son obligatorias")
        LocalDateTime fechaHora;

        String comentario;
}
