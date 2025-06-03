package com.exemplo.agendamedica.model;

import lombok.*;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "slots_agenda")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlotAgendaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_especialidade_id", nullable = false)
    private ProfissionalEspecialidadeModel profissionalEspecialidade;

    @ElementCollection
    @CollectionTable(
            name = "slot_dias_semana",
            joinColumns = @JoinColumn(name = "slot_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana")
    private Set<DayOfWeek> diasSemana;

    @ElementCollection
    @CollectionTable(
            name = "slot_horarios",
            joinColumns = @JoinColumn(name = "slot_id")
    )
    @Column(name = "horario")
    private Set<LocalTime> horarios;
}