package com.ifpb.hard_zone;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Iniciando conexão com o Oracle XE...");

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("hardzone-pu");
            EntityManager em = emf.createEntityManager();

            System.out.println("\n=================================================");
            System.out.println(">>> CONEXÃO REALIZADA COM SUCESSO NO ORACLE! <<<");
            System.out.println("=================================================\n");

            em.close();
            emf.close();
        } catch (Exception e) {
            System.err.println("\n>>> FALHA AO CONECTAR NO BANCO DE DADOS <<<");
            e.printStackTrace();
        }
    }
}
