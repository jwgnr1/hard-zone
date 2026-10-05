package com.ifpb.hard_zone.controller;

import com.ifpb.hard_zone.exception.GlobalExceptionHandler;
import com.ifpb.hard_zone.model.Computador;
import com.ifpb.hard_zone.model.Jogo;
import com.ifpb.hard_zone.model.Sessao;
import com.ifpb.hard_zone.model.Usuario;
import com.ifpb.hard_zone.service.SessaoService;

import java.math.BigDecimal;
import java.util.List;

public class SessaoController {

    private final SessaoService service = new SessaoService();

    public void iniciarSessao(
            Usuario usuario,
            Computador computador,
            Jogo jogo,
            BigDecimal precoPorHora) {

        try {
            service.iniciarSessao(
                    usuario,
                    computador,
                    jogo,
                    precoPorHora
            );

            System.out.println("Sessão iniciada com sucesso.");

        } catch (Exception e) {
            System.out.println(
                    GlobalExceptionHandler.tratar(e)
            );
        }
    }

    public Sessao buscarPorId(Long id) {

        try {
            return service.buscarPorId(id);

        } catch (Exception e) {
            System.out.println(
                    GlobalExceptionHandler.tratar(e)
            );
            return null;
        }
    }

    public List<Sessao> listarSessoes() {

        try {
            return service.listarSessoes();

        } catch (Exception e) {
            System.out.println(
                    GlobalExceptionHandler.tratar(e)
            );
            return List.of();
        }
    }

    public List<Sessao> buscarSessoesAtivas() {

        try {
            return service.buscarSessoesAtivas();

        } catch (Exception e) {
            System.out.println(
                    GlobalExceptionHandler.tratar(e)
            );
            return List.of();
        }
    }

    public Sessao encerrarSessao(Long id) {

        try {
            Sessao sessao = service.encerrarSessao(id);

            System.out.println("Sessão encerrada com sucesso.");
            System.out.println("Valor a pagar: R$ " + sessao.getValor());

            return sessao;

        } catch (Exception e) {
            System.out.println(
                    GlobalExceptionHandler.tratar(e)
            );
            return null;
        }
    }
}