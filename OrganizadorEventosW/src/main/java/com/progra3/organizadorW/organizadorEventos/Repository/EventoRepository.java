package com.progra3.organizadorW.organizadorEventos.Repository;

import com.progra3.organizadorW.organizadorEventos.Models.EventoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<EventoModel,Integer> {
}
