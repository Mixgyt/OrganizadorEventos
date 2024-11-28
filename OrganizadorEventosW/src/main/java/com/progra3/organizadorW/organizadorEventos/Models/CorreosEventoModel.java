package com.progra3.organizadorW.organizadorEventos.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_correos_evento")
public class CorreosEventoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_correos_evento_id_gen")
    @SequenceGenerator(name = "tbl_correos_evento_id_gen", sequenceName = "tbl_correos_evento_id_correos_evento_seq", allocationSize = 1)
    @Column(name = "id_correos_evento", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_evento")
    private EventoModel idEvento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_correo")
    private CorreoModel idCorreo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_invitado")
    private com.progra3.organizadorW.organizadorEventos.Models.TipoInvitadoModel idTipoInvitado;

    @Column(name = "estado")
    private Integer estado;

}