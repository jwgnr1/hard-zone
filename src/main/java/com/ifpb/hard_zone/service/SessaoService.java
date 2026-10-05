package com.ifpb.hard_zone.service;

import com.ifpb.hard_zone.model.Sessao;
import com.ifpb.hard_zone.repository.SessaoRepository;

public class SessaoService {

    private final SessaoRepository repository = new SessaoRepository();

    public void salvarSessao(){
        Sessao sessao = criarSessao();
        repository.salvar(sessao);
    }

    public Sessao criarSessao(){
        return new Sessao();
    }
}
