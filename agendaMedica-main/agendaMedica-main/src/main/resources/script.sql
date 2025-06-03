CREATE DATABASE IF NOT EXISTS clinica_medica CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE clinica_medica;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS pacientes;
CREATE TABLE pacientes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE,
    telefone VARCHAR(15),
    email VARCHAR(100),
    data_nascimento DATE,
    data_cadastro DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_paciente_cpf (cpf)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS especialidades;
CREATE TABLE especialidades (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    INDEX idx_especialidade_nome (nome)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS profissionais;
CREATE TABLE profissionais (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    crm VARCHAR(20) NOT NULL UNIQUE,
    data_nascimento DATE NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefone VARCHAR(15) NOT NULL,
    genero ENUM('MASCULINO','FEMININO','OUTRO','NAO_INFORMADO') NOT NULL,
    data_cadastro DATETIME DEFAULT CURRENT_TIMESTAMP,
    ativo BOOLEAN DEFAULT TRUE,
    INDEX idx_profissional_nome (nome),
    INDEX idx_profissional_crm (crm)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS profissionais_especialidades;
CREATE TABLE profissionais_especialidades (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    profissional_id BIGINT NOT NULL,
    especialidade_id BIGINT NOT NULL,
    FOREIGN KEY (profissional_id) REFERENCES profissionais(id) ON DELETE CASCADE,
    FOREIGN KEY (especialidade_id) REFERENCES especialidades(id) ON DELETE CASCADE,
    UNIQUE KEY uk_prof_esp (profissional_id, especialidade_id),
    INDEX idx_prof_esp_profissional (profissional_id)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS slots_agenda;
CREATE TABLE slots_agenda (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    profissional_especialidade_id BIGINT NOT NULL,
    FOREIGN KEY (profissional_especialidade_id)
        REFERENCES profissionais_especialidades(id) ON DELETE CASCADE,
    INDEX idx_slot_prof_esp (profissional_especialidade_id)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS slot_dias_semana;
CREATE TABLE slot_dias_semana (
    slot_id BIGINT NOT NULL,
    dia_semana ENUM('SEGUNDA','TERCA','QUARTA','QUINTA','SEXTA','SABADO','DOMINGO') NOT NULL,
    PRIMARY KEY (slot_id, dia_semana),
    FOREIGN KEY (slot_id) REFERENCES slots_agenda(id) ON DELETE CASCADE
) ENGINE=InnoDB;

DROP TABLE IF EXISTS slot_horarios;
CREATE TABLE slot_horarios (
    slot_id BIGINT NOT NULL,
    horario TIME NOT NULL,
    PRIMARY KEY (slot_id, horario),
    FOREIGN KEY (slot_id) REFERENCES slots_agenda(id) ON DELETE CASCADE,
    INDEX idx_horario (horario)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS agendamentos;
CREATE TABLE agendamentos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    data_hora DATETIME NOT NULL,
    paciente_id BIGINT NOT NULL,
    profissional_especialidade_id BIGINT NOT NULL,
    slot_id BIGINT,
    status ENUM('AGENDADO','CONFIRMADO','CANCELADO','REALIZADO','NAO_COMPARECEU') NOT NULL DEFAULT 'AGENDADO',
    data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (paciente_id) REFERENCES pacientes(id),
    FOREIGN KEY (profissional_especialidade_id) REFERENCES profissionais_especialidades(id),
    FOREIGN KEY (slot_id) REFERENCES slots_agenda(id),
    INDEX idx_agendamento_data (data_hora),
    INDEX idx_agendamento_paciente (paciente_id)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS atendimentos;
CREATE TABLE atendimentos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    agendamento_id BIGINT NOT NULL UNIQUE,
    paciente_id BIGINT NOT NULL,
    profissional_especialidade_id BIGINT NOT NULL,
    slot_id BIGINT,
    data_hora DATETIME NOT NULL,
    observacoes TEXT,
    FOREIGN KEY (agendamento_id) REFERENCES agendamentos(id),
    FOREIGN KEY (paciente_id) REFERENCES pacientes(id),
    FOREIGN KEY (profissional_especialidade_id) REFERENCES profissionais_especialidades(id),
    FOREIGN KEY (slot_id) REFERENCES slots_agenda(id),
    INDEX idx_atendimento_agendamento (agendamento_id)
) ENGINE=InnoDB;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO especialidades (nome, descricao) VALUES
('Cardiologia', 'Especialidade médica que trata do coração'),
('Dermatologia', 'Especialidade médica que trata da pele');

INSERT INTO profissionais (usuario, nome, crm, data_nascimento, email, telefone, genero) VALUES
('dr.silva', 'Dr. João Silva', 'CRM/SP 123456', '1980-05-15', 'dr.joao@email.com', '11999998888', 'MASCULINO'),
('dra.santos', 'Dra. Maria Santos', 'CRM/SP 654321', '1985-08-20', 'dra.maria@email.com', '11988887777', 'FEMININO');

INSERT INTO profissionais_especialidades (profissional_id, especialidade_id) VALUES
(1, 1), -- Dr. João é cardiologista
(2, 2); -- Dra. Maria é dermatologista

INSERT INTO pacientes (nome, cpf, telefone, email, data_nascimento) VALUES
('Carlos Oliveira', '123.456.789-00', '11977776666', 'carlos@email.com', '1990-01-10'),
('Ana Pereira', '987.654.321-00', '11966665555', 'ana@email.com', '1988-03-25');

INSERT INTO slots_agenda (profissional_especialidade_id) VALUES (1), (2);

INSERT INTO slot_dias_semana (slot_id, dia_semana) VALUES
(1, 'SEGUNDA'), (1, 'QUARTA'), (1, 'SEXTA'),
(2, 'TERCA'), (2, 'QUINTA');

INSERT INTO slot_horarios (slot_id, horario) VALUES
(1, '08:00:00'), (1, '09:00:00'), (1, '10:00:00'),
(2, '14:00:00'), (2, '15:00:00');

SELECT
    p.nome AS profissional,
    e.nome AS especialidade,
    ds.dia_semana AS dia,
    h.horario AS horario

FROM
    slots_agenda sa
JOIN profissionais_especialidades pe ON sa.profissional_especialidade_id = pe.id
JOIN profissionais p ON pe.profissional_id = p.id
JOIN especialidades e ON pe.especialidade_id = e.id
JOIN slot_dias_semana ds ON sa.id = ds.slot_id
JOIN slot_horarios h ON sa.id = h.slot_id
WHERE
   p.ativo = TRUE;