package com.ifpb.hard_zone.repository;

import java.util.List;
import java.util.Optional;


public interface Repository<T, ID> {

    void salvar(T entidade);

    T atualizar(T entidade);

    T remover(T entidade);

    Optional<T> buscarPorId(ID id);

    List<T> buscarTodos();

}
