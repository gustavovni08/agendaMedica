package com.exemplo.agendamedica.repository;

import com.exemplo.agendamedica.model.SlotAgendaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlotAgendaRepository extends JpaRepository<SlotAgendaModel, Long> {
    boolean existsByProfissionalEspecialidadeId(Long profissionalEspecialidadeId);
}