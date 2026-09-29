package com.ifpb.hard_zone.repository;
import com.ifpb.hard_zone.model.Usuario;
import com.ifpb.hard_zone.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.Date;
import java.util.List;

public class UsuarioRepository {
    private  final EntityManager em = JPAUtil.getEntityManager();

    public void salvar(Usuario usuario) {
        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        }catch(Exception e) {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }


    }

    public Usuario buscarPorId(int id) {
        return em.find(Usuario.class,id);
    }

    public List<Usuario> buscarTodos() {
        return em.createQuery("select u from Usuario u", Usuario.class).getResultList();
    }

    public List<Usuario> filtrarPorNome(String nome) {
        List<Usuario> usuarios = em.createQuery("SELECT u FROM Usuario u WHERE LOWER(u.nome) LIKE lOWER(:nome) ",
                        Usuario.class).setParameter("nome",  "%"  + nome + "%").getResultList();

        return usuarios;
    }

    public List<Usuario> buscarPorNome(String nome) {
        return em.createQuery(
                "SELECT u FROM Usuario u WHERE LOWER(u.nome) = LOWER(:nome)",
                Usuario.class).setParameter("nome", nome).getResultList();
    }

   public Usuario buscarPorEmail(String email) {
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE LOWER(u.email) = LOWER(:email)"
                    , Usuario.class).setParameter("email", email).getSingleResult();
        }catch(NoResultException e) {
            return null;
        }
   }

    public List<Usuario> filtrarPeriodo(Date dataInicio, Date dataFim) {

        Date proximoMinuto = new Date(dataFim.getTime() + 60000);

        return em.createQuery(
                        "SELECT u FROM Usuario u " +
                                "WHERE u.dataCadastro >= :dataInicio " +
                                "AND u.dataCadastro < :proximoMinuto",
                        Usuario.class)
                .setParameter("dataInicio", dataInicio)
                .setParameter("proximoMinuto", proximoMinuto).getResultList();
    }

    public List<Usuario> buscarPorData(Date dataCadastro) {
        Date proximoMinuto = new Date(dataCadastro.getTime() + 60000);

        return em.createQuery(
                        "SELECT u FROM Usuario u " +
                                "WHERE u.dataCadastro >= :dataCadastro " +
                                "AND u.dataCadastro < :proximoMinuto",
                        Usuario.class)
                .setParameter("dataCadastro", dataCadastro)
                .setParameter("proximoMinuto", proximoMinuto)
                .getResultList();
    }

   public List<Usuario> buscarUsuariosAtivos() {
        return em.createQuery("SELECT u FROM Usuario u WHERE u.ativo = true", Usuario.class)
                .getResultList();

    }

    public List<Usuario> buscarUsuariosDesativos() {
        return em.createQuery("SELECT u FROM Usuario u WHERE u.ativo = false", Usuario.class)
                .getResultList();

    }

    public void atualizar(Usuario usuario) {
        try {

            em.getTransaction().begin();
            em.merge(usuario);
            em.getTransaction().commit();
            return;

        }catch(Exception e) {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

   public Usuario remover(Usuario usuario) {
        try {
            em.getTransaction().begin();
            em.remove(usuario);
            em.getTransaction().commit();
            return usuario;


        }catch(Exception e) {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }


   }

}
