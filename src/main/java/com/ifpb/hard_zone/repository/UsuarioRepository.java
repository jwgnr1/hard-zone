package com.ifpb.hard_zone.repository;

import com.ifpb.hard_zone.model.Usuario;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class UsuarioRepository extends RepositoryBase<Usuario, Long> {

    public UsuarioRepository() {
        super(Usuario.class);
    }

    public List<Usuario> filtrarPorNome(String nome) {
        return consultar(em -> em.createQuery(
                        "SELECT u FROM Usuario u WHERE LOWER(u.nome) LIKE LOWER(:nome)",
                        Usuario.class)
                .setParameter("nome", "%" + nome + "%")
                .getResultList());
    }

    public List<Usuario> buscarPorNome(String nome) {
        return consultar(em -> em.createQuery(
                        "SELECT u FROM Usuario u WHERE LOWER(u.nome) = LOWER(:nome)",
                        Usuario.class)
                .setParameter("nome", nome).getResultList());
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return consultar(em -> em.createQuery(
                        "SELECT u FROM Usuario u WHERE LOWER(u.email) = LOWER(:email)",
                        Usuario.class)
                .setParameter("email", email).getResultStream().findFirst());
    }

    public List<Usuario> filtrarPeriodo(Date dataInicio, Date dataFim) {

        Date proximoMinuto = new Date(dataFim.getTime() + 60000);

        return consultar(em -> em.createQuery(
                        "SELECT u FROM Usuario u " +
                                "WHERE u.dataCadastro >= :dataInicio " +
                                "AND u.dataCadastro < :proximoMinuto",
                        Usuario.class)
                .setParameter("dataInicio", dataInicio)
                .setParameter("proximoMinuto", proximoMinuto).getResultList());
    }

    public List<Usuario> buscarPorData(Date dataCadastro) {

        Date proximoMinuto = new Date(dataCadastro.getTime() + 60000);

        return consultar(em -> em.createQuery(
                        "SELECT u FROM Usuario u " +
                                "WHERE u.dataCadastro >= :dataCadastro " +
                                "AND u.dataCadastro < :proximoMinuto",
                        Usuario.class)
                .setParameter("dataCadastro", dataCadastro)
                .setParameter("proximoMinuto", proximoMinuto).getResultList());
    }

    public List<Usuario> buscarUsuariosAtivos() {
        return consultar(em -> em.createQuery(
                        "SELECT u FROM Usuario u WHERE u.ativo = true",
                        Usuario.class).getResultList());
    }

    public List<Usuario> buscarUsuariosDesativos() {
        return consultar(em -> em.createQuery(
                        "SELECT u FROM Usuario u WHERE u.ativo = false",
                        Usuario.class).getResultList());
    }
}