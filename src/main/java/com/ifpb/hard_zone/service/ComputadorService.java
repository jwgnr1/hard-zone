package com.ifpb.hard_zone.service;

import com.ifpb.hard_zone.model.Computador;
import com.ifpb.hard_zone.model.Jogo;
import com.ifpb.hard_zone.repository.ComputadorRepository;
import com.ifpb.hard_zone.util.enumerate.StatusComputador;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public class ComputadorService {

    private final ComputadorRepository computadorRepository = new ComputadorRepository();

    public Computador adicionarComputador(Computador computador) {
        if (computadorRepository.existePorNumeroMaquina(computador.getNumeroMaquina())) {
            throw new IllegalArgumentException(
                    "Já existe um computador com o número " + computador.getNumeroMaquina());
        }
        computadorRepository.salvar(computador);
        return computador;
    }

    public List<Computador> listarTodos() {
        return computadorRepository.listarTodos();
    }

    public Computador buscarPorId(Long id) {
        return computadorRepository.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Computador não encontrado com id " + id));
    }

    public Computador atualizar(Long id, Computador dados) {
        Computador existente = buscarPorId(id);

        if (!existente.getNumeroMaquina().equals(dados.getNumeroMaquina())
                && computadorRepository.existePorNumeroMaquina(dados.getNumeroMaquina())) {
            throw new IllegalArgumentException(
                    "Já existe um computador com o número " + dados.getNumeroMaquina());
        }

        existente.setEspecificacoes(dados.getEspecificacoes());
        existente.setNumeroMaquina(dados.getNumeroMaquina());
        existente.setStatus(dados.getStatus());
        return computadorRepository.atualizar(existente);
    }

    public Computador alterarStatus(Long id, StatusComputador novoStatus) {
        Computador computador = buscarPorId(id);
        computador.setStatus(novoStatus);
        return computadorRepository.atualizar(computador);
    }

    public void removerComputador(Long id) {
        computadorRepository.remover(id);
    }

    public Computador adicionarJogo(Long computadorId, Long jogoId) {
        return computadorRepository.adicionarJogo(computadorId, jogoId);
    }

    public Computador removerJogo(Long computadorId, Long jogoId) {
        return computadorRepository.removerJogo(computadorId, jogoId);
    }

    public List<Jogo> listarJogos(Long computadorId) {
        return computadorRepository.listarJogos(computadorId);
    }
}