package com.exemplo.agendamedica.repository;

import com.exemplo.agendamedica.model.AtendimentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtendimentoRepository extends JpaRepository<AtendimentoModel, Long> {
}
