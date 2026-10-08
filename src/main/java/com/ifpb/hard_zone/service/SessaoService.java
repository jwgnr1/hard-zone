package com.ifpb.hard_zone.service;

import com.ifpb.hard_zone.exception.RegraDeNegocioException;
import com.ifpb.hard_zone.exception.SessaoJaEncerradaException;
import com.ifpb.hard_zone.exception.SessaoNaoEncontradaException;
import com.ifpb.hard_zone.model.Computador;
import com.ifpb.hard_zone.model.Sessao;
import com.ifpb.hard_zone.model.Usuario;
import com.ifpb.hard_zone.repository.SessaoRepository;
import com.ifpb.hard_zone.util.enumerate.StatusComputador;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class SessaoService {

    private final SessaoRepository repository;
    private final UsuarioService usuarioService;
    private final ComputadorService computadorService;

    public SessaoService(SessaoRepository repository, 
                         UsuarioService usuarioService, 
                         ComputadorService computadorService) {
        this.repository = repository;
        this.usuarioService = usuarioService;
        this.computadorService = computadorService;
    }

    public SessaoService() {
        this(new SessaoRepository(), new UsuarioService(), new ComputadorService());
    }

    public void iniciarSessao(Long usuarioID, Long computadorID, BigDecimal precoPorHora)
            throws Exception {

        validarPrecoPorHora(precoPorHora);

        Usuario usuario = validarUsuario(usuarioID);
        Computador computador = validarComputador(computadorID);

        validarDisponibilidade(computador);

        Sessao sessao = new Sessao();
        sessao.setUsuario(usuario);
        sessao.setComputador(computador);
        sessao.setPrecoPorHora(precoPorHora);
        sessao.setDataInicio(LocalDateTime.now());

        computador.setStatus(StatusComputador.OCUPADO);
        computadorService.atualizar(computador.getId(), computador);

        repository.salvar(sessao);
    }

    public Sessao buscarPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() ->
                        new SessaoNaoEncontradaException("Sessão não encontrada com o ID: " + id));
    }

    public List<Sessao> listarSessoes() {
        return repository.listarTodos();
    }

    public List<Sessao> buscarSessoesAtivas() {
        return repository.buscarSessoesAtivas();
    }

    public Sessao encerrarSessao(Long id) {

        Sessao sessao = buscarPorId(id);

        if (sessao.getDataFim() != null) {
            throw new SessaoJaEncerradaException("A sessão já foi encerrada.");
        }

        sessao.setDataFim(LocalDateTime.now());

        Duration duracao = Duration.between(
                sessao.getDataInicio(),
                sessao.getDataFim()
        );

        BigDecimal horas = BigDecimal.valueOf(duracao.getSeconds())
                .divide(
                        BigDecimal.valueOf(3600),
                        10,
                        RoundingMode.HALF_UP
                );

        BigDecimal valor = horas.multiply(
                sessao.getPrecoPorHora()
        );

        sessao.setValor(
                valor.setScale(2, RoundingMode.HALF_UP)
        );

        Sessao encerrada = repository.atualizar(sessao);
        computadorService.alterarStatus(sessao.getComputador().getId(), StatusComputador.DISPONIVEL);
        return encerrada;
    }


    private Usuario validarUsuario(Long usuarioID) throws Exception {

        if (usuarioID == null) {
            throw new RegraDeNegocioException("Usuário não pode ser nulo.");
        }

        return usuarioService.buscarUsuarioPorId(usuarioID);
    }

    private Computador validarComputador(Long computadorID) throws RegraDeNegocioException {

        if (computadorID == null) {
            throw new RegraDeNegocioException("Computador não pode ser nulo.");
        }

        return computadorService.buscarPorId(computadorID);
    }

    private void validarDisponibilidade(Computador computador) throws RegraDeNegocioException {

        if (computador.getStatus() != StatusComputador.DISPONIVEL) {
            throw new RegraDeNegocioException(
                    "O computador " + computador.getNumeroMaquina() + " não está disponível.");
        }
    }

    private void validarPrecoPorHora(BigDecimal precoPorHora) throws RegraDeNegocioException {
        if (precoPorHora == null) {
            throw new RegraDeNegocioException("Preço por hora não pode ser nulo.");
        }

        if (precoPorHora.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Preço por hora deve ser maior que zero.");
        }
    }

}
