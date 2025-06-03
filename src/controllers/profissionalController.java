package com.exemplo.agendamedica.controllers;

import com.exemplo.agendamedica.model.ProfissionalModel;
import com.exemplo.agendamedica.services.ProfissionalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/profissionais")
public class ProfissionalController {

    private final ProfissionalService profissionalService;

    @Autowired
    public ProfissionalController(ProfissionalService profissionalService) {
        this.profissionalService = profissionalService;
    }

    @PostMapping
    public ResponseEntity<?> criarProfissional(@Valid @RequestBody ProfissionalModel profissional) {
        try {
            ProfissionalModel novo = profissionalService.salvarProfissional(profissional);
            return new ResponseEntity<>(novo, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ProfissionalModel>> listarTodos() {
        return ResponseEntity.ok(profissionalService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<ProfissionalModel> profissional = profissionalService.buscarPorId(id);
        return profissional.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Profissional não encontrado"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarProfissional(
            @PathVariable Long id,
            @Valid @RequestBody ProfissionalModel profissionalAtualizado) {
        try {
            ProfissionalModel atualizado = profissionalService.atualizarProfissional(id, profissionalAtualizado);
            return ResponseEntity.ok(atualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProfissional(@PathVariable Long id) {
        if (profissionalService.buscarPorId(id).isPresent()) {
            profissionalService.deletarProfissional(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
