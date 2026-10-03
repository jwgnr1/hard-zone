package com.ifpb.hard_zone.repository;

import com.ifpb.hard_zone.model.Usuario;
import com.ifpb.hard_zone.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public abstract class RepositoryBase<T, ID> implements Repository<T, ID>{

    protected final EntityManager em = JPAUtil.getEntityManager();
    private final Class<T> entidade;

    public RepositoryBase(Class<T> entidade){
        this.entidade = entidade;
    }

    @Override
    public void salvar(T entidade) {
        em.getTransaction().begin();
        em.persist(entidade);
        em.getTransaction().commit();
    }

    @Override
    public T atualizar(T entidade) {
        em.getTransaction().begin();
        T entidadeAtualizada = em.merge(entidade);
        em.getTransaction().commit();
        return entidadeAtualizada;
    }

    @Override
    public T remover(T entidade) {
        em.getTransaction().begin();
        em.remove(entidade);
        em.getTransaction().commit();
        return entidade;
    }

    @Override
    public Optional<T> buscarPorId(ID id) {
        return Optional.ofNullable(em.find(entidade, id));
    }

    @Override
    public List<T> buscarTodos() {
        return em.createQuery("SELECT t FROM " + entidade.getSimpleName() + " t", entidade)
                .getResultList();
    }

    @Override
    public List<T> filtrarPorperiodo(OffsetDateTime dataInicio, OffsetDateTime dataFim) {
        String jpql = "SELECT t FROM " + entidade.getSimpleName() + " t WHERE t.dataCadastro BETWEEN :dataInicio AND :dataFim";

        return em.createQuery(jpql, entidade)
                .setParameter("dataInicio", dataInicio)
                .setParameter("dataFim", dataFim)
                .getResultList();
    }
}
