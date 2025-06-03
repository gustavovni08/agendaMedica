package com.exemplo.agendamedica.services;

import com.exemplo.agendamedica.model.SlotAgendaModel;
import com.exemplo.agendamedica.repository.SlotAgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SlotAgendaService {

    private final SlotAgendaRepository repository;

    @Autowired
    public SlotAgendaService(SlotAgendaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public SlotAgendaModel criarSlot(SlotAgendaModel slot) {
        // Validações adicionais podem ser adicionadas aqui
        return repository.save(slot);
    }

    public List<SlotAgendaModel> listarTodosSlots() {
        return repository.findAll();
    }

    public Optional<SlotAgendaModel> buscarSlotPorId(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public void removerSlot(Long id) {
        repository.deleteById(id);
    }

    public boolean existeSlotParaProfissionalEspecialidade(Long profissionalEspecialidadeId) {
        return repository.existsByProfissionalEspecialidadeId(profissionalEspecialidadeId);
    }
}