package com.exemplo.agendamedica.controllers;

import com.exemplo.agendamedica.model.PacienteModel;
import com.exemplo.agendamedica.services.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<?> criarPaciente(@Valid @RequestBody PacienteModel paciente) {
        try {
            PacienteModel novoPaciente = pacienteService.salvarPaciente(paciente);
            return new ResponseEntity<>(novoPaciente, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<PacienteModel>> listarTodosPacientes() {
        List<PacienteModel> pacientes = pacienteService.listarTodos();
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPacientePorId(@PathVariable Long id) {
        Optional<PacienteModel> paciente = pacienteService.buscarPorId(id);
        return paciente.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarPaciente(
            @PathVariable Long id,
            @Valid @RequestBody PacienteModel pacienteAtualizado) {

        if (!pacienteService.buscarPorId(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado");
        }

        pacienteAtualizado.setId(id);
        try {
            PacienteModel paciente = pacienteService.salvarPaciente(pacienteAtualizado);
            return ResponseEntity.ok(paciente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPaciente(@PathVariable Long id) {
        if (pacienteService.buscarPorId(id).isPresent()) {
            pacienteService.deletarPaciente(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}