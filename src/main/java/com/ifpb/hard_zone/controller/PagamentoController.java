package com.ifpb.hard_zone.controller;

import com.ifpb.hard_zone.exception.dataException.DataInvalidaException;
import com.ifpb.hard_zone.model.Pagamento;
import com.ifpb.hard_zone.model.Sessao;
import com.ifpb.hard_zone.service.PagamentoService;
import com.ifpb.hard_zone.util.CodigoPagamento;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class PagamentoController {

    private final PagamentoService service = new PagamentoService();


    public String gerarPagamento(BigDecimal valor, CodigoPagamento codigoPagamento, OffsetDateTime dataHora, Sessao sessao){
        return ControllerExecutor.executar(() ->{
        Pagamento pagamento = new Pagamento();
        pagamento.setValor(valor);
        pagamento.setCodigoPagamento(codigoPagamento);
        pagamento.setDataHora(dataHora);
        pagamento.setSessao(sessao);
        service.salvarPagamento(pagamento);},
        "Pagamento gerado");
    }

    public Pagamento buscarPorId(Long id){
        return service.buscarPorId(id);
    }

    public List<Pagamento> listarPagamentos(){
        return service.listarPagamentos();
    }

    public Pagamento buscarPorIdSessao(Long idSessao){
        return service.buscarPorIdSessao(idSessao);
    }

    public List<Pagamento> filtrarPorPeriodo(OffsetDateTime dataHoraInicio, OffsetDateTime dataHoraFim) throws DataInvalidaException {
        return service.filtrarPorPeriodo(dataHoraInicio,dataHoraFim);
    }

    public BigDecimal faturamentoPorPeriodo(OffsetDateTime dataHoraInicio, OffsetDateTime dataHoraFim) throws DataInvalidaException {
        return service.faturamentoPorPeriodo(dataHoraInicio, dataHoraFim);
    }

    public Pagamento pagamentoComMaiorValor(){
        return service.pagamentoComMaiorValor();
    }


}
