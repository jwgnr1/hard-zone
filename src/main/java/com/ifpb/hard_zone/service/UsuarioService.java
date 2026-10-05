package com.ifpb.hard_zone.service;

import com.ifpb.hard_zone.exception.dataException.DataInvalidaException;
import com.ifpb.hard_zone.exception.usuariosExceptions.DadosUsuarioInvalidoException;
import com.ifpb.hard_zone.exception.usuariosExceptions.UsuarioNaoEncontradoException;
import com.ifpb.hard_zone.exception.usuariosExceptions.UsuarioStatusInvalidoException;
import com.ifpb.hard_zone.model.Usuario;
import com.ifpb.hard_zone.repository.UsuarioRepository;
import com.ifpb.hard_zone.util.Validator;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class UsuarioService {

    private final UsuarioRepository usuarioRepository = new UsuarioRepository();

    public void salvarUsuario(String nome, String dataNascimento, String email) throws DadosUsuarioInvalidoException, ParseException {

        Usuario novoUsuario = criarUsuario(nome, dataNascimento, email);

        Optional<Usuario> usuarioExistente = usuarioRepository.buscarPorEmail(novoUsuario.getEmail());

        if (usuarioExistente.isPresent()) {
            throw new DadosUsuarioInvalidoException(String.format("Já existe um usuário cadastrado com o email: %s.", email));
        }

        usuarioRepository.salvar(novoUsuario);
    }

    public void atualizarUsuario(Long id, String nome, String email) throws DadosUsuarioInvalidoException,
            UsuarioNaoEncontradoException {

        Validator.validarNome(nome);
        Validator.validarEmail(email);

        Usuario usuarioAtualizar = buscarUsuarioPorId(id);

        String emailNormalizado = email.trim().toLowerCase();

        Optional<Usuario> usuarioExistente = usuarioRepository.buscarPorEmail(emailNormalizado);

        if (usuarioExistente.isPresent() && !usuarioExistente.get().getId().equals(id)) {

            throw new DadosUsuarioInvalidoException(String.format("Já existe um usuário cadastrado com o email: %s.", email));
        }

        usuarioAtualizar.setNome(nome);
        usuarioAtualizar.setEmail(emailNormalizado);

        usuarioRepository.atualizar(usuarioAtualizar);
    }

    public Usuario buscarUsuarioPorId(Long id) throws UsuarioNaoEncontradoException {

        Optional<Usuario> usuario = usuarioRepository.buscarPorId(id);


        return usuario.orElseThrow(() -> new UsuarioNaoEncontradoException(String.format("Usuário não encontrado com o ID: %s.", id)));
    }

    public void desativarUsuario(Long id) throws UsuarioNaoEncontradoException, UsuarioStatusInvalidoException {

        Usuario usuario = buscarUsuarioPorId(id);

        if (!usuario.isAtivo()) {
            throw new UsuarioStatusInvalidoException("Não é possível desativar o usuário, pois ele já está desativado.");
        }

        usuario.setAtivo(false);

        usuarioRepository.atualizar(usuario);
    }

    public void ativarUsuario(Long id) throws UsuarioNaoEncontradoException, UsuarioStatusInvalidoException {

        Usuario usuario = buscarUsuarioPorId(id);

        if (usuario.isAtivo()) {
            throw new UsuarioStatusInvalidoException("Não é possível ativar o usuário, pois ele já está ativo.");
        }

        usuario.setAtivo(true);

        usuarioRepository.atualizar(usuario);
    }

    public List<Usuario> buscarUsuarioPorNome(String nome) throws UsuarioNaoEncontradoException, DadosUsuarioInvalidoException {

        Validator.validarNome(nome);

        List<Usuario> usuariosEncontrados = usuarioRepository.buscarPorNome(nome);

        if (usuariosEncontrados.isEmpty()) {
            throw new UsuarioNaoEncontradoException(String.format("Nenhum usuário foi encontrado com o nome: %s.", nome));
        }

        return usuariosEncontrados;
    }

    public Usuario buscarUsuarioPorEmail(String email) throws UsuarioNaoEncontradoException, DadosUsuarioInvalidoException {

        Validator.validarEmail(email);

        String emailNormalizado = email.trim().toLowerCase();

        Optional<Usuario> usuarioEncontrado = usuarioRepository.buscarPorEmail(emailNormalizado);

        if (usuarioEncontrado.isEmpty()) {
            throw new UsuarioNaoEncontradoException(String.format("Nenhum usuário foi encontrado com o email: %s.", email));
        }

        return usuarioEncontrado.get();
    }

    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.listarTodos();
    }

    public List<Usuario> filtrarUsuariosPorNome(String nome) throws DadosUsuarioInvalidoException {

        Validator.validarNome(nome);

        return usuarioRepository.filtrarPorNome(nome);
    }

    public List<Usuario> filtrarUsuariosPorPeriodo(String inicio, String fim) throws DataInvalidaException, ParseException {

        Date dataInicio = Validator.criarDateTime(inicio);
        Date dataFim = Validator.criarDateTime(fim);

        Validator.validarPeriodo(dataInicio, dataFim);

        return usuarioRepository.filtrarPeriodo(dataInicio, dataFim);
    }

    public List<Usuario> listarUsuariosPorDataCadastro(String data) throws DataInvalidaException, ParseException {

        Date dataCadastro = Validator.criarDateTime(data);

        Validator.validarData(dataCadastro);

        return usuarioRepository.buscarPorData(dataCadastro);
    }

    public List<Usuario> listarTodosUsuariosAtivos() {
        return usuarioRepository.buscarUsuariosAtivos();
    }

    public List<Usuario> listarTodosUsuariosDesativados() {
        return usuarioRepository.buscarUsuariosDesativos();
    }

    public Usuario excluirUsuarioPermanentemente(Long id) throws UsuarioNaoEncontradoException {

        Usuario usuario = buscarUsuarioPorId(id);

        return usuarioRepository.remover(usuario);
    }

    private Usuario criarUsuario(String nome, String dataNascimento, String email) throws DadosUsuarioInvalidoException,
            ParseException {

        Date dataNascimentoFormatada = Validator.criarDate(dataNascimento);

        String emailNormalizado = email.trim().toLowerCase();

        validarDadosUsuario(nome, dataNascimentoFormatada, emailNormalizado);

        Usuario usuario = new Usuario();

        usuario.setNome(nome);
        usuario.setDataNascimento(dataNascimentoFormatada);
        usuario.setEmail(emailNormalizado);
        usuario.setDataCadastro(new Date());
        usuario.setAtivo(true);

        return usuario;
    }

    private void validarDadosUsuario(String nome, Date dataNascimento, String email
    ) throws DadosUsuarioInvalidoException {

        Validator.validarNome(nome);
        Validator.validarDataNascimento(dataNascimento);
        Validator.validarEmail(email);
    }
}