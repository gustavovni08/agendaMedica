package com.exemplo.agendamedica.services;

import com.exemplo.agendamedica.model.ProfissionalModel;
import com.exemplo.agendamedica.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProfissionalService {

    private final ProfissionalRepository profissionalRepository;

    @Autowired
    public ProfissionalService(ProfissionalRepository profissionalRepository) {
        this.profissionalRepository = profissionalRepository;
    }

    @Transactional
    public ProfissionalModel salvarProfissional(ProfissionalModel profissional) {
        validarUnicidade(profissional);
        return profissionalRepository.save(profissional);
    }

    @Transactional
    public ProfissionalModel atualizarProfissional(Long id, ProfissionalModel profissionalAtualizado) {
        ProfissionalModel existente = profissionalRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Profissional não encontrado"));

        profissionalAtualizado.setId(id);
        validarUnicidade(profissionalAtualizado);
        return profissionalRepository.save(profissionalAtualizado);
    }

    public List<ProfissionalModel> listarTodos() {
        return profissionalRepository.findAll();
    }

    public Optional<ProfissionalModel> buscarPorId(Long id) {
        return profissionalRepository.findById(id);
    }

    @Transactional
    public void deletarProfissional(Long id) {
        profissionalRepository.deleteById(id);
    }

    private void validarUnicidade(ProfissionalModel profissional) {
        profissionalRepository.findByCrm(profissional.getCrm())
            .filter(p -> !p.getId().equals(profissional.getId()))
            .ifPresent(p -> { throw new IllegalArgumentException("CRM já cadastrado"); });

        profissionalRepository.findByUsuario(profissional.getUsuario())
            .filter(p -> !p.getId().equals(profissional.getId()))
            .ifPresent(p -> { throw new IllegalArgumentException("Usuário já cadastrado"); });

        profissionalRepository.findByEmail(profissional.getEmail())
            .filter(p -> !p.getId().equals(profissional.getId()))
            .ifPresent(p -> { throw new IllegalArgumentException("E-mail já cadastrado"); });
    }
}
