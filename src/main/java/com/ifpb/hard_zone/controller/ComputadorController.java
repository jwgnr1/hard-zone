package com.ifpb.hard_zone.controller;

import com.ifpb.hard_zone.model.Computador;
import com.ifpb.hard_zone.model.Jogo;
import com.ifpb.hard_zone.service.ComputadorService;
import com.ifpb.hard_zone.util.enumerate.StatusComputador;

import java.util.List;

public class ComputadorController {

    private final ComputadorService computadorService = new ComputadorService();

    public String cadastrar(Integer numeroMaquina, String especificacoes) {
        return ControllerExecutor.executar(() -> {
            Computador computador = new Computador();
            computador.setNumeroMaquina(numeroMaquina);
            computador.setEspecificacoes(especificacoes);
            computadorService.adicionarComputador(computador);
        }, "Computador cadastrado com sucesso!");
    }

    public String alterarStatus(Long id, StatusComputador status) {
        return ControllerExecutor.executar(
                () -> computadorService.alterarStatus(id, status),
                "Status alterado!");
    }

    public String remover(Long id) {
        return ControllerExecutor.executar(
                () -> computadorService.removerComputador(id),
                "Computador removido!");
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
