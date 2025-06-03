package com.exemplo.agendamedica.controllers;

import com.exemplo.agendamedica.model.ProfissionalModel;
import com.exemplo.agendamedica.services.ProfissionalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            ProfissionalModel novoProfissional = profissionalService.salvar(profissional);
            return new ResponseEntity<>(novoProfissional, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ProfissionalModel>> listarTodosProfissionais() {
        List<ProfissionalModel> profissionais = profissionalService.listarTodos();
        return ResponseEntity.ok(profissionais);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarProfissionalPorId(@PathVariable Long id) {
        Optional<ProfissionalModel> profissional = profissionalService.buscarPorId(id);
        return profissional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profissional não encontrado"));
    }

    @GetMapping("/por-usuario/{usuario}")
    public ResponseEntity<?> buscarProfissionalPorUsuario(@PathVariable String usuario) {
        Optional<ProfissionalModel> profissional = profissionalService.buscarPorUsuario(usuario);
        return profissional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profissional não encontrado"));
    }

    @GetMapping("/por-crm/{crm}")
    public ResponseEntity<?> buscarProfissionalPorCrm(@PathVariable String crm) {
        Optional<ProfissionalModel> profissional = profissionalService.buscarPorCrm(crm);
        return profissional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profissional não encontrado"));
    }

    @GetMapping("/por-email/{email}")
    public ResponseEntity<?> buscarProfissionalPorEmail(@PathVariable String email) {
        Optional<ProfissionalModel> profissional = profissionalService.buscarPorEmail(email);
        return profissional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profissional não encontrado"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarProfissional(
            @PathVariable Long id,
            @Valid @RequestBody ProfissionalModel profissionalAtualizado) {

        if (!profissionalService.buscarPorId(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profissional não encontrado");
        }

        profissionalAtualizado.setId(id);
        try {
            ProfissionalModel profissional = profissionalService.salvar(profissionalAtualizado);
            return ResponseEntity.ok(profissional);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProfissional(@PathVariable Long id) {
        if (profissionalService.buscarPorId(id).isPresent()) {
            profissionalService.excluir(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}