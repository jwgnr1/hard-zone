package com.ifpb.hard_zone.repository;
import com.ifpb.hard_zone.model.Sessao;

import java.util.List;

public class SessaoRepository extends RepositoryBase<Sessao, Long> {

    public SessaoRepository() {
        super(Sessao.class);
    }

    public List<Sessao> buscarSessoesAtivas() {
        return consultar(em -> em.createQuery(
                "SELECT s FROM Sessao s WHERE s.dataFim IS NULL",
                Sessao.class
        ).getResultList());
    }

    public List<Sessao> buscarPorUsuario(Long idUsuario) {
        return consultar(em -> em.createQuery(
                        "SELECT s FROM Sessao s WHERE s.usuario.id = :idUsuario",
                        Sessao.class
                ).setParameter("idUsuario", idUsuario)
                .getResultList());
    }

    public List<Sessao> buscarPorComputador(Long idComputador) {
        return consultar(em -> em.createQuery(
                        "SELECT s FROM Sessao s WHERE s.computador.id = :idComputador",
                        Sessao.class
                ).setParameter("idComputador", idComputador)
                .getResultList());
    }
}
