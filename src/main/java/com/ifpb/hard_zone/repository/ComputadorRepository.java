package com.ifpb.hard_zone.repository;

import com.ifpb.hard_zone.model.Computador;
import com.ifpb.hard_zone.util.JPAUtil;
import jakarta.persistence.EntityManager;

public class ComputadorRepository extends RepositoryBase<Computador, Long>{

    private final EntityManager em = JPAUtil.getEntityManager();

    ComputadorRepository() {super(Computador.class);}


}
