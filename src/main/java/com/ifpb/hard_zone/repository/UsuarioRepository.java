package com.ifpb.hard_zone.repository;
import com.ifpb.hard_zone.model.Usuario;
import com.ifpb.hard_zone.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.Date;
import java.util.List;

public class UsuarioRepository {
    private  final EntityManager em = JPAUtil.getEntityManager();

    public void salvar(Usuario usuario) {
        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();
    }

    public Usuario buscarPorId(int id) {
        return em.find(Usuario.class,id);
    }

    public List<Usuario> buscarTodos() {
        return em.createQuery("select u from Usuario u", Usuario.class).getResultList();
    }

    public List<Usuario> filtrarPorNome(String nome) {
        List<Usuario> usuarios = em.createQuery("SELECT u FROM Usuario u WHERE u.nome LIKE :nome ",
                        Usuario.class).setParameter("nome",  "%"  + nome + "%").getResultList();

        return usuarios;
    }

    public Usuario buscarPorNome(String nome) {
        return em.createQuery("SELECT u FROM Usuario u WHERE u.nome = :nome",
                        Usuario.class).setParameter("nome", nome).getSingleResult();
    }

   public Usuario buscarPorEmail(String email) {
        return em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email"
        ,Usuario.class).setParameter("email", email).getSingleResult();
   }

   public List<Usuario> filtrarPeriodo(Date dataInicio, Date dataFim) {
        return em.createQuery("SELECT u FROM Usuario u WHERE u.dataCadastro BETWEEN :dataInicio AND :dataFim",
                Usuario.class).setParameter("dataInicio", dataInicio)
                .setParameter("dataFim", dataFim).getResultList();
   }

   public List<Usuario> buscarPorData(Date dataCadastro) {
        return em.createQuery("SELECT u FROM Usuario u WHERE u.dataCadastro = :dataCadastro",
                Usuario.class).setParameter("dataCadastro", dataCadastro).getResultList();
   }

   public List<Usuario> buscarUsuariosAtivos() {
        return em.createQuery("SELECT u FROM Usuario u WHERE u.ativo = true", Usuario.class)
                .getResultList();

    }

    public List<Usuario> buscarUsuariosDesativos() {
        return em.createQuery("SELECT u FROM Usuario u WHERE u.ativo = false", Usuario.class)
                .getResultList();

    }

   public Usuario remover(int id) {
        Usuario usuario = em.find(Usuario.class,id);
        em.getTransaction().begin();
        em.remove(usuario);
        em.getTransaction().commit();
        return usuario;

   }

}
