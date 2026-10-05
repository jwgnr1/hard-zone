package com.ifpb.hard_zone.service;

import com.ifpb.hard_zone.exception.Data.DataInvalidaException;
import com.ifpb.hard_zone.exception.Pagamento.*;
import com.ifpb.hard_zone.model.Pagamento;
import com.ifpb.hard_zone.model.Sessao;
import com.ifpb.hard_zone.repository.PagamentoRepository;
import com.ifpb.hard_zone.util.Validator;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public class PagamentoService {

    private final PagamentoRepository repository = new PagamentoRepository();

    public void salvarPagamento(BigDecimal valor, String codigoPagamento, OffsetDateTime dataHora){
        Pagamento pagamento = criarPagamento(valor,codigoPagamento,dataHora);
        repository.salvar(pagamento);
    }


    public Pagamento buscarPorId(Long id){
        return repository.buscarPorId(id)
                .orElseThrow(()-> new PagamentoNaoEncontradoException("Nenhum pagamento registrado"));
    }

    public List<Pagamento> listarPagamentos(){
        List<Pagamento> listaPagamentos = repository.buscarTodos();

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

    public Pagamento criarPagamento(BigDecimal valor, String codigoPagamento, OffsetDateTime dataHora){

        Pagamento pagamento = new Pagamento();
        pagamento.setValor(valor);
        pagamento.setCodigoPagamento(codigoPagamento);
        pagamento.setDataHora(dataHora);
        return pagamento;
    }
}
