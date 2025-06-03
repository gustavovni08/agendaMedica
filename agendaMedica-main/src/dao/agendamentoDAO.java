package com.exemplo.agendamedica.repository;

import com.exemplo.agendamedica.model.AgendamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<AgendamentoModel, Long> {

}
