package com.exemplo.agendamedica.controllers;

import com.exemplo.agendamedica.model.SlotAgendaModel;
import com.exemplo.agendamedica.services.SlotAgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/slots-agenda")
public class SlotAgendaController {

    private final SlotAgendaService slotService;

    @Autowired
    public SlotAgendaController(SlotAgendaService slotService) {
        this.slotService = slotService;
    }

    @PostMapping
    public ResponseEntity<SlotAgendaModel> criarSlot(@RequestBody SlotAgendaModel slot) {
        SlotAgendaModel novoSlot = slotService.criarSlot(slot);
        return new ResponseEntity<>(novoSlot, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SlotAgendaModel>> listarTodosSlots() {
        List<SlotAgendaModel> slots = slotService.listarTodosSlots();
        return ResponseEntity.ok(slots);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarSlotPorId(@PathVariable Long id) {
        Optional<SlotAgendaModel> slot = slotService.buscarSlotPorId(id);
        return slot.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Slot não encontrado"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarSlot(
            @PathVariable Long id,
            @RequestBody SlotAgendaModel slotAtualizado) {

        if (!slotService.buscarSlotPorId(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Slot não encontrado");
        }

        slotAtualizado.setId(id);
        SlotAgendaModel slot = slotService.criarSlot(slotAtualizado);
        return ResponseEntity.ok(slot);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSlot(@PathVariable Long id) {
        if (slotService.buscarSlotPorId(id).isPresent()) {
            slotService.removerSlot(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/por-profissional/{profissionalEspecialidadeId}")
    public ResponseEntity<List<SlotAgendaModel>> buscarSlotsPorProfissional(
            @PathVariable Long profissionalEspecialidadeId) {

        List<SlotAgendaModel> slots = slotService.buscarPorProfissionalEspecialidade(profissionalEspecialidadeId);
        return ResponseEntity.ok(slots);
    }
}