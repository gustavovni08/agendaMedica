package com.exemplo.agendamedica.services;

import com.exemplo.agendamedica.model.PacienteModel;
import com.exemplo.agendamedica.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Transactional
    public PacienteModel salvarPaciente(PacienteModel paciente) {
        validarUnicidade(paciente);
        return pacienteRepository.save(paciente);
    }

    public List<PacienteModel> listarTodos() {
        return pacienteRepository.findAll();
    }

    public Optional<PacienteModel> buscarPorId(Long id) {
        return pacienteRepository.findById(id);
    }

    @Transactional
    public void deletarPaciente(Long id) {
        pacienteRepository.deleteById(id);
    }

    private void validarUnicidade(PacienteModel paciente) {
        if (pacienteRepository.existsByCpf(paciente.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }

        if (pacienteRepository.existsByUsuario(paciente.getUsuario())) {
            throw new IllegalArgumentException("Usuário já cadastrado");
        }

        if (pacienteRepository.existsByEmail(paciente.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }
    }
}