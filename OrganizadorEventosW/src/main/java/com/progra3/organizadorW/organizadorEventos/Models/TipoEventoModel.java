package com.progra3.organizadorW.organizadorEventos.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_tipo_evento")
public class TipoEventoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_tipo_evento_id_gen")
    @SequenceGenerator(name = "tbl_tipo_evento_id_gen", sequenceName = "tbl_tipo_evento_id_tipo_evento_seq", allocationSize = 1)
    @Column(name = "id_tipo_evento", nullable = false)
    private Integer id;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

}