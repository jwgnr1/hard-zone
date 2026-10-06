package com.ifpb.hard_zone.menu;

import com.ifpb.hard_zone.controller.JogoController;

import static com.ifpb.hard_zone.menu.Console.*;
import static com.ifpb.hard_zone.menu.Formatador.*;

public class MenuJogo {

    private static final JogoController jogoController = new JogoController();

    private MenuJogo() {
    }

    public static void abrir() {
        int opcao;
        do {
            System.out.println("\n--- JOGOS ---");
            System.out.println("1 - Adicionar");
            System.out.println("2 - Listar todos");
            System.out.println("3 - Buscar por nome");
            System.out.println("4 - Atualizar");
            System.out.println("5 - Remover");
            System.out.println("0 - Voltar");
            opcao = lerInt("Escolha: ");

            switch (opcao) {
                case 1 -> adicionarJogo();
                case 2 -> imprimirJogos();
                case 3 -> buscarJogosPorNome();
                case 4 -> atualizarJogo();
                case 5 -> removerJogo();
                case 0 -> { }
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    // Também usado pelo MenuComputador
    public static void imprimirJogos() {
        seguro(() -> mostrarLista(jogoController.listar(),
                "Nenhum jogo cadastrado.", Formatador::formatarJogo));
    }

    private static void adicionarJogo() {
        String nome = lerTexto("Nome do jogo: ");
        int faixaEtaria = lerInt("Faixa etária: ");
        exibir(jogoController.adicionar(nome, faixaEtaria));
    }

    private static void buscarJogosPorNome() {
        String nome = lerTexto("Nome (ou parte dele): ");
        seguro(() -> mostrarLista(jogoController.buscarPorNome(nome),
                "Nenhum jogo encontrado.", Formatador::formatarJogo));
    }

    private static void atualizarJogo() {
        imprimirJogos();
        long id = lerLong("Id do jogo: ");
        String nome = lerTexto("Novo nome: ");
        int faixaEtaria = lerInt("Nova faixa etária: ");
        exibir(jogoController.atualizar(id, nome, faixaEtaria));
    }

    private static void removerJogo() {
        imprimirJogos();
        long id = lerLong("Id do jogo: ");
        if (confirmar("Remover o jogo " + id + "?")) {
            exibir(jogoController.remover(id));
        }
    }
}
