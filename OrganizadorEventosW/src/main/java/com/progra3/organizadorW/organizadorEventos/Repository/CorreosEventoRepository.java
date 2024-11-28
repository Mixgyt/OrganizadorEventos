package com.progra3.organizadorW.organizadorEventos.Repository;

import com.progra3.organizadorW.organizadorEventos.Models.CorreoModel;
import com.progra3.organizadorW.organizadorEventos.Models.CorreosEventoModel;
import com.progra3.organizadorW.organizadorEventos.Models.EventoModel;
import com.progra3.organizadorW.organizadorEventos.Models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface CorreosEventoRepository extends JpaRepository<CorreosEventoModel,Integer> {
    ArrayList<CorreosEventoModel> findByIdEvento(EventoModel evento);
    ArrayList<CorreosEventoModel> findByIdCorreo(CorreoModel idCorreo);
}
