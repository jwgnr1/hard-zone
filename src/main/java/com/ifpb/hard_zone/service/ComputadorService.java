package com.ifpb.hard_zone.service;

import com.ifpb.hard_zone.exception.RegraDeNegocioException;
import com.ifpb.hard_zone.model.Computador;
import com.ifpb.hard_zone.model.Jogo;
import com.ifpb.hard_zone.repository.ComputadorRepository;
import com.ifpb.hard_zone.util.enumerate.StatusComputador;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public class ComputadorService {

    private final ComputadorRepository computadorRepository = new ComputadorRepository();

    public Computador adicionarComputador(Computador computador) {
        validar(computador);

        if (computadorRepository.existePorNumeroMaquina(computador.getNumeroMaquina())) {
            throw new RegraDeNegocioException(
                    "Já existe um computador com o número " + computador.getNumeroMaquina());
        }

        computadorRepository.salvar(computador);
        return computador;
    }

    public List<Computador> listarTodos() {
        return computadorRepository.listarTodos();
    }

    public Computador buscarPorId(Long id) {
        exigirId(id, "computador");
        return computadorRepository.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Computador não encontrado com id " + id));
    }

    public Computador buscarPorNumeroMaquina(Integer numeroMaquina) {
        if (numeroMaquina == null || numeroMaquina <= 0) {
            throw new RegraDeNegocioException("O número da máquina deve ser um inteiro positivo");
        }
        return computadorRepository.buscarPorNumeroMaquina(numeroMaquina)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Computador não encontrado com o número " + numeroMaquina));
    }

    public Computador atualizar(Long id, Computador dados) {
        validar(dados);
        Computador existente = buscarPorId(id);

        if (!existente.getNumeroMaquina().equals(dados.getNumeroMaquina())
                && computadorRepository.existePorNumeroMaquina(dados.getNumeroMaquina())) {
            throw new RegraDeNegocioException(
                    "Já existe um computador com o número " + dados.getNumeroMaquina());
        }

        existente.setEspecificacoes(dados.getEspecificacoes());
        existente.setNumeroMaquina(dados.getNumeroMaquina());
        if (dados.getStatus() != null) {
            existente.setStatus(dados.getStatus());
        }
        return computadorRepository.atualizar(existente);
    }

    public Computador alterarStatus(Long id, StatusComputador novoStatus) {
        if (novoStatus == null) {
            throw new RegraDeNegocioException("O status é obrigatório");
        }
        Computador computador = buscarPorId(id);
        computador.setStatus(novoStatus);
        return computadorRepository.atualizar(computador);
    }

    public void removerComputador(Long id) {
        exigirId(id, "computador");
        computadorRepository.remover(id);
    }

    public Computador adicionarJogo(Long computadorId, Long jogoId) {
        exigirId(computadorId, "computador");
        exigirId(jogoId, "jogo");
        return computadorRepository.adicionarJogo(computadorId, jogoId);
    }

    public Computador removerJogo(Long computadorId, Long jogoId) {
        exigirId(computadorId, "computador");
        exigirId(jogoId, "jogo");
        return computadorRepository.removerJogo(computadorId, jogoId);
    }

    public List<Jogo> listarJogos(Long computadorId) {
        buscarPorId(computadorId);
        return computadorRepository.listarJogos(computadorId);
    }

    private void validar(Computador computador) {
        if (computador == null) {
            throw new RegraDeNegocioException("O computador não pode ser nulo");
        }
        if (computador.getNumeroMaquina() == null || computador.getNumeroMaquina() <= 0) {
            throw new RegraDeNegocioException("O número da máquina deve ser um inteiro positivo");
        }
        if (computador.getEspecificacoes() == null || computador.getEspecificacoes().isBlank()) {
            throw new RegraDeNegocioException("As especificações são obrigatórias");
        }
    }

    private void exigirId(Long id, String entidade) {
        if (id == null) {
            throw new RegraDeNegocioException("O id do " + entidade + " é obrigatório");
        }
    }
}