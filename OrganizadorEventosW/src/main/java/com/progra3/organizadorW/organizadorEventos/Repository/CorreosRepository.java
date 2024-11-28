package com.progra3.organizadorW.organizadorEventos.Repository;

import com.progra3.organizadorW.organizadorEventos.Models.CorreoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorreosRepository extends JpaRepository<CorreoModel,Integer> {
}
