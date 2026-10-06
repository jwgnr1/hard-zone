package com.ifpb.hard_zone.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("hardzone-pu");

    private JPAUtil() {
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void fechar() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}