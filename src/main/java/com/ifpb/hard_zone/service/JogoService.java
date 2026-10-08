package com.ifpb.hard_zone.service;

import com.ifpb.hard_zone.exception.RegraDeNegocioException;
import com.ifpb.hard_zone.model.Jogo;
import com.ifpb.hard_zone.repository.JogoRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public class JogoService {

    private final JogoRepository jogoRepository;

    public JogoService(JogoRepository jogoRepository) {
        this.jogoRepository = jogoRepository;
    }

    public JogoService() {
        this(new JogoRepository());
    }

    public Jogo adicionar(Jogo jogo) {
        if (jogo == null) {
            throw new RegraDeNegocioException("O jogo não pode ser nulo");
        }
        if (jogo.getNome() == null || jogo.getNome().isBlank()) {
            throw new RegraDeNegocioException("O nome do jogo é obrigatório");
        }
        if (jogo.getFaixaEtaria() < 0) {
            throw new RegraDeNegocioException("A faixa etária não pode ser negativa");
        }
        jogo.setNome(jogo.getNome().trim());
        if (jogoRepository.existePorNome(jogo.getNome())) {
            throw new RegraDeNegocioException("Já existe um jogo com o nome " + jogo.getNome());
        }
        jogoRepository.salvar(jogo);
        return jogo;
    }

    public Jogo atualizar(Long id, Jogo dados) {
        if (dados == null) {
            throw new RegraDeNegocioException("Os dados do jogo não podem ser nulos");
        }
        if (dados.getNome() == null || dados.getNome().isBlank()) {
            throw new RegraDeNegocioException("O nome do jogo é obrigatório");
        }

        Jogo existente = buscarPorId(id);

        boolean nomeMudou = !dados.getNome().trim().equalsIgnoreCase(existente.getNome());
        if (nomeMudou && jogoRepository.existePorNome(dados.getNome())) {
            throw new RegraDeNegocioException(
                    "Já existe um jogo com o nome " + dados.getNome().trim());
        }

        existente.setNome(dados.getNome().trim());
        existente.setFaixaEtaria(dados.getFaixaEtaria());

        return jogoRepository.atualizar(existente);
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

    public List<Jogo> buscarPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new RegraDeNegocioException("Informe o nome do jogo para a busca");
        }
        return jogoRepository.buscarPorNome(nome);
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
