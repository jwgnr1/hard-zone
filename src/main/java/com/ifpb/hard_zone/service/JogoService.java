package com.ifpb.hard_zone.service;

import com.ifpb.hard_zone.model.Jogo;
import com.ifpb.hard_zone.repository.JogoRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public class JogoService {

    private final JogoRepository jogoRepository = new JogoRepository();

    public Jogo adicionar(Jogo jogo) {
        if (jogo == null) {
            throw new IllegalArgumentException("O jogo não pode ser nulo");
        }
        jogoRepository.salvar(jogo);
        return jogo;
    }

    public void remover(Long id) {
        jogoRepository.remover(id);
    }

    public Jogo buscarPorId(Long id) {
        return jogoRepository.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Jogo não encontrado com id " + id));
    }

    public List<Jogo> listarTodos() {
        return jogoRepository.listarTodos();
    }

}
