package com.ifpb.hard_zone;

import com.ifpb.hard_zone.controller.ComputadorController;
import com.ifpb.hard_zone.controller.JogoController;
import com.ifpb.hard_zone.model.Computador;
import com.ifpb.hard_zone.model.Jogo;
import com.ifpb.hard_zone.model.Usuario;
import com.ifpb.hard_zone.service.SessaoService;
import com.ifpb.hard_zone.util.JPAUtil;

import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        EntityManager em = JPAUtil.getEntityManager();
        JogoController jc = new JogoController();
        ComputadorController cc = new ComputadorController();

        try {


        } finally {
            em.close();
        }
    }
}