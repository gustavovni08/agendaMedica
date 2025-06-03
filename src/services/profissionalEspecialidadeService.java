package com.exemplo.agendamedica.services;

import com.exemplo.agendamedica.model.ProfissionalEspecialidadeModel;
import com.exemplo.agendamedica.repository.ProfissionalEspecialidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProfissionalEspecialidadeService {

    private final ProfissionalEspecialidadeRepository repository;

    @Autowired
    public ProfissionalEspecialidadeService(ProfissionalEspecialidadeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ProfissionalEspecialidadeModel vincularEspecialidade(ProfissionalEspecialidadeModel vinculo) {
        // Verifica se o vínculo já existe
        if (repository.existsByProfissionalIdAndEspecialidadeId(
                vinculo.getProfissional().getId(),
                vinculo.getEspecialidade().getId())) {
            throw new IllegalArgumentException("Este profissional já possui esta especialidade vinculada");
        }
        return repository.save(vinculo);
    }

    public Optional<ProfissionalEspecialidadeModel> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public void removerVinculo(Long id) {
        repository.deleteById(id);
    }
}