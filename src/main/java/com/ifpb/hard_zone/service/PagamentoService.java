package com.ifpb.hard_zone.service;

import com.ifpb.hard_zone.exception.Data.DataInvalidaException;
import com.ifpb.hard_zone.exception.Pagamento.*;
import com.ifpb.hard_zone.model.Pagamento;
import com.ifpb.hard_zone.repository.PagamentoRepository;
import com.ifpb.hard_zone.util.Validator;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public class PagamentoService {

    PagamentoRepository repository = new PagamentoRepository();

    public void salvarPagamento(BigDecimal valor, String codigoPagamento, OffsetDateTime dataHora){
        Pagamento pagamento = criarPagamento(valor, codigoPagamento, dataHora);
        repository.salvar(pagamento);
    }


    public Optional<Pagamento> buscarPorId(Long id){
        Optional<Pagamento> pagamento = repository.buscarPorId(id);

        if(pagamento.isEmpty()){
            throw new PagamentoNaoEncontradoException(String.format("Nenhum pagamento com esse id: %d",id));
        }
        return pagamento;
    }

    public List<Pagamento> listarPagamentos(){
        List<Pagamento> listaPagamentos = repository.buscarTodos();

        if(listaPagamentos.isEmpty()){
            throw new NenhumPagamentoRegistradoException("Nenhum pagamento registrado");
        }
        return listaPagamentos;
    }

    public Optional<Pagamento> buscarPorIdSessao(Long idSessao){
        Optional<Pagamento> pagamento = repository.buscarPorSessao(idSessao);

        if(pagamento.isEmpty()){
            throw new PagamentoNaoEncontradoException(String.format("Nenhuma sessão com esse id: %d",idSessao));
        }
        return pagamento;
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

    public Optional<Pagamento> pagamentoComMaiorValor(){

        Optional<Pagamento> pagamentoComMaiorValor = repository.pagamentoComMaiorValor();

        if(pagamentoComMaiorValor.isEmpty()){
            throw new PagamentoNaoEncontradoException("Nenhum pagamento registrado");
        }

        return pagamentoComMaiorValor;
    }



    public Pagamento criarPagamento(BigDecimal valor, String codigoPagamento, OffsetDateTime dataHora){

        Pagamento pagamento = new Pagamento();
        pagamento.setValor(valor);
        pagamento.setCodigoPagamento(codigoPagamento);
        pagamento.setDataHora(dataHora);

        return pagamento;
    }
}
