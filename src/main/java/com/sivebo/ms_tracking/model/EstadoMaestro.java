package com.sivebo.ms_tracking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "ESTADO_MAESTRO")
public class EstadoMaestro {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;

        @Column(name = "nombre_estado", nullable = false, unique = true)
        @Enumerated(EnumType.STRING)
        TipoEstado tipoEstado;

        @Column(name = "orden", nullable = false)
        Integer orden;
}
