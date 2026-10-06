package com.ifpb.hard_zone.repository;
import com.ifpb.hard_zone.model.Sessao;
import com.ifpb.hard_zone.repository.RepositoryBase;
import com.ifpb.hard_zone.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class SessaoRepository extends RepositoryBase<Sessao, Long> {

    private final EntityManager em = JPAUtil.getEntityManager();

    public SessaoRepository() {
        super(Sessao.class);
    }

    public List<Sessao> buscarSessoesAtivas() {
        return em.createQuery(
                "SELECT s FROM Sessao s WHERE s.dataFim IS NULL",
                Sessao.class
        ).getResultList();
    }

    public List<Sessao> buscarPorUsuario(Long idUsuario) {
        return em.createQuery(
                        "SELECT s FROM Sessao s WHERE s.usuario.id = :idUsuario",
                        Sessao.class
                ).setParameter("idUsuario", idUsuario)
                .getResultList();
    }

    public List<Sessao> buscarPorComputador(Long idComputador) {
        return em.createQuery(
                        "SELECT s FROM Sessao s WHERE s.computador.id = :idComputador",
                        Sessao.class
                ).setParameter("idComputador", idComputador)
                .getResultList();
    }
}