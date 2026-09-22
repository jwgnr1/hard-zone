package com.ifpb.hard_zone.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static final EntityManagerFactory FACTORY =
            Persistence.createEntityManagerFactory("Projeto 1 BD2");

    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }
}