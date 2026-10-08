package com.ifpb.hard_zone.repository;

import com.ifpb.hard_zone.model.Computador;
import com.ifpb.hard_zone.model.Jogo;
import com.ifpb.hard_zone.util.enumerate.StatusComputador;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public class ComputadorRepository extends RepositoryBase<Computador, Long>{

    public ComputadorRepository() {super(Computador.class);}

    public boolean existePorNumeroMaquina(Integer numeroMaquina) {
        return consultar(em -> em.createQuery(
                        "select count(c) from Computador c where c.numeroMaquina = :n and c.ativo = true",
                        Long.class)
                .setParameter("n", numeroMaquina)
                .getSingleResult() > 0);
    }

    public Optional<Computador> buscarPorNumeroMaquina(Integer numeroMaquina) {
        return consultar(em -> em.createQuery(
                        "select c from Computador c where c.numeroMaquina = :n and c.ativo = true",
                        Computador.class)
                .setParameter("n", numeroMaquina)
                .getResultList()
                .stream()
                .findFirst());
    }

    @Override
    public List<Computador> listarTodos() {
        return consultar(em -> em.createQuery(
                "SELECT c FROM Computador c WHERE c.ativo = true", Computador.class)
                .getResultList());
    }

    @Override
    public Computador remover(Computador computador) {
        remover(computador.getId());
        return computador;
    }

    public void remover(Long id) {
        executarEmTransacao(em -> {
            Computador computador = em.find(Computador.class, id);
            if (computador == null) {
                throw new EntityNotFoundException("Computador não encontrado com id " + id);
            }
            // Abordagem A: Soft Delete para preservar histórico de sessões
            computador.setAtivo(false);
            computador.setStatus(StatusComputador.FORA_DE_USO);
            em.merge(computador);
            return null;
        });
    }

    public Computador adicionarJogo(Long computadorId, Long jogoId) {
        return executarEmTransacao(em -> {
            Computador computador = em.find(Computador.class, computadorId);
            if (computador == null || !computador.isAtivo()) {
                throw new EntityNotFoundException("Computador não encontrado com id " + computadorId);
            }
            Jogo jogo = em.find(Jogo.class, jogoId);
            if (jogo == null) {
                throw new EntityNotFoundException("Jogo não encontrado com id " + jogoId);
            }
            computador.adicionarJogo(jogo);
            return computador;
        });
    }

    public Computador removerJogo(Long computadorId, Long jogoId) {
        return executarEmTransacao(em -> {
            Computador computador = em.find(Computador.class, computadorId);
            if (computador == null || !computador.isAtivo()) {
                throw new EntityNotFoundException("Computador não encontrado com id " + computadorId);
            }
            Jogo jogo = em.find(Jogo.class, jogoId);
            if (jogo == null) {
                throw new EntityNotFoundException("Jogo não encontrado com id " + jogoId);
            }
            computador.removerJogo(jogo);
            return computador;
        });
    }

    public List<Jogo> listarJogos(Long computadorId) {
        return consultar(em -> em.createQuery(
                        "select j from Computador c join c.jogos j where c.id = :id and c.ativo = true",
                        Jogo.class)
                .setParameter("id", computadorId)
                .getResultList());
    }

}
