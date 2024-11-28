package com.progra3.organizadorW.organizadorEventos.Models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;

@Getter
@Setter
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
}