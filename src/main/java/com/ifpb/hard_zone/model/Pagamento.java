package com.ifpb.hard_zone.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;

@Entity
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false, unique = true)
    private String codigoPagamento;

    @Column(nullable = false)
    private OffsetDateTime dataHora;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sessao")
    private Sessao sessao;

    public Pagamento(){}

    public Long getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getCodigoPagamento() {
        return codigoPagamento;
    }

    public OffsetDateTime getDataHora() {
        return dataHora;
    }

    public Sessao getSessao() {
        return sessao;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setCodigoPagamento(String codigoPagamento) {
        this.codigoPagamento = codigoPagamento;
    }
    public void setDataHora(OffsetDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public void setSessao(Sessao sessao) {
        this.sessao = sessao;
    }
}


