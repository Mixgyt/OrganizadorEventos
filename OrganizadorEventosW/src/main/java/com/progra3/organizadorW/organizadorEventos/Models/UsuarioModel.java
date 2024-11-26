package com.progra3.organizadorW.organizadorEventos.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tbl_usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioModel {
    @Id
    @GeneratedValue
    private Integer id;
    private String nombre;
    private String pass;
    private String id_correo;
}