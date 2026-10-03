package com.ifpb.hard_zone.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

/*
Interface utilizada para unificar metodos em comum nos Repositories,
a fim de evitar duplicidade de código.
*/

public interface Repository<T, ID> {

    void salvar(T entidade);

    T atualizar(T entidade);

    T remover(T entidade);

    Optional<T> buscarPorId(ID id);

    List<T> buscarTodos();

    List<T> filtrarPorperiodo(OffsetDateTime dataInicio, OffsetDateTime dataFim);
}
