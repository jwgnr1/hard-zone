import com.ifpb.hard_zone.model.Pagamento;
import com.ifpb.hard_zone.model.Sessao;
import com.ifpb.hard_zone.service.PagamentoService;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class Main {

    @Deprecated(since = "Só para testes/ Remover depois")
    public static void main(String[] args) {
        PagamentoService pagamentoService = new PagamentoService();

        System.out.println("=== INICIANDO TESTES DO PAGAMENTO SERVICE ===");

        try {
            // 1. Criar e Salvar Pagamentos de Teste
            OffsetDateTime agora = OffsetDateTime.now(ZoneOffset.ofHours(-3));
            OffsetDateTime dataOntem = agora.minusDays(1);
            OffsetDateTime dataSemanaPassada = agora.minusDays(7);

            System.out.println("\n1. Cadastrando pagamentos...");
            pagamentoService.salvarPagamento(new BigDecimal("50.00"), "PAG-001", dataSemanaPassada);
            pagamentoService.salvarPagamento(new BigDecimal("120.50"), "PAG-002", dataOntem);
            pagamentoService.salvarPagamento(new BigDecimal("200.00"), "PAG-003", agora);
            System.out.println("✅ 3 pagamentos salvos com sucesso!");

            // 2. Listar Todos os Pagamentos
            System.out.println("\n2. Listando todos os pagamentos salvos:");
            List<Pagamento> todos = pagamentoService.listarPagamentos();
            todos.forEach(p -> System.out.printf("  - ID: %d | Código: %s | Valor: R$ %.2f | DataHora: %s%n",
                    p.getId(), p.getCodigoPagamento(), p.getValor(), p.getDataHora()));

            // 3. Filtrar por Período (Últimos 2 dias)
            OffsetDateTime inicioFiltro = agora.minusDays(2);
            OffsetDateTime fimFiltro = agora.plusDays(1);

            System.out.println("\n3. Filtrando pagamentos dos últimos 2 dias:");
            List<Pagamento> filtrados = pagamentoService.filtrarPorPeriodo(inicioFiltro, fimFiltro);
            filtrados.forEach(p -> System.out.printf("  - Código: %s | Valor: R$ %.2f | DataHora: %s%n",
                    p.getCodigoPagamento(), p.getValor(), p.getDataHora()));

            // 4. Calcular Faturamento no Período
            System.out.println("\n4. Calculando faturamento total do período:");
            BigDecimal faturamento = pagamentoService.faturamentoPorPeriodo(inicioFiltro, fimFiltro);
            System.out.println("  - Faturamento total: R$ " + faturamento);

            // 5. Consultar Pagamento de Maior Valor
            System.out.println("\n5. Buscando o pagamento com maior valor:");
            Pagamento maiorPagamento = pagamentoService.pagamentoComMaiorValor();
            System.out.printf("  - Maior pagamento: Código %s (R$ %.2f)%n",
                    maiorPagamento.getCodigoPagamento(), maiorPagamento.getValor());

        } catch (Exception e) {
            System.err.println("❌ Ocorreu um erro durante o teste:");
            e.printStackTrace();
        } finally {
            System.out.println("\n=== FIM DOS TESTES ===");
        }
    }
}
