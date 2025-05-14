package com.exemplo.agendamedica.model;

import com.exemplo.agendamedica.model.enums.GeneroEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "profissionais")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfissionalModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Column(unique = true)
    private String usuario;

    @NotBlank
    private String nome;

    @NotBlank @Column(unique = true)
    private String crm;

    @NotNull
    private LocalDate dataNascimento;

    @NotBlank @Email
    private String email;

    @NotBlank
    private String telefone;

    @Enumerated(EnumType.STRING) @NotNull
    private GeneroEnum genero;
}