package com.ifpb.hard_zone.exception;

import com.ifpb.hard_zone.exception.dataException.DataInvalidaException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;

public class GlobalExceptionHandler {

    public static String tratar(Throwable e) {
        if (e instanceof RegraDeNegocioException
                || e instanceof EntityNotFoundException
                || e instanceof DataInvalidaException) {
            return e.getMessage();
        }
        if (e instanceof PersistenceException) {
            e.printStackTrace();
            return "Erro ao acessar o banco de dados. Verifique os dados informados.";
        }
        e.printStackTrace();
        return "Erro inesperado. Tente novamente.";
    }
}