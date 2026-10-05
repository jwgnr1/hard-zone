package com.ifpb.hard_zone;

import com.ifpb.hard_zone.model.Usuario;
import com.ifpb.hard_zone.repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Date;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Tentando conectar ao Oracle Database XE 21c ===");

        // "hardzone-pu" deve ser o mesmo nome declarado no persistence.xml
        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            emf = Persistence.createEntityManagerFactory("hardzone-pu");
            em = emf.createEntityManager();

            // Executa uma consulta nativa rápida no Oracle para validar a conexão ativa
            Object result = em.createNativeQuery("SELECT 'Conexao OK!' FROM DUAL").getSingleResult();

            System.out.println("\n--------------------------------------------------");
            System.out.println(" SUCESSO! Conexao estabelecida com o banco:");
            System.out.println(" Resultado da query de teste: " + result);
            System.out.println("--------------------------------------------------\n");

        } catch (Exception e) {
            System.err.println("\n--------------------------------------------------");
            System.err.println(" ERRO: Nao foi possivel conectar ao banco de dados.");
            System.err.println("--------------------------------------------------");
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
            System.out.println("=== Recursos fechados com sucesso ===");
        }
    }
}