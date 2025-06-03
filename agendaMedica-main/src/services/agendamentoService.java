package com.exemplo.agendamedica.service;

import com.exemplo.agendamedica.model.AgendamentoModel;
import com.exemplo.agendamedica.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public AgendamentoModel salvar(AgendamentoModel agendamento) {
        return agendamentoRepository.save(agendamento);
    }

    public void deletar(Long id) {
        agendamentoRepository.deleteById(id);
    }

    public Optional<AgendamentoModel> buscarPorId(Long id) {
        return agendamentoRepository.findById(id);
    }

    public List<AgendamentoModel> listarTodos() {
        return agendamentoRepository.findAll();
    }
}