package com.ifpb.hard_zone.exception;

import com.ifpb.hard_zone.exception.Pagamento.NenhumPagamentoRegistradoException;
import com.ifpb.hard_zone.exception.Pagamento.PagamentoNaoEncontradoException;
import com.ifpb.hard_zone.exception.dataException.DataInvalidaException;
import com.ifpb.hard_zone.exception.usuariosExceptions.DadosUsuarioInvalidoException;
import com.ifpb.hard_zone.exception.usuariosExceptions.UsuarioNaoEncontradoException;
import com.ifpb.hard_zone.exception.usuariosExceptions.UsuarioStatusInvalidoException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;

public class GlobalExceptionHandler {

    public static String tratar(Throwable e) {
        if (e instanceof RegraDeNegocioException
                || e instanceof EntityNotFoundException
                || e instanceof DataInvalidaException
                || e instanceof DadosUsuarioInvalidoException
                || e instanceof UsuarioNaoEncontradoException
                || e instanceof UsuarioStatusInvalidoException
                || e instanceof NenhumPagamentoRegistradoException
                || e instanceof PagamentoNaoEncontradoException) {
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