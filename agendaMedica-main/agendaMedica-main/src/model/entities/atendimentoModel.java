package com.exemplo.agendamedica.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "atendimentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AtendimentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "agendamento_id", nullable = false)
    private AgendamentoModel agendamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private PacienteModel paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_especialidade_id", nullable = false)
    private ProfissionalEspecialidadeModel profissionalEspecialidade;

    @NotNull
    private LocalDateTime dataHora;

    @Lob
    private String observacoes;
}