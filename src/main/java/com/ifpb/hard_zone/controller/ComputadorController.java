package com.ifpb.hard_zone.controller;

import com.ifpb.hard_zone.model.Computador;
import com.ifpb.hard_zone.model.Jogo;
import com.ifpb.hard_zone.service.ComputadorService;
import com.ifpb.hard_zone.util.enumerate.StatusComputador;

import java.util.List;

public class ComputadorController {

    private final ComputadorService computadorService;

    public ComputadorController(ComputadorService computadorService) {
        this.computadorService = computadorService;
    }

    public ComputadorController() {
        this(new ComputadorService());
    }

    public String adicionar(Integer numeroMaquina, String especificacoes) {
        return ControllerExecutor.executar(() -> {
            Computador computador = new Computador();
            computador.setNumeroMaquina(numeroMaquina);
            computador.setEspecificacoes(especificacoes);
            computadorService.adicionarComputador(computador);
        }, "Computador adicionado com sucesso!");
    }

    public String alterarStatus(Long id, String status) {
        return ControllerExecutor.executar(
                () -> computadorService.alterarStatus(id, StatusComputador.deTexto(status)),
                "Status alterado!");
    }

    public String atualizar(Long id, Integer numeroMaquina, String especificacoes, String status) {
        return ControllerExecutor.executar(() -> {
            Computador dados = new Computador();
            dados.setNumeroMaquina(numeroMaquina);
            dados.setEspecificacoes(especificacoes);
            dados.setStatus(StatusComputador.deTexto(status));
            computadorService.atualizar(id, dados);
        }, "Computador atualizado!");
    }

    public String remover(Long id) {
        return ControllerExecutor.executar(
                () -> computadorService.removerComputador(id),
                "Computador removido!");
    }

    public Computador buscarPorNumeroMaquina(Integer numeroMaquina) {
        return computadorService.buscarPorNumeroMaquina(numeroMaquina);
    }

    public String adicionarJogo(Long computadorId, Long jogoId) {
        return ControllerExecutor.executar(
                () -> computadorService.adicionarJogo(computadorId, jogoId),
                "Jogo vinculado ao computador!");
    }

    public String removerJogo(Long computadorId, Long jogoId) {
        return ControllerExecutor.executar(
                () -> computadorService.removerJogo(computadorId, jogoId),
                "Jogo removido do computador!");
    }

    public List<Computador> listar() {
        return computadorService.listarTodos();
    }

    public List<Jogo> listarJogos(Long computadorId) {
        return computadorService.listarJogos(computadorId);
    }
}
