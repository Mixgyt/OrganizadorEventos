package com.progra3.organizadorW.organizadorEventos.Repository;

import com.progra3.organizadorW.organizadorEventos.Models.CorreoModel;
import com.progra3.organizadorW.organizadorEventos.Models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioModel,Integer> {
    UsuarioModel findByNombre(String nombre);
    UsuarioModel findByIdCorreo(CorreoModel correo);
}
