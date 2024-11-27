package com.progra3.organizadorW.organizadorEventos.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "tbl_eventos")
public class EventoModel {
    @Id
    @ColumnDefault("nextval('tbl_eventos_id_evento_seq')")
    @Column(name = "id_evento", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private UsuarioModel idUsuario; //Este es el dueño del evento

    @Column(name = "nombre", length = 50)
    private String nombre;

    @Column(name = "fecha_inicio")
    private Instant fechaInicio;

    @Column(name = "fecha_final")
    private Instant fechaFinal;

    @Column(name = "ubicacion", length = 100)
    private String ubicacion;

    @Column(name = "descripcion", length = 400)
    private String descripcion;

    @Column(name = "detalles", length = 100)
    private String detalles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_evento")
    private com.progra3.organizadorW.organizadorEventos.Models.TipoEventoModel idTipoEvento;

}