package com.ifpb.hard_zone.service;

import com.ifpb.hard_zone.exception.RegraDeNegocioException;
import com.ifpb.hard_zone.model.Jogo;
import com.ifpb.hard_zone.repository.JogoRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public class JogoService {

    private final JogoRepository jogoRepository = new JogoRepository();

    public Jogo adicionar(Jogo jogo) {
        if (jogo == null) {
            throw new RegraDeNegocioException("O jogo não pode ser nulo");
        }

        jogoRepository.salvar(jogo);
        return jogo;
    }

    public void remover(Long id) {
        exigirId(id);
        jogoRepository.remover(id);
    }

    public Jogo buscarPorId(Long id) {
        exigirId(id);
        return jogoRepository.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Jogo não encontrado com id " + id));
    }

    public List<Jogo> listarTodos() {
        return jogoRepository.listarTodos();
    }

    private void exigirId(Long id) {
        if (id == null) {
            throw new RegraDeNegocioException("O id do jogo é obrigatório");
        }
    }
}
