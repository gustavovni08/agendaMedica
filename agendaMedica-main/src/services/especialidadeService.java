package com.exemplo.agendamedica.services;

import com.exemplo.agendamedica.dao.ProfissionalDao;
import com.exemplo.agendamedica.model.ProfissionalModel;
import com.exemplo.agendamedica.model.enums.GeneroEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProfissionalService {

    private final ProfissionalDao profissionalDao;

    @Autowired
    public ProfissionalService(ProfissionalDao profissionalDao) {
        this.profissionalDao = profissionalDao;
    }

    @Transactional
    public ProfissionalModel salvar(ProfissionalModel profissional) {
        validarProfissional(profissional);
        return profissionalDao.save(profissional);
    }

    public List<ProfissionalModel> listarTodos() {
        return profissionalDao.findAll();
    }

    public Optional<ProfissionalModel> buscarPorId(Long id) {
        return profissionalDao.findById(id);
    }

    public Optional<ProfissionalModel> buscarPorUsuario(String usuario) {
        return profissionalDao.findByUsuario(usuario);
    }

    public Optional<ProfissionalModel> buscarPorCrm(String crm) {
        return profissionalDao.findByCrm(crm);
    }

    public Optional<ProfissionalModel> buscarPorEmail(String email) {
        return profissionalDao.findByEmail(email);
    }

    @Transactional
    public void excluir(Long id) {
        profissionalDao.deleteById(id);
    }

    @Transactional
    public ProfissionalModel atualizar(Long id, ProfissionalModel profissionalAtualizado) {
        ProfissionalModel profissionalExistente = profissionalDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        atualizarDadosProfissional(profissionalExistente, profissionalAtualizado);
        return profissionalDao.save(profissionalExistente);
    }

    private void validarProfissional(ProfissionalModel profissional) {
        if (profissionalDao.existsByUsuario(profissional.getUsuario())) {
            throw new RuntimeException("Usuário já cadastrado");
        }

        if (profissionalDao.existsByCrm(profissional.getCrm())) {
            throw new RuntimeException("CRM já cadastrado");
        }

        if (profissionalDao.existsByEmail(profissional.getEmail())) {
            throw new RuntimeException("E-mail já cadastrado");
        }
    }

    private void atualizarDadosProfissional(ProfissionalModel profissionalExistente, ProfissionalModel profissionalAtualizado) {
        if (profissionalAtualizado.getNome() != null) {
            profissionalExistente.setNome(profissionalAtualizado.getNome());
        }

        if (profissionalAtualizado.getDataNascimento() != null) {
            profissionalExistente.setDataNascimento(profissionalAtualizado.getDataNascimento());
        }

        if (profissionalAtualizado.getTelefone() != null) {
            profissionalExistente.setTelefone(profissionalAtualizado.getTelefone());
        }

        if (profissionalAtualizado.getGenero() != null) {
            profissionalExistente.setGenero(profissionalAtualizado.getGenero());
        }
    }
}