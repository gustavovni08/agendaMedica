package com.exemplo.agendamedica.model;

import com.exemplo.agendamedica.model.enums.StatusAgendamentoEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "agendamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendamentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Data/hora obrigatória")
    private LocalDateTime dataHora;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private PacienteModel paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_especialidade_id", nullable = false)
    private ProfissionalEspecialidadeModel profissionalEspecialidade;

    @Enumerated(EnumType.STRING)
    @NotNull
    private StatusAgendamentoEnum status;
}