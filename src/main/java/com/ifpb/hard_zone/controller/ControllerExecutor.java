package com.ifpb.hard_zone.controller;

import com.ifpb.hard_zone.exception.GlobalExceptionHandler;

public class ControllerExecutor {

    @FunctionalInterface
    public interface Acao {
        void executar() throws Exception;
    }

    public static String executar(Acao acao, String mensagemSucesso) {
        try {
            acao.executar();
            return mensagemSucesso;
        } catch (Exception e) {
            return "Erro: " + GlobalExceptionHandler.tratar(e);
        }
    }
}