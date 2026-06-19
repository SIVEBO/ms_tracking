package com.sivebo.ms_tracking.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuiaDespachoResponseDTO {

        Long id;
        String codigoTracking;
        Long idAdmision;
        LocalDateTime fechaCreacion;
}
