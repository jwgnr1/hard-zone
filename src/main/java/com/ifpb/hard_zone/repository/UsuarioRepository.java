package com.ifpb.hard_zone.repository;
import com.ifpb.hard_zone.model.Usuario;
import com.ifpb.hard_zone.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.Date;
import java.util.List;

public class UsuarioRepository extends RepositoryBase<Usuario, Long>{
    private  final EntityManager em = JPAUtil.getEntityManager();

    public UsuarioRepository(){
        super(Usuario.class);
    }

    public Usuario buscarPorId(int id) {
        return em.find(Usuario.class,id);
    }

    public List<Usuario> filtrarPorNome(String nome) {
        List<Usuario> usuarios = em.createQuery("SELECT u FROM Usuario u WHERE u.nome LIKE :nome ",
                        Usuario.class).setParameter("nome",  "%"  + nome + "%").getResultList();

        return usuarios;
    }

    public List<Usuario> buscarPorNome(String nome) {
        return em.createQuery(
                "SELECT u FROM Usuario u WHERE u.nome = :nome",
                Usuario.class).setParameter("nome", nome).getResultList();
    }

   public Usuario buscarPorEmail(String email) {
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email"
                    , Usuario.class).setParameter("email", email).getSingleResult();
        }catch(NoResultException e) {
            return null;
        }
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
}
