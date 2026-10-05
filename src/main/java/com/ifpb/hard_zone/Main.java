package com.ifpb.hard_zone;

import com.ifpb.hard_zone.model.Computador;
import com.ifpb.hard_zone.model.Jogo;
import com.ifpb.hard_zone.model.Usuario;
import com.ifpb.hard_zone.service.SessaoService;
import com.ifpb.hard_zone.util.JPAUtil;

import jakarta.persistence.EntityManager;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {

        EntityManager em = JPAUtil.getEntityManager();

        try {

            Usuario usuario = em.find(Usuario.class, 1L);
            Computador computador = em.find(Computador.class, 1L);
            Jogo jogo = em.find(Jogo.class, 1L);

            SessaoService service = new SessaoService();

            service.iniciarSessao(
                    usuario,
                    computador,
                    jogo,
                    new BigDecimal("10.00")
            );

            System.out.println("Sessão criada com sucesso!");

        } finally {
            em.close();
        }
    }
}