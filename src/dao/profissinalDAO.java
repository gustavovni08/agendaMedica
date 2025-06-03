package com.exemplo.agendamedica.repository;

import com.exemplo.agendamedica.model.ProfissionalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfissionalRepository extends JpaRepository<ProfissionalModel, Long> {
    boolean existsByCrm(String crm);
    boolean existsByUsuario(String usuario);
    boolean existsByEmail(String email);

    Optional<ProfissionalModel> findByCrm(String crm);
    Optional<ProfissionalModel> findByUsuario(String usuario);
    Optional<ProfissionalModel> findByEmail(String email);
}
