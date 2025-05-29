package com.exemplo.agendamedica.repository;

import com.exemplo.agendamedica.model.ProfissionalEspecialidadeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfissionalEspecialidadeRepository extends JpaRepository<ProfissionalEspecialidadeModel, Long> {
    boolean existsByProfissionalIdAndEspecialidadeId(Long profissionalId, Long especialidadeId);
}