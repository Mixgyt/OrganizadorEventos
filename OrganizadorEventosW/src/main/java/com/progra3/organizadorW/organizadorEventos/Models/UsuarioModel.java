package com.progra3.organizadorW.organizadorEventos.Models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;

@Entity
@Table(name="tbl_usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_usuario", nullable = false)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "pass")
    private String pass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_correo")
    private CorreoModel idCorreo;

    public CorreoModel getIdCorreo() {
        return idCorreo;
    }

    public void setIdCorreo(CorreoModel idCorreo) {
        this.idCorreo = idCorreo;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}