package com.ifpb.hard_zone;

import com.ifpb.hard_zone.model.*;
import com.ifpb.hard_zone.service.*;
import com.ifpb.hard_zone.util.CodigoPagamento;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FluxoPersistenciaTest {

    @Test
    void testarFluxoCompleto() throws Exception {
        UsuarioService usuarioService = new UsuarioService();
        ComputadorService computadorService = new ComputadorService();
        JogoService jogoService = new JogoService();
        SessaoService sessaoService = new SessaoService();
        PagamentoService pagamentoService = new PagamentoService();

        // 1. Cadastrar Usuário
        String emailTeste = "teste_" + System.currentTimeMillis() + "@email.com";
        usuarioService.salvarUsuario("Usuario Teste", "01/01/2000", emailTeste);
        Usuario usuario = usuarioService.buscarUsuarioPorEmail(emailTeste);
        assertNotNull(usuario.getId());

        // 2. Cadastrar Computador
        int numeroMaquina = (int) (Math.random() * 10000) + 1;
        Computador comp = new Computador();
        comp.setNumeroMaquina(numeroMaquina);
        comp.setEspecificacoes("Core i7, RTX 3060, 16GB RAM");
        computadorService.adicionarComputador(comp);
        Computador computador = computadorService.buscarPorNumeroMaquina(numeroMaquina);
        assertNotNull(computador.getId());

        // 3. Cadastrar Jogo e vincular ao computador
        Jogo jogo = new Jogo();
        jogo.setNome("Cyberpunk " + System.currentTimeMillis());
        jogo.setFaixaEtaria(18);
        jogoService.adicionar(jogo);
        Jogo jogoCriado = jogoService.buscarPorId(jogo.getId());
        assertNotNull(jogoCriado.getId());

        computadorService.adicionarJogo(computador.getId(), jogoCriado.getId());

        // 4. Iniciar Sessão
        BigDecimal precoHora = new BigDecimal("10.00");
        sessaoService.iniciarSessao(usuario.getId(), computador.getId(), precoHora);
        
        // Buscar a sessão recém criada (ativa)
        var sessoesAtivas = sessaoService.buscarSessoesAtivas();
        assertFalse(sessoesAtivas.isEmpty());
        Sessao sessao = sessoesAtivas.get(sessoesAtivas.size() - 1);

        // 5. Simular 10 segundos de sessão (aguardar e encerrar)
        System.out.println("Aguardando 10 segundos de sessão...");
        Thread.sleep(10000);

        Sessao sessaoEncerrada = sessaoService.encerrarSessao(sessao.getId());
        assertNotNull(sessaoEncerrada.getDataFim());
        assertNotNull(sessaoEncerrada.getValor());
        System.out.println("Sessao encerrada. Valor calculado: R$ " + sessaoEncerrada.getValor());

        // 6. Gerar Pagamento
        Pagamento pagamento = new Pagamento();
        pagamento.setSessao(sessaoEncerrada);
        pagamento.setValor(sessaoEncerrada.getValor());
        pagamento.setDataHora(OffsetDateTime.now());
        pagamento.setCodigoPagamento(new CodigoPagamento("GERADO"));

        Pagamento pagamentoSalvo = pagamentoService.salvarPagamento(pagamento);
        assertNotNull(pagamentoSalvo.getId());
        System.out.println("Pagamento salvo com sucesso ID: " + pagamentoSalvo.getId() + " Codigo: " + pagamentoSalvo.getCodigoPagamento().getCodigoGerado());
    }
}
