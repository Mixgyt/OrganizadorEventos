package com.progra3.organizadorW.organizadorEventos.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_correos")
public class CorreoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_correo;

    private String correo;

    public Integer getId_correo() {
        return id_correo;
    }

    public void setId_correo(Integer id_correo) {
        this.id_correo = id_correo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

}