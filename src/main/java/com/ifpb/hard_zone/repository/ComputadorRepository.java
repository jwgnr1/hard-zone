package com.ifpb.hard_zone.repository;

import com.ifpb.hard_zone.model.Computador;
import com.ifpb.hard_zone.model.Jogo;
import com.ifpb.hard_zone.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComputadorRepository extends RepositoryBase<Computador, Long>{

    private final EntityManager em = JPAUtil.getEntityManager();

    public ComputadorRepository() {super(Computador.class);}

    public boolean existePorNumeroMaquina(Integer numeroMaquina) {
        return consultar(em -> em.createQuery(
                        "select count(c) from Computador c where c.numeroMaquina = :n",
                        Long.class)
                .setParameter("n", numeroMaquina)
                .getSingleResult() > 0);
    }

    public Optional<Computador> buscarPorNumeroMaquina(Integer numeroMaquina) {
        return consultar(em -> em.createQuery(
                        "select c from Computador c where c.numeroMaquina = :n",
                        Computador.class)
                .setParameter("n", numeroMaquina)
                .getResultList()
                .stream()
                .findFirst());
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
            // desfaz o vínculo dos dois lados antes de remover
            new ArrayList<>(computador.getJogos()).forEach(computador::removerJogo);
            em.remove(computador);
            return null;
        });
    }

    public Computador adicionarJogo(Long computadorId, Long jogoId) {
        return executarEmTransacao(em -> {
            Computador computador = em.find(Computador.class, computadorId);
            if (computador == null) {
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
            if (computador == null) {
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
                        "select j from Computador c join c.jogos j where c.id = :id",
                        Jogo.class)
                .setParameter("id", computadorId)
                .getResultList());
    }

}
