package com.ifpb.hard_zone.menu;

import com.ifpb.hard_zone.controller.ComputadorController;
import com.ifpb.hard_zone.model.Computador;

import static com.ifpb.hard_zone.menu.Console.*;
import static com.ifpb.hard_zone.menu.Formatador.*;

public class MenuComputador {

    private static final ComputadorController computadorController = new ComputadorController();

    private MenuComputador() {
    }

    public static void abrir() {
        int opcao;
        do {
            System.out.println("\n--- COMPUTADORES ---");
            System.out.println("1 - Adicionar");
            System.out.println("2 - Listar todos");
            System.out.println("3 - Buscar por número da máquina");
            System.out.println("4 - Atualizar");
            System.out.println("5 - Alterar status");
            System.out.println("6 - Remover");
            System.out.println("7 - Vincular jogo");
            System.out.println("8 - Desvincular jogo");
            System.out.println("9 - Listar jogos do computador");
            System.out.println("0 - Voltar");
            opcao = lerInt("Escolha: ");

            switch (opcao) {
                case 1 -> adicionarComputador();
                case 2 -> imprimirComputadores();
                case 3 -> buscarComputadorPorNumero();
                case 4 -> atualizarComputador();
                case 5 -> alterarStatusComputador();
                case 6 -> removerComputador();
                case 7 -> vincularJogo();
                case 8 -> desvincularJogo();
                case 9 -> listarJogosDoComputador();
                case 0 -> { }
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    public static void imprimirComputadores() {
        seguro(() -> mostrarLista(computadorController.listar(),
                "Nenhum computador cadastrado.", Formatador::formatarComputador));
    }

    private static void adicionarComputador() {
        int numero = lerInt("Número da máquina: ");
        String especificacoes = lerTexto("Especificações: ");
        exibir(computadorController.adicionar(numero, especificacoes));
    }

    private static void buscarComputadorPorNumero() {
        int numero = lerInt("Número da máquina: ");
        seguro(() -> {
            Computador computador = computadorController.buscarPorNumeroMaquina(numero);
            mostrarObjeto(computador == null ? null : formatarComputador(computador),
                    "Computador não encontrado.");
        });
    }

    private static void atualizarComputador() {
        imprimirComputadores();
        long id = lerLong("Id do computador: ");
        int numero = lerInt("Novo número da máquina: ");
        String especificacoes = lerTexto("Novas especificações: ");
        String status = lerTexto("Status (DISPONIVEL, MANUTENCAO, OCUPADO) - Enter para manter: ");
        exibir(computadorController.atualizar(id, numero, especificacoes, status));
    }

    private static void alterarStatusComputador() {
        imprimirComputadores();
        long id = lerLong("Id do computador: ");
        String status = lerTexto("Novo status (DISPONIVEL, MANUTENCAO, OCUPADO): ");
        exibir(computadorController.alterarStatus(id, status));
    }

    private static void removerComputador() {
        imprimirComputadores();
        long id = lerLong("Id do computador: ");
        if (confirmar("Remover o computador " + id + "?")) {
            exibir(computadorController.remover(id));
        }
    }

    private static void vincularJogo() {
        imprimirComputadores();
        MenuJogo.imprimirJogos();
        long computadorId = lerLong("Id do computador: ");
        long jogoId = lerLong("Id do jogo: ");
        exibir(computadorController.adicionarJogo(computadorId, jogoId));
    }

    private static void desvincularJogo() {
        imprimirComputadores();
        long computadorId = lerLong("Id do computador: ");
        seguro(() -> mostrarLista(computadorController.listarJogos(computadorId),
                "Esse computador não tem jogos vinculados.", Formatador::formatarJogo));
        long jogoId = lerLong("Id do jogo a desvincular: ");
        exibir(computadorController.removerJogo(computadorId, jogoId));
    }

    private static void listarJogosDoComputador() {
        long computadorId = lerLong("Id do computador: ");
        seguro(() -> mostrarLista(computadorController.listarJogos(computadorId),
                "Esse computador não tem jogos vinculados.", Formatador::formatarJogo));
    }
}
