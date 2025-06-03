package com.exemplo.agendamedica.controllers;

import com.exemplo.agendamedica.model.ProfissionalEspecialidadeModel;
import com.exemplo.agendamedica.services.ProfissionalEspecialidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/profissionais-especialidades")
public class ProfissionalEspecialidadeController {

    private final ProfissionalEspecialidadeService vinculoService;

    @Autowired
    public ProfissionalEspecialidadeController(ProfissionalEspecialidadeService vinculoService) {
        this.vinculoService = vinculoService;
    }

    @PostMapping
    public ResponseEntity<?> criarVinculo(@RequestBody ProfissionalEspecialidadeModel vinculo) {
        try {
            ProfissionalEspecialidadeModel novoVinculo = vinculoService.vincularEspecialidade(vinculo);
            return new ResponseEntity<>(novoVinculo, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ProfissionalEspecialidadeModel>> listarTodosVinculos() {
        List<ProfissionalEspecialidadeModel> vinculos = vinculoService.listarTodos();
        return ResponseEntity.ok(vinculos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarVinculoPorId(@PathVariable Long id) {
        Optional<ProfissionalEspecialidadeModel> vinculo = vinculoService.buscarPorId(id);
        return vinculo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vínculo não encontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVinculo(@PathVariable Long id) {
        if (vinculoService.buscarPorId(id).isPresent()) {
            vinculoService.removerVinculo(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}