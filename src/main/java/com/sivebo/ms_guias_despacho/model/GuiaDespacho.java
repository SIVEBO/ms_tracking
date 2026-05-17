package com.sivebo.ms_guias_despacho.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "guia_despacho")
public class GuiaDespacho {
        
        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        Long id;

        @Column(name = "codigo_tracking", nullable = false, unique = true, length = 13)
        String codigoTracking;

        @Column(name = "id_admision", nullable = false)
        Long idAdmision;

}
