package com.exemplo.agendamedica.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "profissionais_especialidades")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfissionalEspecialidadeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private ProfissionalModel profissional;

    @ManyToOne
    @JoinColumn(name = "especialidade_id")
    private EspecialidadeModel especialidade;
}