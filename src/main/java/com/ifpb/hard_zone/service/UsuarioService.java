package com.ifpb.hard_zone.service;
import com.ifpb.hard_zone.exception.DadosUsuarioInvalidoException;
import com.ifpb.hard_zone.exception.Data.DataInvalidaException;
import com.ifpb.hard_zone.exception.UsuarioNaoEncontradoException;
import com.ifpb.hard_zone.model.Usuario;
import com.ifpb.hard_zone.repository.UsuarioRepository;
import com.ifpb.hard_zone.util.Validator;

import java.util.Date;
import java.util.List;

public class UsuarioService {
    UsuarioRepository usuarioRepository = new UsuarioRepository();

    public void salvarUsuario(String nome, Date dataNascimento, String email) throws DadosUsuarioInvalidoException {
        Usuario usuario = criarUsuario(nome, dataNascimento, email);
        usuarioRepository.salvar(usuario);

    }

    public void atualizarUsuario(int id, String nome, String email)
            throws DadosUsuarioInvalidoException, UsuarioNaoEncontradoException {

        Usuario usuarioAtualizar = buscarUsuarioPorId(id);

        Validator.validarNome(nome);
        usuarioAtualizar.setNome(nome);

        Validator.validarEmail(email);

        Usuario usuarioExistente = usuarioRepository.buscarPorEmail(email);

        if (usuarioExistente != null && usuarioExistente.getId() != id) {
            throw new DadosUsuarioInvalidoException(
                    String.format("Já existe um Usuario com esse email: %s", email)
            );
        }

        usuarioAtualizar.setEmail(email);

        usuarioRepository.atualizar(usuarioAtualizar);
    }

    public Usuario buscarUsuarioPorId(int id) throws UsuarioNaoEncontradoException {
        Usuario usuario = usuarioRepository.buscarPorId(id);

        if (usuario == null) {
            throw new UsuarioNaoEncontradoException(String.format("Nenhum usuario encontrado com esse ID: %d no banco de dados" ,id));
        }

        return usuario;
    }

    public List<Usuario> buscarUsuarioPorNome(String nome) {
        List<Usuario> usuario = usuarioRepository.buscarPorNome(nome);

        return usuario;
    }

    public Usuario buscarUsuarioPorEmail(String email) throws UsuarioNaoEncontradoException {
        Usuario usuario = usuarioRepository.buscarPorEmail(email);

        if (usuario == null) {
            throw new UsuarioNaoEncontradoException(String.format("Nenhum usuario encontrado com esse email: %s no banco de dados" , email));
        }

        return usuario;
    }

    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.buscarTodos();

    }

    public List<Usuario> filtrarUsuariosPorNome(String nome) {
        return usuarioRepository.filtrarPorNome(nome);

    }

    public List<Usuario> filtrarUsuariosPorDataCadastro(Date dataInicio, Date dataFim) throws DataInvalidaException {
        Validator.validarPeriodo(dataInicio, dataFim);
       return usuarioRepository.filtrarPeriodo(dataInicio, dataFim);

    }

    public List<Usuario> buscarPorDataCadastro(Date data) throws DataInvalidaException {
        Validator.validarData(data);
        return usuarioRepository.buscarPorData(data);

    }

    public List<Usuario> listarTodosUsuariosAtivos () {
        return usuarioRepository.buscarUsuariosAtivos();

    }

    public List<Usuario> listarTodosUsuariosDesativado () {
         return usuarioRepository.buscarUsuariosDesativos();

    }

    public Usuario excluirUsuarioPermanentemente(int id) throws UsuarioNaoEncontradoException {
        Usuario usuario = buscarUsuarioPorId(id);
        return usuarioRepository.remover(usuario);

    }

    private Usuario criarUsuario(String nome, Date dataNascimento, String email) throws DadosUsuarioInvalidoException {
        verificarEntity(nome, dataNascimento, email);

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setDataNascimento(dataNascimento);
        usuario.setEmail(email);
        usuario.setDataCadastro(new Date());
        usuario.setAtivo(true);

        return usuario;
    }

    private void verificarEntity(String nome, Date dataNascimento, String email) throws DadosUsuarioInvalidoException {
       Validator.validarNome(nome);
       Validator.validarDataNascimento(dataNascimento);
       Validator.validarEmail(email);

    }
}
