package com.progra3.organizadorW.organizadorEventos.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_tipo_invitado")
public class TipoInvitadoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_tipo_invitado_id_gen")
    @SequenceGenerator(name = "tbl_tipo_invitado_id_gen", sequenceName = "tbl_tipo_invitado_id_tipo_invitado_seq", allocationSize = 1)
    @Column(name = "id_tipo_invitado", nullable = false)
    private Integer id;

    @Column(name = "descripcion", length = 100)
    private String descripcion;

}