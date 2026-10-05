package com.ifpb.hard_zone.repository;

import com.ifpb.hard_zone.model.Jogo;
import com.ifpb.hard_zone.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;

public class JogoRepository extends RepositoryBase<Jogo, Long>{


    private final EntityManager em = JPAUtil.getEntityManager();

    public JogoRepository() {super(Jogo.class);}

    @Override
    public Jogo remover(Jogo jogo) {
        remover(jogo.getId());
        return jogo;
    }

    public void remover(Long id) {
        executarEmTransacao(em -> {
            Jogo jogo = em.find(Jogo.class, id);
            if (jogo == null) {
                throw new EntityNotFoundException("Jogo não encontrado com id " + id);
            }
            new ArrayList<>(jogo.getComputadores())
                    .forEach(computador -> computador.removerJogo(jogo));

            em.remove(jogo);
            return null;
        });
    }

}
