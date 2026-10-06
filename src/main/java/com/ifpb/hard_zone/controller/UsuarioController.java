package com.ifpb.hard_zone.controller;

import com.ifpb.hard_zone.model.Usuario;
import com.ifpb.hard_zone.service.UsuarioService;

import java.util.List;

public class UsuarioController {

    private final UsuarioService usuarioService = new UsuarioService();

    public String cadastrarUsuario(String nome, String dataNascimento, String email) {
        return ControllerExecutor.executar(() -> usuarioService.salvarUsuario(nome, dataNascimento, email)
                , "Usuário cadastrado com sucesso!");
    }

    public Usuario buscarUsuarioPorId(Long id) {
        return ControllerExecutor.executar(() -> usuarioService.buscarUsuarioPorId(id));
    }

    public String atualizarDadosUsuario(Long id, String nome, String email) {
        return ControllerExecutor.executar(
                () -> usuarioService.atualizarUsuario(id, nome, email), "Usuário atualizado com sucesso!");
    }

    public String desativarUsuario(Long id) {
        return ControllerExecutor.executar(
                () -> usuarioService.desativarUsuario(id), "Usuário desativado com sucesso!");
    }

    public String ativarUsuario(Long id) {
        return ControllerExecutor.executar(
                () -> usuarioService.ativarUsuario(id), "Usuário ativado com sucesso!");
    }

    public List<Usuario> buscarUsuarioPorNome(String nome) {
        return ControllerExecutor.executar(() -> usuarioService.buscarUsuarioPorNome(nome));
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return ControllerExecutor.executar(() -> usuarioService.buscarUsuarioPorEmail(email));
    }

    public List<Usuario> listarTodosUsuarios() {
        return usuarioService.listarTodosUsuarios();
    }

    public List<Usuario> listarTodosUsuariosAtivos() {
        return usuarioService.listarTodosUsuariosAtivos();
    }

    public List<Usuario> listarTodosUsuariosDesativados() {
        return usuarioService.listarTodosUsuariosDesativados();
    }

    public List<Usuario> listarUsuariosPorDataCadastro(String data) {
        return ControllerExecutor.executar(
                () -> usuarioService.listarUsuariosPorDataCadastro(data)
        );
    }

    public List<Usuario> filtrarPorNomeUsuario(String nome) {
        return ControllerExecutor.executar(
                () -> usuarioService.filtrarUsuariosPorNome(nome)
        );
    }

    public List<Usuario> filtrarUsuariosPorPeriodo(String inicio, String fim) {
        return ControllerExecutor.executar(
                () -> usuarioService.filtrarUsuariosPorPeriodo(inicio, fim)
        );
    }

    public String excluirUsuarioPermanente(Long id) {
        return ControllerExecutor.executar(
                () -> usuarioService.excluirUsuarioPermanentemente(id),
                "Usuário excluído com sucesso!"
        );
    }
}