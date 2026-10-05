package com.ifpb.hard_zone.repository;

import com.ifpb.hard_zone.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class RepositoryBase<T, ID> implements Repository<T, ID> {

    private final Class<T> entidade;

    public RepositoryBase(Class<T> entidade) {
        this.entidade = entidade;
    }

    protected <R> R executarEmTransacao(Function<EntityManager, R> operacao) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            R resultado = operacao.apply(em);
            tx.commit();
            return resultado;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    protected <R> R consultar(Function<EntityManager, R> operacao) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return operacao.apply(em);
        } finally {
            em.close();
        }
    }

    @Override
    public void salvar(T entidade) {
        executarEmTransacao(em -> {
            em.persist(entidade);
            return null;
        });
    }

    @Override
    public T atualizar(T entidade) {
        return executarEmTransacao(em -> em.merge(entidade));
    }

    @Override
    public T remover(T entidade) {
        return executarEmTransacao(em -> {
            T gerenciada = em.contains(entidade) ? entidade : em.merge(entidade);
            em.remove(gerenciada);
            return entidade;
        });
    }

    @Override
    public Optional<T> buscarPorId(ID id) {
        return consultar(em -> Optional.ofNullable(em.find(entidade, id)));
    }

    @Override
    public List<T> buscarTodos() {
        return consultar(em -> em.createQuery(
                        "SELECT t FROM " + entidade.getSimpleName() + " t", entidade)
                .getResultList());
    }
}