package com.exemplo.agendamedica.repository;

import com.exemplo.agendamedica.model.PacienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<PacienteModel, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByUsuario(String usuario);
    boolean existsByEmail(String email);
}