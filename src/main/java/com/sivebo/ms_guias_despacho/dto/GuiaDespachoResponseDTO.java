package com.sivebo.ms_guias_despacho.dto;

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
}
