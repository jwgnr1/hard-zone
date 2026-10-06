package com.ifpb.hard_zone;

import com.ifpb.hard_zone.exception.GlobalExceptionHandler;
import com.ifpb.hard_zone.menu.MenuComputador;
import com.ifpb.hard_zone.menu.MenuJogo;
import com.ifpb.hard_zone.menu.MenuPagamento;
import com.ifpb.hard_zone.menu.MenuSessao;
import com.ifpb.hard_zone.menu.MenuUsuario;
import com.ifpb.hard_zone.util.JPAUtil;

import static com.ifpb.hard_zone.menu.Console.lerInt;

public class Main {

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((thread, erro) ->
                System.out.println("Erro: " + GlobalExceptionHandler.tratar(erro)));

        int opcao;
        do {
            System.out.println("\n===== HARD ZONE - LAN HOUSE =====");
            System.out.println("1 - Computadores");
            System.out.println("2 - Jogos");
            System.out.println("3 - Usuários");
            System.out.println("4 - Sessões");
            System.out.println("5 - Pagamentos");
            System.out.println("0 - Sair");
            opcao = lerInt("Escolha: ");

            switch (opcao) {
                case 1 -> MenuComputador.abrir();
                case 2 -> MenuJogo.abrir();
                case 3 -> MenuUsuario.abrir();
                case 4 -> MenuSessao.abrir();
                case 5 -> MenuPagamento.abrir();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        JPAUtil.fechar();
    }
}
