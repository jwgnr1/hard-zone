package com.ifpb.hard_zone.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "jogos")
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jogos_seq_gen")
    @SequenceGenerator(
            name = "jogos_seq_gen",
            sequenceName = "jogos_SEQ",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private int faixaEtaria;

    @ManyToMany(mappedBy = "jogos")
    private List<Computador> computadores = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getFaixaEtaria() {
        return faixaEtaria;
    }

    public void setFaixaEtaria(int faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }

    public List<Computador> getComputadores() {
        return computadores;
    }

    public void setComputadores(List<Computador> computadores) {
        this.computadores = computadores;
    }
}
