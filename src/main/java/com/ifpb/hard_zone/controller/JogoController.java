package com.ifpb.hard_zone.controller;

import com.ifpb.hard_zone.model.Jogo;
import com.ifpb.hard_zone.service.JogoService;

import java.util.List;

public class JogoController {

    private final JogoService jogoService = new JogoService();

    public String adicionar(String nome, int faixaEtaria) {
        return ControllerExecutor.executar(() -> {
            Jogo jogo = new Jogo();
            jogo.setNome(nome);
            jogo.setFaixaEtaria(faixaEtaria);
            jogoService.adicionar(jogo);
        }, "Jogo adicionado com sucesso!");
    }

    public String remover(Long id) {
        return ControllerExecutor.executar(
                () -> jogoService.remover(id),
                "Jogo removido com sucesso");
    }

    public String atualizar(Long id, String nome, int faixaEtaria) {
        return ControllerExecutor.executar(() -> {
            Jogo dados = new Jogo();
            dados.setNome(nome);
            dados.setFaixaEtaria(faixaEtaria);
            jogoService.atualizar(id, dados);
        }, "Jogo atualizado!");
    }

    public List<Jogo> buscarPorNome(String nome) {
        return jogoService.buscarPorNome(nome);
    }

    public List<Jogo> listar() {
        return jogoService.listarTodos();
    }
}
