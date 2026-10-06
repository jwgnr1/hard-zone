package com.ifpb.hard_zone.controller;

import com.ifpb.hard_zone.exception.GlobalExceptionHandler;

public class ControllerExecutor {

    @FunctionalInterface
    public interface Acao {
        void executar() throws Exception;
    }

    @FunctionalInterface
    public interface Consulta<T> {
        T executar() throws Exception;
    }

    public static String executar(Acao acao, String mensagemSucesso) {
        try {
            acao.executar();
            return mensagemSucesso;
        } catch (Exception e) {
            return "Erro: " + GlobalExceptionHandler.tratar(e);
        }
    }



    public static <T> T executar(Consulta<T> consulta) {
        try {
            return consulta.executar();
        } catch (Exception e) {
            System.out.println("Erro: " + GlobalExceptionHandler.tratar(e));
            return null;
        }
    }
}