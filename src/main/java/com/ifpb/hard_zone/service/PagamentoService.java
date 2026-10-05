package com.ifpb.hard_zone.service;

import com.ifpb.hard_zone.exception.dataException.DataInvalidaException;
import com.ifpb.hard_zone.exception.Pagamento.*;
import com.ifpb.hard_zone.model.Pagamento;
import com.ifpb.hard_zone.repository.PagamentoRepository;
import com.ifpb.hard_zone.util.Validator;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class PagamentoService {

    private final PagamentoRepository repository = new PagamentoRepository();

    public Pagamento salvarPagamento(Pagamento pagamento){
        repository.salvar(pagamento);
        return pagamento;
    }


    public Pagamento buscarPorId(Long id){
        return repository.buscarPorId(id)
                .orElseThrow(()-> new PagamentoNaoEncontradoException("Nenhum pagamento registrado"));
    }

    public List<Pagamento> listarPagamentos(){
        List<Pagamento> listaPagamentos = repository.listarTodos();

        if(listaPagamentos.isEmpty()){
            throw new NenhumPagamentoRegistradoException("Nenhum pagamento registrado");
        }
        return listaPagamentos;
    }

    public Pagamento buscarPorIdSessao(Long idSessao){
        return repository.buscarPorSessao(idSessao)
                .orElseThrow(()-> new PagamentoNaoEncontradoException("Nenhuma sessão registrada"));
    }

    public List<Pagamento> filtrarPorPeriodo(OffsetDateTime dataInicio, OffsetDateTime dataFim) throws DataInvalidaException {

        Validator.validarPeriodo(dataInicio,dataFim);

        List<Pagamento> pagamentosNoPeriodo = repository.filtrarPorperiodo(dataInicio,dataFim);

        if (pagamentosNoPeriodo.isEmpty()){
            throw new PagamentoNaoEncontradoException("Nenhum pagamento encontrado nesse periodo");
        }

        return pagamentosNoPeriodo;
    }

    public BigDecimal faturamentoPorPeriodo(OffsetDateTime dataInicio, OffsetDateTime dataFim) throws DataInvalidaException {

        Validator.validarPeriodo(dataInicio,dataFim);

        return repository.faturamnetoPorPeriodo(dataInicio,dataFim);
    }

    public Pagamento pagamentoComMaiorValor(){
        return repository.pagamentoComMaiorValor()
                .orElseThrow(()-> new PagamentoNaoEncontradoException("Nenhuma sessão registrada"));
    }

}
