package com.ifpb.hard_zone.service;
import com.ifpb.hard_zone.exception.usuariosExceptions.DadosUsuarioInvalidoException;
import com.ifpb.hard_zone.exception.dataException.DataInvalidaException;
import com.ifpb.hard_zone.exception.usuariosExceptions.UsuarioNaoEncontradoException;
import com.ifpb.hard_zone.exception.usuariosExceptions.UsuarioStatusInvalidoException;
import com.ifpb.hard_zone.model.Usuario;
import com.ifpb.hard_zone.repository.UsuarioRepository;
import com.ifpb.hard_zone.util.Validator;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository = new UsuarioRepository();

    public void salvarUsuario(String nome, String dataNascimento, String email) throws DadosUsuarioInvalidoException, ParseException{
        Usuario novoUsuario = criarUsuario(nome, dataNascimento, email);
        usuarioRepository.salvar(novoUsuario);

    }

    public void atualizarUsuario(int id, String nome, String email)
            throws DadosUsuarioInvalidoException, UsuarioNaoEncontradoException {

        Validator.validarNome(nome);
        Validator.validarEmail(email);

        Usuario usuarioAtualizar = buscarUsuarioPorId(id);
        Usuario usuarioExistente = usuarioRepository.buscarPorEmail(email);

        if (usuarioExistente != null && usuarioExistente.getId() != id) {
            throw new DadosUsuarioInvalidoException(
                    String.format("Já existe um Usuario com esse email: %s", email)
            );
        }

        usuarioAtualizar.setNome(nome);
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

    public void desativarUsuario(int id) throws UsuarioNaoEncontradoException, UsuarioStatusInvalidoException {
        Usuario usuarioDesativar = buscarUsuarioPorId(id);
        if(!usuarioDesativar.isAtivo()){
            throw new UsuarioStatusInvalidoException("Não é possível desativar o usuário, pois ele já está desativado.");

        }
        usuarioDesativar.setAtivo(false);

        usuarioRepository.atualizar(usuarioDesativar);
    }

    public void ativarUsuario(int id) throws UsuarioNaoEncontradoException, UsuarioStatusInvalidoException {
        Usuario usuarioAtivar = buscarUsuarioPorId(id);
        if (usuarioAtivar.isAtivo()) {
            throw new UsuarioStatusInvalidoException("Não é possível ativar o usuário, pois ele já está ativo.");
        }
        usuarioAtivar.setAtivo(true);
        usuarioRepository.atualizar(usuarioAtivar);
    }

    public List<Usuario> buscarUsuarioPorNome(String nome) throws UsuarioNaoEncontradoException, DadosUsuarioInvalidoException {
        Validator.validarNome(nome);
        List<Usuario> usuarios = usuarioRepository.buscarPorNome(nome);

        if (usuarios.isEmpty()) {
            throw new UsuarioNaoEncontradoException(String.format("Nenhum usuario encontrado com esse nome: %s no banco de dados" , nome));
        }

        return usuarios;
    }

    public Usuario buscarUsuarioPorEmail(String email) throws UsuarioNaoEncontradoException, DadosUsuarioInvalidoException {
        Validator.validarEmail(email);
        Usuario usuario = usuarioRepository.buscarPorEmail(email);

        if (usuario == null) {
            throw new UsuarioNaoEncontradoException(String.format("Nenhum usuario encontrado com esse email: %s no banco de dados" , email));
        }

        return usuario;
    }

    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.listarTodos();

    }

    public List<Usuario> filtrarUsuariosPorNome(String nome) throws DadosUsuarioInvalidoException {
        Validator.validarNome(nome);
        return usuarioRepository.filtrarPorNome(nome);

    }

    public List<Usuario> filtrarUsuariosPorDataCadastro(String inicio, String fim) throws DataInvalidaException,ParseException {
        Date dataInicio =Validator.criarDateTime(inicio);
        Date dataFim = Validator.criarDateTime(fim);
        Validator.validarPeriodo(dataInicio, dataFim);
       return usuarioRepository.filtrarPeriodo(dataInicio, dataFim);

    }

    public List<Usuario> listarUsuariosPorDataCadastro(String data) throws DataInvalidaException, ParseException {
        Date dataCadastro = Validator.criarDateTime(data);
        Validator.validarData(dataCadastro);
        return usuarioRepository.buscarPorData(dataCadastro);

    }

    public List<Usuario> listarTodosUsuariosAtivos () {
        return usuarioRepository.buscarUsuariosAtivos();

    }

    public List<Usuario> listarTodosUsuariosDesativados() {
         return usuarioRepository.buscarUsuariosDesativos();

    }

    public Usuario excluirUsuarioPermanentemente(int id) throws UsuarioNaoEncontradoException {
        Usuario usuario = buscarUsuarioPorId(id);
        return usuarioRepository.remover(usuario);

    }

    private Usuario criarUsuario(String nome, String dataNascimento, String email) throws DadosUsuarioInvalidoException, ParseException {
        Date dataNascimentoFormatada = Validator.criarDate(dataNascimento);
        validarDadosUsuario(nome, dataNascimentoFormatada , email);

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setDataNascimento(dataNascimentoFormatada);
        usuario.setEmail(email);
        usuario.setDataCadastro(new Date());
        usuario.setAtivo(true);

        return usuario;
    }

    private void validarDadosUsuario(String nome, Date dataNascimento, String email) throws DadosUsuarioInvalidoException {
       Validator.validarNome(nome);
       Validator.validarDataNascimento(dataNascimento);
       Validator.validarEmail(email);

    }
}
