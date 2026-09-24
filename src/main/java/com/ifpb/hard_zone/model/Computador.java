package com.ifpb.hard_zone.model;

import com.ifpb.hard_zone.util.enumerate.StatusComputador;
import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "computadores")
public class Computador {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String especificacoes;

    @Column(nullable = false, unique = true)
    private Integer numeroMaquina;

    @Enumerated(EnumType.STRING)
    private StatusComputador Computador;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "computador_jogos",
            joinColumns = @JoinColumn(name = "computador_id"),
            inverseJoinColumns = @JoinColumn(name = "jogo_id")
    )
    private List<Jogo> jogos = new ArrayList<>();

    public void adiconarJogo(Jogo jogo) {
        if (jogo != null && !this.jogos.contains(jogo)) {
            this.jogos.add(jogo);
            jogo.getComputadores().add(this);
        }
    }

    public void removerJogo(Jogo jogo) {
        if(jogo != null && this.jogos.contains(jogo)) {
            this.jogos.remove(jogo);
            jogo.getComputadores().remove(this);
        }
    }

    public Long getId() {
        return id;
    }

    public String getEspecificacoes() {
        return especificacoes;
    }

    public void setEspecificacoes(String especificacoes) {
        this.especificacoes = especificacoes;
    }

    public Integer getNumeroMaquina() {
        return numeroMaquina;
    }

    public void setNumeroMaquina(Integer numeroMaquina) {
        this.numeroMaquina = numeroMaquina;
    }

    public StatusComputador getComputador() {
        return Computador;
    }

    public void setComputador(StatusComputador computador) {
        Computador = computador;
    }

    public List<Jogo> getJogos() {
        return jogos;
    }

    public void setJogos(List<Jogo> jogos) {
        this.jogos = jogos;
    }
}
