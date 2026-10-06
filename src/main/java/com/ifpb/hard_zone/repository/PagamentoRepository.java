package com.ifpb.hard_zone.repository;

import com.ifpb.hard_zone.model.Pagamento;
import com.ifpb.hard_zone.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public class PagamentoRepository extends RepositoryBase<Pagamento, Long>{

    private final EntityManager em = JPAUtil.getEntityManager();

    public PagamentoRepository(){
        super(Pagamento.class);
    }

    public Optional<Pagamento> buscarPorSessao(Long idSessao){
        return em.createQuery("SELECT p FROM Pagamento p WHERE p.sessao.id = :idSessao", Pagamento.class)
                .setParameter("idSessao", idSessao)
                .getResultStream()
                .findFirst();
    }

    public List<Pagamento> filtrarPorperiodo(OffsetDateTime dataInicio, OffsetDateTime dataFim) {
        List<Pagamento> pagamentos = em.createQuery("SELECT p FROM Pagamento p WHERE p.dataHora BETWEEN :dataInicio AND :dataFim",
                        Pagamento.class)
                .setParameter("dataInicio", dataInicio)
                .setParameter("dataFim", dataFim)
                .getResultList();
        return pagamentos;
    }

    public BigDecimal faturamnetoPorPeriodo(OffsetDateTime dataInicio, OffsetDateTime dataFim) {
        return em.createQuery("SELECT COALESCE(SUM(p.valor), 0) FROM Pagamento p WHERE p.dataHora BETWEEN :dataInicio AND :dataFim",
                        BigDecimal.class)
                .setParameter("dataInicio", dataInicio)
                .setParameter("dataFim", dataFim)
                .getSingleResult();
    }

    public Optional<Pagamento> pagamentoComMaiorValor(){
        return em.createQuery("SELECT p FROM Pagamento p ORDER BY p.valor DESC",
                        Pagamento.class)
                .setMaxResults(1)
                .getResultStream()
                .findFirst();
    }
}