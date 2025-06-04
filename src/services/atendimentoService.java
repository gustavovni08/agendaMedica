package com.exemplo.agendamedica.service;

import com.exemplo.agendamedica.model.AtendimentoModel;
import com.exemplo.agendamedica.repository.AtendimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtendimentoService {

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    public AtendimentoModel salvar(AtendimentoModel atendimento) {
        return atendimentoRepository.save(atendimento);
    }

    public void deletar(Long id) {
        atendimentoRepository.deleteById(id);
    }

    public Optional<AtendimentoModel> buscarPorId(Long id) {
        return atendimentoRepository.findById(id);
    }

    public List<AtendimentoModel> listarTodos() {
        return atendimentoRepository.findAll();
    }
}
