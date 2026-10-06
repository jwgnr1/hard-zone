package com.ifpb.hard_zone.exception;

public class SessaoJaEncerradaException extends RuntimeException {

    public SessaoJaEncerradaException(String mensagem) {
        super(mensagem);
    }
}
