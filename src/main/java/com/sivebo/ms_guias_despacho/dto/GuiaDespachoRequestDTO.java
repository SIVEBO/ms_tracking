package com.sivebo.ms_guias_despacho.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuiaDespachoRequestDTO{

        
        @NotBlank(message="El código de tracking es obligatorio")
        String codigoTracking;

        @NotNull(message="El id de admision es obligatorio")
        Long idAdmision;
}