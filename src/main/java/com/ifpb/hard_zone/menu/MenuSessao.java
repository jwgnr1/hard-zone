package com.ifpb.hard_zone.menu;

import com.ifpb.hard_zone.controller.SessaoController;
import com.ifpb.hard_zone.controller.UsuarioController;

import java.math.BigDecimal;

import static com.ifpb.hard_zone.menu.Console.*;
import static com.ifpb.hard_zone.menu.Formatador.*;

public class MenuSessao {

    private static final SessaoController sessaoController = new SessaoController();
    private static final UsuarioController usuarioController = new UsuarioController();

    private MenuSessao() {
    }

    public static void abrir() {
        int opcao;
        do {
            System.out.println("\n--- SESSÕES ---");
            System.out.println("1 - Iniciar sessão");
            System.out.println("2 - Encerrar sessão");
            System.out.println("3 - Buscar por id");
            System.out.println("4 - Listar todas");
            System.out.println("5 - Listar ativas");
            System.out.println("0 - Voltar");
            opcao = lerInt("Escolha: ");

            switch (opcao) {
                case 1 -> iniciarSessao();
                case 2 -> encerrarSessao();
                case 3 -> buscarSessaoPorId();
                case 4 -> seguro(() -> mostrarLista(sessaoController.listar(),
                        "Nenhuma sessão registrada."));
                case 5 -> imprimirSessoesAtivas();
                case 0 -> { }
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void iniciarSessao() {
        seguro(() -> mostrarLista(usuarioController.listarTodosUsuariosAtivos(),
                "Nenhum usuário ativo."));
        MenuComputador.imprimirComputadores();
        long usuarioId = lerLong("Id do usuário: ");
        long computadorId = lerLong("Id do computador: ");
        BigDecimal precoPorHora = lerDecimal("Preço por hora: ");
        exibir(sessaoController.iniciarSessao(usuarioId, computadorId, precoPorHora));
    }

    private static void encerrarSessao() {
        imprimirSessoesAtivas();
        long id = lerLong("Id da sessão: ");
        String resultado = sessaoController.encerrarSessao(id);
        exibir(resultado);
        if (!resultado.startsWith("Erro") && confirmar("Gerar o pagamento agora?")) {
            MenuPagamento.gerarPagamentoDaSessao(id);
        }
    }

    private static void buscarSessaoPorId() {
        long id = lerLong("Id da sessão: ");
        seguro(() -> mostrarObjeto(sessaoController.buscarPorId(id), "Sessão não encontrada."));
    }

    private static void imprimirSessoesAtivas() {
        seguro(() -> mostrarLista(sessaoController.listarAtivas(), "Nenhuma sessão ativa."));
    }
}
