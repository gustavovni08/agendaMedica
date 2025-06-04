package com.exemplo.agendamedica.controller;

import com.exemplo.agendamedica.model.AgendamentoModel;
import com.exemplo.agendamedica.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoModel> criarAgendamento(@RequestBody AgendamentoModel agendamento) {
        AgendamentoModel agendamentoSalvo = agendamentoService.salvar(agendamento);
        return ResponseEntity.ok(agendamentoSalvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAgendamento(@PathVariable Long id) {
        agendamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoModel> buscarAgendamentoPorId(@PathVariable Long id) {
        Optional<AgendamentoModel> agendamento = agendamentoService.buscarPorId(id);
        return agendamento.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoModel>> listarTodos() {
        List<AgendamentoModel> agendamentos = agendamentoService.listarTodos();
        return ResponseEntity.ok(agendamentos);
    }
}
