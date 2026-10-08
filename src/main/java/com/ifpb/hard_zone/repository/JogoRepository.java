package com.ifpb.hard_zone.repository;

import com.ifpb.hard_zone.model.Jogo;
import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class JogoRepository extends RepositoryBase<Jogo, Long>{

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

    public List<Jogo> buscarPorNome(String nome) {
        return consultar(em -> em.createQuery(
                        "select j from Jogo j where lower(j.nome) like lower(:nome) order by j.nome",
                        Jogo.class)
                .setParameter("nome", "%" + nome.trim() + "%")
                .getResultList());
    }

    public boolean existePorNome(String nome) {
        return consultar(em -> em.createQuery(
                        "select count(j) from Jogo j where lower(j.nome) = lower(:nome)",
                        Long.class)
                .setParameter("nome", nome.trim())
                .getSingleResult() > 0);
    }

}
