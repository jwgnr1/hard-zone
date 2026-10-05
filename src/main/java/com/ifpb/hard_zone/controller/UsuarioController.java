package com.ifpb.hard_zone.controller;
import com.ifpb.hard_zone.exception.dataException.DataInvalidaException;
import com.ifpb.hard_zone.exception.usuariosExceptions.DadosUsuarioInvalidoException;
import com.ifpb.hard_zone.exception.usuariosExceptions.UsuarioNaoEncontradoException;
import com.ifpb.hard_zone.exception.usuariosExceptions.UsuarioStatusInvalidoException;
import com.ifpb.hard_zone.model.Usuario;
import com.ifpb.hard_zone.service.UsuarioService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioController {
    private final  UsuarioService usuarioService;

    public UsuarioController() {
        usuarioService = new UsuarioService();
    }

    public void cadastrarUsuario(String nome, String dataNascimento, String email) {
        try {
            usuarioService.salvarUsuario(nome, dataNascimento, email);
            System.out.println("Usuário cadastrado com sucesso!");
        } catch (DadosUsuarioInvalidoException e) {
            System.out.println(e.getMessage());

        } catch (ParseException e) {
            System.out.println("Data inválida. Use o formato dd/MM/yyyy.");
        }
    }

    public Usuario buscarUsuarioPorId(String id) {
        try {
            int idUsuario = Integer.parseInt(id);

            Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);
            System.out.println("Usuário buscado com sucesso!");
            return usuario;

        } catch (NumberFormatException e) {
            System.out.println("Digite apenas números.");

        } catch (UsuarioNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public void atualizarDadosUsuario(String id, String nome, String email) {
        try {
            int idUsuario = Integer.parseInt(id);
            usuarioService.atualizarUsuario(idUsuario, nome, email);
            System.out.println("Usuário atualizado com sucesso!");

        } catch (UsuarioNaoEncontradoException e) {
            System.out.println(e.getMessage());

        } catch(DadosUsuarioInvalidoException e) {
            System.out.println(e.getMessage());

        } catch(NumberFormatException e) {
            System.out.println("Digite apenas números.");
        }
    }

    public void desativarUsuario(String id) {
        try {
            int idUsuario = Integer.parseInt(id);
            usuarioService.desativarUsuario(idUsuario);
            System.out.println("Usuário desativado com sucesso!");

        } catch(UsuarioNaoEncontradoException e) {
            System.out.println(e.getMessage());

        } catch(NumberFormatException e) {
            System.out.println("Digite apenas números.");

        } catch(UsuarioStatusInvalidoException e) {
            System.out.println(e.getMessage());
        }
    }

    public void ativarUsuario(String id) {
        try {
            int idUsuario = Integer.parseInt(id);
            usuarioService.ativarUsuario(idUsuario);
            System.out.println("Usuário ativado com sucesso!");

        } catch(UsuarioNaoEncontradoException e) {
            System.out.println(e.getMessage());

        } catch(NumberFormatException e) {
            System.out.println("Digite apenas números.");

        } catch(UsuarioStatusInvalidoException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Usuario> buscarUsuarioPorNome(String nome) {
        try {

            List<Usuario> usuariosEncontrados =  usuarioService.buscarUsuarioPorNome(nome);
            System.out.println("Usuários encontrados com sucesso!");
            return usuariosEncontrados;

        }  catch(DadosUsuarioInvalidoException e) {
            System.out.println(e.getMessage());

        } catch(UsuarioNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();

    }

    public Usuario buscarUsuarioPorEmail(String email) {
        try {
            Usuario usuario = usuarioService.buscarUsuarioPorEmail(email);
            System.out.println("Usuário encontrado com sucesso!");
            return usuario;

        } catch (UsuarioNaoEncontradoException e) {
            System.out.println(e.getMessage());

        } catch (DadosUsuarioInvalidoException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<Usuario> listarTodosUsuarios() {
        List<Usuario> usuariosEncontrados = usuarioService.listarTodosUsuarios();
        System.out.println("Usuários listados com sucesso!");
        return usuariosEncontrados;
    }

    public List<Usuario> listarTodosUsuariosAtivos() {
        List<Usuario> usuariosAtivos = usuarioService.listarTodosUsuariosAtivos();
        System.out.println("Usuários ativos encontrados com sucesso!");
        return usuariosAtivos;
    }

    public List<Usuario> listarTodosUsuariosDesativados() {
        List<Usuario> usuariosDesativados = usuarioService.listarTodosUsuariosDesativados();
        System.out.println("Usuários desativados encontrados com sucesso!");
        return usuariosDesativados;
    }

    public List<Usuario> listarUsuariosPorDataCadastro(String data) {
        try {
            List<Usuario> usuarios = usuarioService.listarUsuariosPorDataCadastro(data);
            System.out.println("Usuários encontrados com sucesso!");
            return usuarios;

        } catch (DataInvalidaException e) {
            System.out.println(e.getMessage());

        } catch (ParseException e) {
            System.out.println("Data inválida. Use o formato dd/MM/yyyy.");
        }

        return new ArrayList<>();
    }

    public List<Usuario> filtrarPorNomeUsuario(String nome) {
        try {
            List<Usuario> usuariosFiltrados = usuarioService.filtrarUsuariosPorNome(nome);
            System.out.println("Usuarios filtrados com sucesso!");
            return usuariosFiltrados;

        } catch(DadosUsuarioInvalidoException e) {
            System.out.println(e.getMessage());
        }

        return new ArrayList<>();
    }

    public List<Usuario> filtrarUsuariosPorPeriodo(String inicio, String fim) {
        try {
            List<Usuario> usuariosFiltrados = usuarioService.filtrarUsuariosPorPeriodo(inicio, fim);

            System.out.println("Usuários filtrados com sucesso!");
            return usuariosFiltrados;

        } catch (DataInvalidaException e) {
            System.out.println(e.getMessage());

        } catch (ParseException e) {
            System.out.println("Data inválida. Use o formato dd/MM/yyyy.");
        }

        return new ArrayList<>();
    }

    public Usuario excluirUsuarioPermanente(String id) {
        try {
            int idUsuario = Integer.parseInt(id);
            Usuario usuarioExcluido = usuarioService.excluirUsuarioPermanentemente(idUsuario);
            System.out.println("Usuario excluido com sucesso!");
            return usuarioExcluido;

        } catch(UsuarioNaoEncontradoException e) {
            System.out.println(e.getMessage());

        } catch(NumberFormatException e) {
            System.out.println("Digite apenas números.");

        }
        return null;
    }
}
