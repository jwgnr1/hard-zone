package com.ifpb.hard_zone.controller;

import com.ifpb.hard_zone.model.Sessao;
import com.ifpb.hard_zone.model.Usuario;
import com.ifpb.hard_zone.model.Computador;
import com.ifpb.hard_zone.model.Jogo;
import com.ifpb.hard_zone.service.SessaoService;

import java.math.BigDecimal;
import java.util.List;

public class SessaoController {

    private final SessaoService sessaoService = new SessaoService();

    public String iniciarSessao(
            Usuario usuario,
            Computador computador,
            Jogo jogo,
            BigDecimal precoPorHora) {

        return ControllerExecutor.executar(
                () -> sessaoService.iniciarSessao(
                        usuario,
                        computador,
                        jogo,
                        precoPorHora
                ),
                "Sessão iniciada com sucesso!"
        );
    }

    public Sessao buscarPorId(Long id) {
        return sessaoService.buscarPorId(id);
    }

    public List<Sessao> listar() {
        return sessaoService.listarSessoes();
    }

    public List<Sessao> listarAtivas() {
        return sessaoService.buscarSessoesAtivas();
    }

    public String encerrarSessao(Long id) {

        return ControllerExecutor.executar(
                () -> sessaoService.encerrarSessao(id),
                "Sessão encerrada com sucesso!"
        );
    }
}