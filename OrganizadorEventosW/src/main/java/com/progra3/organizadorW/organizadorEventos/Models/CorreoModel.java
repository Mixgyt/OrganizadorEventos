package com.progra3.organizadorW.organizadorEventos.Models;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Table(name = "tbl_correos")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CorreoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_correo", nullable = false)
    private Integer id_correo;

    @Column(name = "correo")
    private String correo;

}