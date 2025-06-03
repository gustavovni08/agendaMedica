package com.exemplo.agendamedica.controller;

import com.exemplo.agendamedica.model.AtendimentoModel;
import com.exemplo.agendamedica.service.AtendimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/atendimentos")
public class AtendimentoController {

    @Autowired
    private AtendimentoService atendimentoService;

    @PostMapping
    public ResponseEntity<AtendimentoModel> criarAtendimento(@RequestBody AtendimentoModel atendimento) {
        AtendimentoModel atendimentoSalvo = atendimentoService.salvar(atendimento);
        return ResponseEntity.ok(atendimentoSalvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAtendimento(@PathVariable Long id) {
        atendimentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtendimentoModel> buscarAtendimentoPorId(@PathVariable Long id) {
        Optional<AtendimentoModel> atendimento = atendimentoService.buscarPorId(id);
        return atendimento.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AtendimentoModel>> listarTodos() {
        List<AtendimentoModel> atendimentos = atendimentoService.listarTodos();
        return ResponseEntity.ok(atendimentos);
    }
}