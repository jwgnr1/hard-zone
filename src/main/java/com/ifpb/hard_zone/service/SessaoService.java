package com.ifpb.hard_zone.service;

import com.ifpb.hard_zone.exception.SessaoJaEncerradaException;
import com.ifpb.hard_zone.exception.SessaoNaoEncontradaException;
import com.ifpb.hard_zone.model.Computador;
import com.ifpb.hard_zone.model.Jogo;
import com.ifpb.hard_zone.model.Sessao;
import com.ifpb.hard_zone.model.Usuario;
import com.ifpb.hard_zone.repository.SessaoRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class SessaoService {

    private final SessaoRepository repository = new SessaoRepository();

    public void iniciarSessao(
            Usuario usuario,
            Computador computador,
            Jogo jogo,
            BigDecimal precoPorHora) {

        Sessao sessao = new Sessao();

        sessao.setUsuario(usuario);
        sessao.setComputador(computador);
        sessao.setJogo(jogo);
        sessao.setPrecoPorHora(precoPorHora);
        sessao.setDataInicio(LocalDateTime.now());

        repository.salvar(sessao);
    }

    public Sessao buscarPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() ->
                        new SessaoNaoEncontradaException(
                                "Sessão não encontrada com o ID: " + id
                        ));
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
            throw new SessaoJaEncerradaException(
                    "A sessão já foi encerrada."
            );
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

        return repository.atualizar(sessao);
    }
}
