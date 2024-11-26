package com.progra3.organizadorW.organizadorEventos.Repository;

import com.progra3.organizadorW.organizadorEventos.Models.UsuarioModel;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<UsuarioModel,Integer> {
    UsuarioModel findByNombre(String nombre);
}
