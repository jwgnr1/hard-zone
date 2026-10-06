package com.ifpb.hard_zone.menu;

import com.ifpb.hard_zone.controller.PagamentoController;
import com.ifpb.hard_zone.controller.SessaoController;
import com.ifpb.hard_zone.exception.Pagamento.PagamentoNaoEncontradoException;
import com.ifpb.hard_zone.model.Sessao;
import com.ifpb.hard_zone.util.CodigoPagamento;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;

import static com.ifpb.hard_zone.menu.Console.*;
import static com.ifpb.hard_zone.menu.Formatador.*;

public class MenuPagamento {

    private static final PagamentoController pagamentoController = new PagamentoController();
    private static final SessaoController sessaoController = new SessaoController();

    private MenuPagamento() {
    }

    public static void abrir() {
        int opcao;
        do {
            System.out.println("\n--- PAGAMENTOS ---");
            System.out.println("1 - Gerar pagamento");
            System.out.println("2 - Buscar por id");
            System.out.println("3 - Buscar por sessão");
            System.out.println("4 - Listar todos");
            System.out.println("5 - Filtrar por período");
            System.out.println("6 - Faturamento por período");
            System.out.println("7 - Pagamento de maior valor");
            System.out.println("0 - Voltar");
            opcao = lerInt("Escolha: ");

            switch (opcao) {
                case 1 -> gerarPagamento();
                case 2 -> buscarPagamentoPorId();
                case 3 -> buscarPagamentoPorSessao();
                case 4 -> seguro(() -> mostrarLista(pagamentoController.listarPagamentos(),
                        "Nenhum pagamento registrado."));
                case 5 -> filtrarPagamentosPorPeriodo();
                case 6 -> faturamentoPorPeriodo();
                case 7 -> seguro(() -> mostrarObjeto(pagamentoController.pagamentoComMaiorValor(),
                        "Nenhum pagamento registrado."));
                case 0 -> { }
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void gerarPagamento() {
        seguro(() -> mostrarLista(sessaoController.listar(), "Nenhuma sessão registrada."));
        long sessaoId = lerLong("Id da sessão: ");
        gerarPagamentoDaSessao(sessaoId);
    }

    public static void gerarPagamentoDaSessao(long sessaoId) {
        seguro(() -> {
            Sessao sessao = sessaoController.buscarPorId(sessaoId);

            if (sessao.getDataFim() == null || sessao.getValor() == null) {
                System.out.println("A sessão ainda está em andamento. Encerre-a antes de gerar o pagamento.");
                return;
            }
            if (pagamentoJaExiste(sessaoId)) {
                System.out.println("Essa sessão já tem um pagamento registrado.");
                return;
            }

            // o argumento do construtor é ignorado: ele mesmo gera o código PAG-HARDZONE-...
            CodigoPagamento codigo = new CodigoPagamento(null);
            exibir(pagamentoController.gerarPagamento(
                    sessao.getValor(), codigo, OffsetDateTime.now(), sessao));
        });
    }

    private static boolean pagamentoJaExiste(long sessaoId) {
        try {
            pagamentoController.buscarPorIdSessao(sessaoId);
            return true;
        } catch (PagamentoNaoEncontradoException e) {
            return false;
        }
    }

    private static void buscarPagamentoPorId() {
        long id = lerLong("Id do pagamento: ");
        seguro(() -> mostrarObjeto(pagamentoController.buscarPorId(id), "Pagamento não encontrado."));
    }

    private static void buscarPagamentoPorSessao() {
        long sessaoId = lerLong("Id da sessão: ");
        seguro(() -> mostrarObjeto(pagamentoController.buscarPorIdSessao(sessaoId),
                "Nenhum pagamento para essa sessão."));
    }

    private static void filtrarPagamentosPorPeriodo() {
        OffsetDateTime inicio = inicioDoDia(lerData("Data inicial (dd/MM/yyyy): "));
        OffsetDateTime fim = fimDoDia(lerData("Data final (dd/MM/yyyy): "));
        seguro(() -> mostrarLista(pagamentoController.filtrarPorPeriodo(inicio, fim),
                "Nenhum pagamento no período."));
    }

    private static void faturamentoPorPeriodo() {
        OffsetDateTime inicio = inicioDoDia(lerData("Data inicial (dd/MM/yyyy): "));
        OffsetDateTime fim = fimDoDia(lerData("Data final (dd/MM/yyyy): "));
        seguro(() -> {
            BigDecimal total = pagamentoController.faturamentoPorPeriodo(inicio, fim);
            if (total == null) {
                System.out.println("Nenhum faturamento no período.");
            } else {
                System.out.println("Faturamento no período: R$ " + total.setScale(2, RoundingMode.HALF_UP));
            }
        });
    }
}
