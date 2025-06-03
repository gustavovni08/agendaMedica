package com.exemplo.agendamedica.dao;

import com.exemplo.agendamedica.model.ProfissionalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfissionalDao extends JpaRepository<ProfissionalModel, Long> {

    Optional<ProfissionalModel> findByUsuario(String usuario);

    Optional<ProfissionalModel> findByCrm(String crm);

    Optional<ProfissionalModel> findByEmail(String email);

    boolean existsByUsuario(String usuario);

    boolean existsByCrm(String crm);

    boolean existsByEmail(String email);
}